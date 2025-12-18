package pl.org.opi.ragdeterm.prompt;

import pl.org.opi.ragdeterm.db.util.NewLine;
import pl.org.opi.ragdeterm.service.*;
import pl.org.opi.ragdeterm.service.universal.EnumAnswerType;

import java.util.List;

public class RGFunctions {

    public static String classContextService(List<String> args) {
        String canonicalBaseClassName = args.get(0);
        EnumAnswerType answerType = EnumAnswerType.fromString(args.get(1));
        String prefix = NewLine.replaceEscapedNewlines(args.get(2));
        String suffix = NewLine.replaceEscapedNewlines(args.get(3));
        String separator = NewLine.replaceEscapedNewlines(args.get(4));

        ClassContextService service = new ClassContextService();
        return service.exec(canonicalBaseClassName,
                answerType, prefix, suffix, separator);
    }

    public static String classesInheritedAnyLevelService(List<String> args) {
        String canonicalBaseClassName = args.get(0);
        EnumAnswerType answerType = EnumAnswerType.fromString(args.get(1));
        String prefix = NewLine.replaceEscapedNewlines(args.get(2));
        String suffix = NewLine.replaceEscapedNewlines(args.get(3));
        String separator = NewLine.replaceEscapedNewlines(args.get(4));

        ClassesInheritedAnyLevelService service = new ClassesInheritedAnyLevelService();
        return service.exec(canonicalBaseClassName,
                answerType, prefix, suffix, separator);
    }

    public static String classesInheritedDirectlyService(List<String> args) {
        String canonicalBaseClassName = args.get(0);
        EnumAnswerType answerType = EnumAnswerType.fromString(args.get(1));
        String prefix = NewLine.replaceEscapedNewlines(args.get(2));
        String suffix = NewLine.replaceEscapedNewlines(args.get(3));
        String separator = NewLine.replaceEscapedNewlines(args.get(4));

        ClassesInheritedDirectlyService service = new ClassesInheritedDirectlyService();
        return service.exec(canonicalBaseClassName,
                answerType, prefix, suffix, separator);
    }

    public static String ifaceImplementationsAnyLevelService(List<String> args) {
        String canonicalBaseClassName = args.get(0);
        EnumAnswerType answerType = EnumAnswerType.fromString(args.get(1));
        String prefix = NewLine.replaceEscapedNewlines(args.get(2));
        String suffix = NewLine.replaceEscapedNewlines(args.get(3));
        String separator = NewLine.replaceEscapedNewlines(args.get(4));

        IfaceImplementationsAnyLevelService service = new IfaceImplementationsAnyLevelService();
        return service.exec(canonicalBaseClassName,
                answerType, prefix, suffix, separator);
    }

    public static String ifaceImplementationsDirectlyService(List<String> args) {
        String canonicalBaseClassName = args.get(0);
        EnumAnswerType answerType = EnumAnswerType.fromString(args.get(1));
        String prefix = NewLine.replaceEscapedNewlines(args.get(2));
        String suffix = NewLine.replaceEscapedNewlines(args.get(3));
        String separator = NewLine.replaceEscapedNewlines(args.get(4));

        IfaceImplementationsDirectlyService service = new IfaceImplementationsDirectlyService();
        return service.exec(canonicalBaseClassName,
                answerType, prefix, suffix, separator);
    }

    public static String packageClassesAnyLevelService(List<String> args) {
        String canonicalBaseClassName = args.get(0);
        EnumAnswerType answerType = EnumAnswerType.fromString(args.get(1));
        String prefix = NewLine.replaceEscapedNewlines(args.get(2));
        String suffix = NewLine.replaceEscapedNewlines(args.get(3));
        String separator = NewLine.replaceEscapedNewlines(args.get(4));

        PackageClassesAnyLevelService service = new PackageClassesAnyLevelService();
        return service.exec(canonicalBaseClassName,
                answerType, prefix, suffix, separator);
    }

    public static String packageClassesDirectlyService(List<String> args) {
        String canonicalBaseClassName = args.get(0);
        EnumAnswerType answerType = EnumAnswerType.fromString(args.get(1));
        String prefix = NewLine.replaceEscapedNewlines(args.get(2));
        String suffix = NewLine.replaceEscapedNewlines(args.get(3));
        String separator = NewLine.replaceEscapedNewlines(args.get(4));

        PackageClassesDirectlyService service = new PackageClassesDirectlyService();
        return service.exec(canonicalBaseClassName,
                answerType, prefix, suffix, separator);
    }

}
