package pl.org.opi.ragdeterm.prompt;

import pl.org.opi.dbaccess.util.NewLine;
import pl.org.opi.ragdeterm.service.*;
import pl.org.opi.ragdeterm.service.util.EnumAnswerType;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RGFunctions {

    public static String classesInheritedAnyLevelService(List<String> args) {
        String canonicalBaseClassName = args.get(0);
        String preferredJarSimpleName = NewLine.replaceEscapedNewlines(args.get(1));
        EnumAnswerType answerType = EnumAnswerType.fromString(args.get(2));
        String prefix = NewLine.replaceEscapedNewlines(args.get(3));
        String suffix = NewLine.replaceEscapedNewlines(args.get(4));
        String separator = NewLine.replaceEscapedNewlines(args.get(5));
        ClassesInheritedAnyLevelService service = new ClassesInheritedAnyLevelService();
        return service.exec(canonicalBaseClassName, preferredJarSimpleName,
                answerType, prefix, suffix, separator);
    }

    public static String classesInheritedDirectlyService(List<String> args) {
        String canonicalBaseClassName = args.get(0);
        String preferredJarSimpleName = NewLine.replaceEscapedNewlines(args.get(1));
        EnumAnswerType answerType = EnumAnswerType.fromString(args.get(2));
        String prefix = NewLine.replaceEscapedNewlines(args.get(3));
        String suffix = NewLine.replaceEscapedNewlines(args.get(4));
        String separator = NewLine.replaceEscapedNewlines(args.get(5));
        ClassesInheritedDirectlyService service = new ClassesInheritedDirectlyService();
        return service.exec(canonicalBaseClassName, preferredJarSimpleName,
                answerType, prefix, suffix, separator);
    }

    public static String ifaceImplementationsAnyLevelService(List<String> args) {
        String canonicalBaseClassName = args.get(0);
        String preferredJarSimpleName = NewLine.replaceEscapedNewlines(args.get(1));
        EnumAnswerType answerType = EnumAnswerType.fromString(args.get(2));
        String prefix = NewLine.replaceEscapedNewlines(args.get(3));
        String suffix = NewLine.replaceEscapedNewlines(args.get(4));
        String separator = NewLine.replaceEscapedNewlines(args.get(5));
        IfaceImplementationsAnyLevelService service = new IfaceImplementationsAnyLevelService();
        return service.exec(canonicalBaseClassName, preferredJarSimpleName,
                answerType, prefix, suffix, separator);
    }

    public static String ifaceImplementationsDirectlyService(List<String> args) {
        String canonicalBaseClassName = args.get(0);
        String preferredJarSimpleName = NewLine.replaceEscapedNewlines(args.get(1));
        EnumAnswerType answerType = EnumAnswerType.fromString(args.get(2));
        String prefix = NewLine.replaceEscapedNewlines(args.get(3));
        String suffix = NewLine.replaceEscapedNewlines(args.get(4));
        String separator = NewLine.replaceEscapedNewlines(args.get(5));
        IfaceImplementationsDirectlyService service = new IfaceImplementationsDirectlyService();
        return service.exec(canonicalBaseClassName, preferredJarSimpleName,
                answerType, prefix, suffix, separator);
    }

    public static String packageTypesAnyLevelService(List<String> args) {
        String pckgName = args.get(0);
        EnumAnswerType answerType = EnumAnswerType.fromString(args.get(1));
        String prefix = NewLine.replaceEscapedNewlines(args.get(2));
        String suffix = NewLine.replaceEscapedNewlines(args.get(3));
        String separator = NewLine.replaceEscapedNewlines(args.get(4));
        PackageTypesAnyLevelService service = new PackageTypesAnyLevelService();
        return service.exec(pckgName,
                answerType, prefix, suffix, separator);
    }

    public static String packageTypesDirectlyService(List<String> args) {
        String pckgName = args.get(0);
        EnumAnswerType answerType = EnumAnswerType.fromString(args.get(1));
        String prefix = NewLine.replaceEscapedNewlines(args.get(2));
        String suffix = NewLine.replaceEscapedNewlines(args.get(3));
        String separator = NewLine.replaceEscapedNewlines(args.get(4));
        PackageTypesDirectlyService service = new PackageTypesDirectlyService();
        return service.exec(pckgName,
                answerType, prefix, suffix, separator);
    }

    public static String selectedJarPackageTypesAnyLevelService(List<String> args) {
        String jarSimpleName = args.get(0);
        String pckgName = args.get(1);
        EnumAnswerType answerType = EnumAnswerType.fromString(args.get(2));
        String prefix = NewLine.replaceEscapedNewlines(args.get(3));
        String suffix = NewLine.replaceEscapedNewlines(args.get(4));
        String separator = NewLine.replaceEscapedNewlines(args.get(5));
        SelectedJarPackageTypesAnyLevelService service = new SelectedJarPackageTypesAnyLevelService();
        return service.exec(jarSimpleName, pckgName,
                answerType, prefix, suffix, separator);
    }

    public static String structureContextService(List<String> args) {
        String canonicalBaseClassName = args.get(0);
        String preferredJarSimpleName = NewLine.replaceEscapedNewlines(args.get(1));
        EnumAnswerType answerType = EnumAnswerType.fromString(args.get(2));
        String prefix = NewLine.replaceEscapedNewlines(args.get(3));
        String suffix = NewLine.replaceEscapedNewlines(args.get(4));
        String separator = NewLine.replaceEscapedNewlines(args.get(5));
        StructureContextService service = new StructureContextService();
        return service.exec(canonicalBaseClassName, preferredJarSimpleName,
                answerType, prefix, suffix, separator);
    }

    public static String cooperationContextService(List<String> args) {
        String canonicalBaseClassName = args.get(0);
        String preferredJarSimpleName = NewLine.replaceEscapedNewlines(args.get(1));
        EnumAnswerType answerType = EnumAnswerType.fromString(args.get(2));
        String prefix = NewLine.replaceEscapedNewlines(args.get(3));
        String suffix = NewLine.replaceEscapedNewlines(args.get(4));
        String separator = NewLine.replaceEscapedNewlines(args.get(5));
        CooperationContextService service = new CooperationContextService();
        return service.exec(canonicalBaseClassName, preferredJarSimpleName,
                answerType, prefix, suffix, separator);
    }

    public static String struCoopContextService(List<String> args) {
        String canonicalBaseClassName = args.get(0);
        String preferredJarSimpleName = NewLine.replaceEscapedNewlines(args.get(1));
        EnumAnswerType answerType = EnumAnswerType.fromString(args.get(2));
        String prefix = NewLine.replaceEscapedNewlines(args.get(3));
        String suffix = NewLine.replaceEscapedNewlines(args.get(4));
        String separator = NewLine.replaceEscapedNewlines(args.get(5));
        StruCoopContextService service = new StruCoopContextService();
        return service.exec(canonicalBaseClassName, preferredJarSimpleName,
                answerType, prefix, suffix, separator);
    }

    public static String concatService(List<String> args) {
        return args.stream().map(Object::toString).collect(Collectors.joining());
    }

}
