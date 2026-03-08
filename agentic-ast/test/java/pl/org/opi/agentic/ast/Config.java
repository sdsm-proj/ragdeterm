package pl.org.opi.agentic.ast;

import java.nio.file.Path;
import java.util.List;

public class Config {
    public static String EMBEDDING_MODEL = "text-embedding-3-small";

    public static int MAX_RESULTS = 30;
    public static double MIN_SCORE = 0.7;

    public static List<Path> DIRECTORIES = List.of(
            Path.of("c:\\ragdeterm\\hierarchy-lib\\src\\main\\java")
    );
    public static String IMPLEMENTATIONS_OF = "TheSameLetters";
}
