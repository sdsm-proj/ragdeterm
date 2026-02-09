package pl.org.opi.ragdeterm.service;

import pl.org.opi.ragdeterm.repo.inheritance.EnumExtendImpl;
import pl.org.opi.ragdeterm.service.base.KlazzExtendImplUniversal;
import pl.org.opi.ragdeterm.service.util.EnumAnswerType;

public class ClassesInheritedDirectlyService {

    public String exec(String canonicalBaseClassName, String preferredJarSimpleName, EnumAnswerType answerType, String prefix, String suffix, String separator) {
        return new KlazzExtendImplUniversal().exec(
                EnumExtendImpl.EXTEND, false,
                canonicalBaseClassName, preferredJarSimpleName,
                answerType, prefix, suffix, separator
        );
    }

}
