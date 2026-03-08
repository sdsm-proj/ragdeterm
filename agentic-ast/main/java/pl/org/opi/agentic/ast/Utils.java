package pl.org.opi.agentic.ast;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Utils {

    public static List<Document> loadJavaDocumentsRecursively(Path rootDir) {
        List<Document> documents = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(rootDir)) {
            paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().toLowerCase().endsWith(".java"))
                    .forEach(path -> {
                        Document doc = FileSystemDocumentLoader.loadDocument(
                                path,
                                new TextDocumentParser()
                        );
                        documents.add(doc);
                    });
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
        return documents;
    }

}
