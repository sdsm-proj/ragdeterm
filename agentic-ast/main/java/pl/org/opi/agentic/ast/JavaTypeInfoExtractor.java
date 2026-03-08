package pl.org.opi.agentic.ast;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JavaTypeInfoExtractor {

    public JavaType extract(String javaSourceCode) {

        CompilationUnit cu;
        try {
            cu = StaticJavaParser.parse(javaSourceCode);
        } catch (Exception e) {
            return new JavaType(null, null, null, null, javaSourceCode);
        }

        String pkg = cu.getPackageDeclaration()
                .map(p -> p.getNameAsString())
                .orElse(null);

        Optional<ClassOrInterfaceDeclaration> typeOpt =
                cu.findFirst(ClassOrInterfaceDeclaration.class);

        if (typeOpt.isEmpty()) {
            return new JavaType(null, null, null, null, javaSourceCode);
        }

        ClassOrInterfaceDeclaration type = typeOpt.get();

        String typeName = type.getNameAsString();
        String superType = null;
        List<String> implementedInterfaces = new ArrayList<>();

        if (type.isInterface()) {
            List<String> extended = type.getExtendedTypes()
                    .stream()
                    .map(t -> t.getNameAsString())
                    .toList();

            if (!extended.isEmpty()) {

                superType = extended.get(0);

                if (extended.size() > 1) {
                    implementedInterfaces.addAll(extended.subList(1, extended.size()));
                }
            }
        } else  {
            // extends
            if (!type.getExtendedTypes().isEmpty()) {
                superType = type.getExtendedTypes().get(0).getNameAsString();
            }

            // implements
            type.getImplementedTypes()
                    .forEach(i -> implementedInterfaces.add(i.getNameAsString()));

        }

        return new JavaType(
                pkg,
                typeName,
                superType,
                implementedInterfaces,
                javaSourceCode
        );
    }
}
