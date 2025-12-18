package pl.org.opi.ragdeterm.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.org.opi.ragdeterm.prompt.PromptProcessor;

import java.util.List;

@RestController
public class RagChatController {

    private final ChatClient chatClient;

    public RagChatController(ChatClient.Builder builder) {
        this.chatClient = builder
                .build();
    }

    @GetMapping("/rag")
    public String chat(@RequestParam(defaultValue = "Hello, how are you?") String query) {
        String preprocessedPrompt = PromptProcessor.process(query);

        System.out.println(preprocessedPrompt);

        return chatClient
                .prompt()
                .messages(List.of(new UserMessage(preprocessedPrompt)))
                .call()
                .content();
    }

}
