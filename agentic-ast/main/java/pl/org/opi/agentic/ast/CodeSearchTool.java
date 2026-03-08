package pl.org.opi.agentic.ast;

import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.EmbeddingStore;

import java.util.List;

public class CodeSearchTool {

    private final EmbeddingStore<TextSegment> store;
    private final EmbeddingModel embeddingModel;
    private final int maxResults;
    private final double minScore;

    public CodeSearchTool(EmbeddingStore<TextSegment> store,
                          EmbeddingModel embeddingModel, int maxResults, double minScore) {
        this.store = store;
        this.embeddingModel = embeddingModel;
        this.maxResults = maxResults;
        this.minScore = minScore;
    }

    @Tool("Semantic search in Java source code")
    public List<String> search(String query) {

        Embedding queryEmbedding = embeddingModel.embed(query).content();
        EmbeddingSearchRequest request = EmbeddingSearchRequest.builder()
                .queryEmbedding(queryEmbedding)
                .maxResults(maxResults)
                .minScore(minScore)
                .build();
        EmbeddingSearchResult<TextSegment> result = store.search(request);
        var rsltList = result.matches()
                .stream()
                .map(match -> match.embedded().text())
                .toList();

        return rsltList;
    }

}