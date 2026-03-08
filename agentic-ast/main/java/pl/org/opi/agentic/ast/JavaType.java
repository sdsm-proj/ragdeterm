package pl.org.opi.agentic.ast;

import java.util.List;

public record JavaType(
        String pckg,
        String typeName,
        String superType,
        List<String> implementedInterfaces,
        String sourceCode
) {
}