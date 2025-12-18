package pl.org.opi.ragdeterm.codeanalyser.classdepot;

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

@Slf4j
public class UrlClassLoaderHelper {

    private final URLClassLoader parentClassLoader;

    public List<File> jarPath2Files(List<String> jarFullPathList) {
        List<File> rslt = new ArrayList<>();
        for (String jarFullPath : jarFullPathList) {
            rslt.add(new File(jarFullPath));
        }
        return rslt;
    }

    public UrlClassLoaderHelper(List<String> jarFullPathList) throws MalformedURLException {
        List<File> jarFiles = jarPath2Files(jarFullPathList);

        List<URL> urlList = new ArrayList<>();
        for (var file : jarFiles) {
            urlList.add(file.toURI().toURL());
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
            if (jarAnyName.equalsIgnoreCase("JDK")) {
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
