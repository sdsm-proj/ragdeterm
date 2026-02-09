package pl.org.opi.ragdeterm.klazzdepot;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static pl.org.opi.ragdeterm.consta.Consta.JDK_PREFIX;

@Getter
@Slf4j
public class UrlClassLoaderHelper {

    private final URLClassLoader parentClassLoader;
    private final List<String> jarFullPathList;
    private final List<File> jarFiles = new ArrayList<>();
    private final List<String> jarSimpleNames = new ArrayList<>();

    public UrlClassLoaderHelper(List<String> aJarFullPathList) throws MalformedURLException {
        jarFullPathList = aJarFullPathList;
        for (String jarFullPath : jarFullPathList) {
            jarFiles.add(new File(jarFullPath));
        }

        List<URL> urlList = new ArrayList<>();
        for (var file : jarFiles) {
            urlList.add(file.toURI().toURL());
            jarSimpleNames.add(StringUtils.removeEndIgnoreCase(file.getName(), ".jar"));
        }

        URL[] urls = urlList.toArray(new URL[0]);
        parentClassLoader = new URLClassLoader(urls);
    }

    public URLClassLoader getOrderedClassLoader(String topJarAnyName) {
        return getOrderedClassLoader(List.of(topJarAnyName));
    }

    public URLClassLoader getOrderedClassLoader(List<String> topJarAnyNameList) {
        List<URL> orderedUrlList = getOrderedUrlList(topJarAnyNameList);
        if (orderedUrlList.isEmpty()) {
            return parentClassLoader;
        } else {
            List<URL> tmpUrlList = new ArrayList<>();
            tmpUrlList.addAll(orderedUrlList);
            tmpUrlList.addAll(Arrays.asList(parentClassLoader.getURLs()));
            URL[] urls = tmpUrlList.toArray(new URL[0]);
            return URLClassLoader.newInstance(urls);
        }
    }

    private List<URL> getOrderedUrlList(List<String> topJarAnyNameList) {
        List<URL> rslt = new ArrayList<>();
        for (String jarAnyName : topJarAnyNameList) {
            if (StringUtils.isBlank(jarAnyName)) {
                continue;
            }
            if (jarAnyName.startsWith(JDK_PREFIX)) {
                continue;
            }
            String tmpName = jarAnyName.trim();
            if (!tmpName.toLowerCase().endsWith(".jar")) {
                jarAnyName += ".jar";
            }
            String seekJarBaseName = FilenameUtils.getBaseName(jarAnyName);
            for (var parentUrl : parentClassLoader.getURLs()) {
                String baseUrlFileName = FilenameUtils.getBaseName(parentUrl.getFile());
                if (seekJarBaseName.equalsIgnoreCase(baseUrlFileName)) {
                    rslt.add(parentUrl);
                }
            }
        }
        return rslt;
    }

}
