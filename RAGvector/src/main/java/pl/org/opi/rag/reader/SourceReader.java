package pl.org.opi.rag.reader;

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
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Slf4j
@Component
public class SourceReader {

    @Autowired
    private Environment env;

    @Autowired
    VectorStore vectorStore;

    @PostConstruct
    public void load() {
        if (env.getProperty("create.store").trim().equalsIgnoreCase("true")) {
            if (StringUtils.isNotBlank(env.getProperty("source.folder.1"))) {
                iterateJavaSourceFiles(env.getProperty("source.folder.1").trim());
            }
            if (StringUtils.isNotBlank(env.getProperty("source.folder.2"))) {
                iterateJavaSourceFiles(env.getProperty("source.folder.2").trim());
            }
            ((SimpleVectorStore) vectorStore).save(new File(env.getProperty("vector.file.name").trim()));
        } else {
            ((SimpleVectorStore) vectorStore).load(new File(env.getProperty("vector.file.name").trim()));
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
        }
    }

}
