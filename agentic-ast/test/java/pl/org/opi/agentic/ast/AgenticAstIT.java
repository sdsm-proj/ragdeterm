package pl.org.opi.agentic.ast;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AgenticAstIT {

    @Test
    void runTest() {
        EmbeddingModel embeddingModel =
                OpenAiEmbeddingModel.builder()
                        .apiKey(API_KEY.VALUE)
                        .modelName(Config.EMBEDDING_MODEL)
                        .build();

        EmbeddingStore<TextSegment> embeddingStore =
                new InMemoryEmbeddingStore<>();

        List<Document> allDocuments = new ArrayList<>();
        for (Path dir : Config.DIRECTORIES) {
            allDocuments.addAll(Utils.loadJavaDocumentsRecursively(dir));
        }
        for (Document document : allDocuments) {
            TextSegment segment = TextSegment.from(
                    document.text(),
                    document.metadata()
            );
            Embedding embedding = embeddingModel.embed(segment).content();
            embeddingStore.add(embedding, segment);
        }

        CodeSearchTool searchTool = new CodeSearchTool(
                embeddingStore, embeddingModel,
                Config.MAX_RESULTS, Config.MIN_SCORE);

        JavaTypeInfoExtractor extractor = new JavaTypeInfoExtractor();
        Orchestrator orchestrator = new Orchestrator(searchTool, extractor);

        long start = System.nanoTime();
        List<JavaType> result = orchestrator.findAllImplementations(Config.IMPLEMENTATIONS_OF);
        long end = System.nanoTime();
        double seconds = (end - start) / 1_000_000_000.0;

        System.out.printf("Execution time: %.3f s%n", seconds);
        System.out.println("Result size: " + result.size());

        result.sort(Comparator.comparing(JavaType::typeName));
        for(JavaType t: result) {
            System.out.println(t.typeName());
        }

    }

}