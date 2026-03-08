package pl.org.opi.agentic.llm;

import java.nio.file.Path;
import java.util.List;

public class Config {
    public static String EMBEDDING_MODEL = "text-embedding-3-small";
    public static String LLM_MODEL = "gpt-4o-mini-2024-07-18";
    public static double TEMPERATURE = 0.1;
    public static boolean LOG_REQUESTS = false;
    public static boolean LOG_RESPONSES = false;

    public static int MAX_RESULTS = 30;
    public static double MIN_SCORE = 0.7;

    public static List<Path> DIRECTORIES = List.of(
            Path.of("c:\\ragdeterm\\hierarchy-lib\\src\\main\\java")
    );
    public static String IMPLEMENTATIONS_OF = "TheSameLetters";
}
