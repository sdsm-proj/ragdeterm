package pl.org.opi.agentic.llm;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface JavaInheritanceExtractor {

    @SystemMessage("""
            Extract structural information from Java class or interface source code.
            
            Return a JSON object with the following fields:
            - pckg (full package name or null if none)
            - typeName (name of the class or interface)
            - superType
            - implementedInterfaces (empty list if none)
            
            Definitions:
            - If the type is a class:
              - superType = the class specified after the `extends` keyword (null if none)
              - implementedInterfaces = all interfaces listed after the `implements` keyword
            
            - If the type is an interface:
              - superType = the first interface listed after the `extends` keyword (null if none)
              - implementedInterfaces = all additional interfaces listed after the `extends` keyword beyond the first one (empty list if none)
            
            Important:
            - For interfaces, the `extends` keyword defines superinterfaces.
            - Do not ignore `extends` in interfaces.
            - Do not guess missing information.
            - If the input does not define a class or interface, return all fields as null.
            
            Return only valid JSON. Do not include explanations.        
        """)
    JavaType extract(@UserMessage String javaSourceCode);

}

