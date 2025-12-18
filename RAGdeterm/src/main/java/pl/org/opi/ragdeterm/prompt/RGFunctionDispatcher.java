package pl.org.opi.ragdeterm.prompt;

import java.util.*;
import java.util.function.Function;

public class RGFunctionDispatcher {

    private static final Map<String, Function<List<String>, String>> FUNCTIONS =
            new HashMap<>();

    static {
        FUNCTIONS.put("ClassContext", RGFunctions::classContextService);
        FUNCTIONS.put("ClassesInheritedAnyLevel", RGFunctions::classesInheritedAnyLevelService);
        FUNCTIONS.put("ClassesInheritedDirectly", RGFunctions::classesInheritedDirectlyService);
        FUNCTIONS.put("IfaceImplementationsAnyLevel", RGFunctions::ifaceImplementationsAnyLevelService);
        FUNCTIONS.put("IfaceImplementationsDirectly", RGFunctions::ifaceImplementationsDirectlyService);
        FUNCTIONS.put("PackageClassesAnyLevel", RGFunctions::packageClassesAnyLevelService);
        FUNCTIONS.put("PackageClassesDirectly", RGFunctions::packageClassesDirectlyService);
    }

    public static Function<List<String>, String> get(String name) {
        return FUNCTIONS.get(name);
    }
}
