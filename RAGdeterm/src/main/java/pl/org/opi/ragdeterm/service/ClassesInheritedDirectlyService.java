package pl.org.opi.ragdeterm.service;

import pl.org.opi.ragdeterm.service.universal.EnumAnswerType;
import pl.org.opi.ragdeterm.service.universal.KlazzExtendImplUniversal;

public class ClassesInheritedDirectlyService {

    public String exec(String canonicalBaseClassName, EnumAnswerType answerType, String prefix, String suffix, String separator) {
        return new KlazzExtendImplUniversal().exec(
                KlazzExtendImplUniversal.EnumExtendImpl.EXTEND, false,
                canonicalBaseClassName, answerType, prefix, suffix, separator
        );
    }

}
