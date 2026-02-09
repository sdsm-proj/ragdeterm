package pl.org.opi.ragdeterm.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.org.opi.dbaccess.conn.DbConnConfig;
import pl.org.opi.dbaccess.container.DbConnContainer;
import pl.org.opi.ragdeterm.prompt.PromptProcessor;

import java.util.List;

@Slf4j
@RestController
public class RagDetermController {

    private final ChatClient chatClient;

    public RagDetermController(ChatClient.Builder builder, Environment env) {
        chatClient = builder
                .build();
        DbConnContainer.addDbConn(new DbConnConfig(env.getProperty("conn.id"),
                env.getProperty("conn.url"), env.getProperty("conn.user"),
                env.getProperty("conn.psw"), env.getProperty("conn.driver"),
                false, 20, 180000
        ));
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @GetMapping("/ragdeterm")
    public String chat(@RequestParam(defaultValue = "Hello, how are you?") String query) {
        String preprocessedPrompt = PromptProcessor.process(query);
        log.info("\n\n" + preprocessedPrompt);
        String rslt = chatClient
                .prompt()
                .messages(List.of(new UserMessage(preprocessedPrompt)))
                .call()
                .content();
        log.info("\n\n" + rslt);
        return rslt;
    }

}
