package pl.org.opi.ragdeterm.service;

import pl.org.opi.ragdeterm.service.universal.EnumAnswerType;
import pl.org.opi.ragdeterm.service.universal.KlazzExtendImplUniversal;

public class ClassesInheritedAnyLevelService {

    public String exec(String canonicalBaseClassName, EnumAnswerType answerType, String prefix, String suffix, String separator) {
        return new KlazzExtendImplUniversal().exec(
                KlazzExtendImplUniversal.EnumExtendImpl.EXTEND, true,
                canonicalBaseClassName, answerType, prefix, suffix, separator
        );
    }

}
