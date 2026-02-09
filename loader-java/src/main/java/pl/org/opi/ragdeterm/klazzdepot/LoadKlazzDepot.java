package pl.org.opi.ragdeterm.klazzdepot;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import pl.org.opi.dbaccess.util.StrTool;

import java.io.File;
import java.io.IOException;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static pl.org.opi.ragdeterm.consta.Consta.JDK_PREFIX;

@Slf4j
public class LoadKlazzDepot {

    public KlazzDepotData loadJdk() throws IOException {
        String jdkVersion = JDK_PREFIX + System.getProperty("java.vm.vendor").trim() + "_" + Runtime.version().toString().trim();
        KlazzDepotData klazzDepotData = new KlazzDepotData(jdkVersion, new ArrayList<>());
        for (var module : ModuleLayer.boot().modules()) {
            var reference = ModuleLayer.boot().configuration().findModule(module.getName()).orElseThrow().reference();
            try (var reader = reference.open()) {
                for (String row : reader.list().toList()) {
                    if (row.equalsIgnoreCase("module-info.class")) continue;
                    if (!row.toLowerCase().endsWith(".class")) continue;
                    var name = row.replaceAll("/", ".");
                    name = StringUtils.removeEnd(name, ".class");
                    try {
                        var k = ClassLoader.getSystemClassLoader().loadClass(name);
                        klazzDepotData.appendKlazz(k, true);
                    } catch (Exception ex) {
                        log.error(ex.getMessage());
                    }
                }
            }
        }
        return klazzDepotData;
    }

    public KlazzDepotData loadJar(String jarFullPath, String jarClassPath) throws IOException {
        jarFullPath = StrTool.normalizeBlankTrim(jarFullPath);
        jarClassPath = StrTool.normalizeBlankTrim(jarClassPath);

        List<String> jarClassPathList = new ArrayList<>();
        jarClassPathList.add(jarFullPath);
        for (String pathPos: jarClassPath.split("[,;]")) {
            pathPos = StrTool.normalizeBlankTrim(pathPos);
            if (StringUtils.isNotBlank(pathPos)) {
                jarClassPathList.add(pathPos);
            }
        }

        UrlClassLoaderHelper urlClassLoaderHelper = new UrlClassLoaderHelper(jarClassPathList);
        File file = new File(jarFullPath);
        URLClassLoader currentClassLoader = urlClassLoaderHelper.getOrderedClassLoader(file.getName());
        JarFile jarFile = new JarFile(file);
        KlazzDepotData klazzDepotData = new KlazzDepotData(FilenameUtils.getBaseName(jarFile.getName()), urlClassLoaderHelper.getJarSimpleNames());
        final Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            final JarEntry entry = entries.nextElement();
            if (entry.getName().contains(".")) {
                if (!entry.getName().toUpperCase().startsWith("META-INF")) {
                    var name = entry.getName().replaceAll("/", ".");
                    name = StringUtils.removeEnd(name, ".class");
                    try {
                        var k = currentClassLoader.loadClass(name);
                        klazzDepotData.appendKlazz(k, false);
                    } catch (Exception ex) {
                        log.warn(ex.getMessage());
                        log.warn("Cannot load class: [ " + name + " ].");
                        log.warn("Verify classpath.");
                    }
                }
            }
        }
        return klazzDepotData;
    }

}
