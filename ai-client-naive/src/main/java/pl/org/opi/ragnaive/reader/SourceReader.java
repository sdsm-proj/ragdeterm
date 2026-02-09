package pl.org.opi.ragnaive.reader;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class SourceReader {

    @Autowired
    private Environment env;

    @Autowired
    private VectorStore vectorStore;

    private static int javaFilesTotal = 0;

    @PostConstruct
    public void load() {
        if (env.getProperty("create.store").equalsIgnoreCase("true")) {
            findSourceFolders().forEach((key, value) -> {
                iterateJavaSourceFiles(("" + value).trim());
            });
            ((SimpleVectorStore) vectorStore).save(new File(env.getProperty("vector.file.name")));
            log.info("javaFilesTotal: " + javaFilesTotal);
        } else {
            ((SimpleVectorStore) vectorStore).load(new File(env.getProperty("vector.file.name")));
        }
    }

    private void iterateJavaSourceFiles(String absolutePath) {
        String[] ext = {"java"};
        var files = FileUtils.listFiles(new File(absolutePath), ext, true);
        for(var file: files) {
            log.info(file.getAbsolutePath());
            TextReader textReader = new TextReader(new FileSystemResource(file));
            List<Document> documents = textReader.get();
            var textSplitter = new TokenTextSplitter();
            vectorStore.accept(textSplitter.apply(documents));
            javaFilesTotal++;
        }
    }

    public Map<String, Object> findSourceFolders() {
        Map<String, Object> result = new HashMap<>();
        if (env instanceof ConfigurableEnvironment configurableEnv) {
            for (PropertySource<?> ps : configurableEnv.getPropertySources()) {
                if (ps instanceof EnumerablePropertySource<?> eps) {
                    for (String name : eps.getPropertyNames()) {
                        if (name.startsWith("source.folder")) {
                            result.put(name, eps.getProperty(name));
                        }
                    }
                }
            }
        }
        return result;
    }

}
