package pl.org.opi.ragdeterm.prompt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class RGFunctionDispatcher {

    private static final Map<String, Function<List<String>, String>> FUNCTIONS =
            new HashMap<>();

    static {
        FUNCTIONS.put("ClassesInheritedAnyLevel", RGFunctions::classesInheritedAnyLevelService);
        FUNCTIONS.put("ClassesInheritedDirectly", RGFunctions::classesInheritedDirectlyService);
        FUNCTIONS.put("IfaceImplementationsAnyLevel", RGFunctions::ifaceImplementationsAnyLevelService);
        FUNCTIONS.put("IfaceImplementationsDirectly", RGFunctions::ifaceImplementationsDirectlyService);
        FUNCTIONS.put("PackageTypesAnyLevel", RGFunctions::packageTypesAnyLevelService);
        FUNCTIONS.put("PackageTypesDirectly", RGFunctions::packageTypesDirectlyService);
        FUNCTIONS.put("SelectedJarPackageTypesAnyLevel", RGFunctions::selectedJarPackageTypesAnyLevelService);
        FUNCTIONS.put("StructureContext", RGFunctions::structureContextService);
        FUNCTIONS.put("CooperationContext", RGFunctions::cooperationContextService);
        FUNCTIONS.put("StruCoopContext", RGFunctions::struCoopContextService);
        FUNCTIONS.put("Concat", RGFunctions::concatService);
    }

    public static Function<List<String>, String> get(String name) {
        return FUNCTIONS.get(name);
    }

}
