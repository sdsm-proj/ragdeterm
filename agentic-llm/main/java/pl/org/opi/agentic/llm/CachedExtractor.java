package pl.org.opi.agentic.llm;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CachedExtractor implements JavaInheritanceExtractor {

    public static int llmCallCount = 0;

    private final JavaInheritanceExtractor delegate;

    private final Map<String, JavaType> cache = new ConcurrentHashMap<>();

    public CachedExtractor(JavaInheritanceExtractor delegate) {
        this.delegate = delegate;
    }

    @Override
    public JavaType extract(String javaSourceCode) {
        String hash = hash(javaSourceCode);
        return cache.computeIfAbsent(hash, h -> {
            llmCallCount++;
            return delegate.extract(javaSourceCode);
        });
    }

    private String hash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(
                    input.getBytes(StandardCharsets.UTF_8)
            );
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}