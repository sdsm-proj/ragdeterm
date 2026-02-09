package pl.org.opi.ragnaive.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Slf4j
public class PromptLoggingAdvisor implements CallAdvisor {

    public static String path = "C:\\ragdeterm\\ai-client-naive\\docs\\" +
            "experiment-2-data\\embedding-3-large\\" +
            "similarityThreshold-08";
    public static String emb = "3-large";
    public static double sTh = 0.8;
    public static int topK = 1000;
    public static int trial = 1;

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {

        String fileName = String.format(
                Locale.US,
                "emb-%s-sTh-%.1f-topK-%d-trial-%d.txt",
                emb, sTh, topK, trial
        );
        String fullFileName = path + "\\" + fileName;
        String text = chatClientRequest.prompt().toString();
        try {
            FileUtils.writeStringToFile(new File(fullFileName), text, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ChatClientResponse chatClientResponse = callAdvisorChain.nextCall(chatClientRequest);
        return chatClientResponse;
    }

    @Override
    public String getName() {
        return "PromptLoggingAdvisor";
    }

    @Override
    public int getOrder() {
        return 100;
    }

}
