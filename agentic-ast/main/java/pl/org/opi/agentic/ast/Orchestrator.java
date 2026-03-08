package pl.org.opi.agentic.ast;

import java.util.ArrayList;
import java.util.List;

public class Orchestrator {

    private final CodeSearchTool searchTool;
    private final JavaTypeInfoExtractor extractor;

    public Orchestrator(CodeSearchTool searchTool, JavaTypeInfoExtractor extractor) {
        this.searchTool = searchTool;
        this.extractor = extractor;
    }

    public List<JavaType> findAllImplementations(String typeName) {
        List<JavaType> foundTypes = new ArrayList<>();
        findRecursively(typeName, foundTypes);
        return foundTypes;
    }

    private void findRecursively(String typeName, List<JavaType> foundTypes) {
        List<String> candidates = searchTool.search("type implementing or extending " + typeName);
        for (String code : candidates) {
            JavaType javaType = extractor.extract(code);
            if (typeFits(typeName, javaType)) {
                foundTypes.add(javaType);
                findRecursively(javaType.typeName(), foundTypes);
            }
        }
    }

    private boolean typeFits(String typeName, JavaType javaType) {
        if (javaType == null) {
            return false;
        }
        if ((javaType.implementedInterfaces() != null) && (javaType.implementedInterfaces().contains(typeName))) {
            return true;
        }
        return typeName.equals(javaType.superType());
    }

}
