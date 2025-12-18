package pl.org.opi.ragdeterm.codeanalyser.classdepot;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import pl.org.opi.ragdeterm.db.util.GUID;
import pl.org.opi.ragdeterm.repository.klazz.KlazzEntity;
import pl.org.opi.ragdeterm.repository.klazz.KlazzResolveTypex;
import pl.org.opi.ragdeterm.repository.klazz.KlazzService;

import java.io.File;
import java.io.IOException;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@Slf4j
public class KlazzDepotImpl implements KlazzDepot {

    public void loadJarKlazzes2Table(String jarFullPath, String sourceFullPath, boolean loadSource) throws IOException {
        UrlClassLoaderHelper urlClassLoaderHelper = new UrlClassLoaderHelper(List.of(jarFullPath));
        File file = new File(jarFullPath);
        URLClassLoader currentClassLoader = urlClassLoaderHelper.getOrderedClassLoader(file.getName());
        JarFile jarFile = new JarFile(file);
        final Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            final JarEntry entry = entries.nextElement();
            if (entry.getName().contains(".")) {
                if (!entry.getName().toUpperCase().startsWith("META-INF")) {
                    var name = entry.getName().replaceAll("/", ".");
                    name = StringUtils.removeEnd(name, ".class");

                    try {
                        var k = currentClassLoader.loadClass(name);
                        appendKlazz(k, FilenameUtils.getBaseName(jarFile.getName()), sourceFullPath, loadSource);
                    } catch (Exception ex) {
                        // OK
                    }
                }
            }
        }
    }

    public void loadJdk2Table() throws IOException {
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
                        appendKlazz(k, "JDK", null, false);
                    } catch (Exception ex) {
                        log.error(ex.getMessage());
                    }
                }
            }
        }
    }

    private void appendKlazz(Class<?> k, String jarSimpleName, String sourceFullPath, boolean loadSource) {
        KlazzService service = new KlazzService();
        var en = new KlazzEntity();

        en.setIdUid(GUID.gen());
        en.setJarSimpleName(jarSimpleName);
        en.setPckg(k.getPackageName());
        en.setTypex(KlazzResolveTypex.resolve(k));
        en.setSimpleName(k.getSimpleName());
        en.setFullCanonicalName(k.getCanonicalName());
        en.setFullTypeName(k.getTypeName());
        en.setSubLevel(StringUtils.countMatches(k.getTypeName(), '$'));
        if (StringUtils.isBlank(en.getFullCanonicalName())) {
            en.setFullCanonicalName("???");
        }
        if (loadSource) {
            en.setSrcCode(findKlazzSource(k, sourceFullPath));
        }
        service.addKlazz(en);
    }

    private String findKlazzSource(Class<?> k, String sourceFullPath) {
        try {
            String canName = k.getCanonicalName();
            String canName2 = StringUtils.replaceChars(canName, ".", "/");
            String fname = sourceFullPath + "/" + canName2 + ".java";
            File f = new File(fname);
            if (f.exists()) {
                return FileUtils.readFileToString(f, StandardCharsets.UTF_8);
            }
        } catch(Exception ex) {
            // OK
        }
        return "";
    }

}
