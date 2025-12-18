package pl.org.opi.rag.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RagChatController {

    private final ChatClient chatClient;

    public RagChatController(ChatClient.Builder builder, VectorStore vectorStore) {
        QuestionAnswerAdvisor questionAnswerAdvisor = QuestionAnswerAdvisor.builder(vectorStore)
                .searchRequest(
                        SearchRequest.builder()
                                .similarityThreshold(0.2d)
                                .topK(1000)
                                .build()
                )
                .build();
        this.chatClient = builder
                .defaultAdvisors(questionAnswerAdvisor)
                .build();
    }

    @GetMapping("/rag")
    public String chat(@RequestParam(defaultValue = "Hello, how are you?") String query) {
        return chatClient
                .prompt()
                .messages(List.of(new UserMessage(query)))
                .call()
                .content();
    }

}
