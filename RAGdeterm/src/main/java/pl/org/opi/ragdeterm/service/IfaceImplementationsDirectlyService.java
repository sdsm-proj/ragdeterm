package pl.org.opi.ragdeterm.service;

import pl.org.opi.ragdeterm.service.universal.EnumAnswerType;
import pl.org.opi.ragdeterm.service.universal.KlazzExtendImplUniversal;

public class IfaceImplementationsDirectlyService {

    public String exec(String canonicalIfaceClassName, EnumAnswerType answerType, String prefix, String suffix, String separator) {
        return new KlazzExtendImplUniversal().exec(
                KlazzExtendImplUniversal.EnumExtendImpl.IMPL, false,
                canonicalIfaceClassName, answerType, prefix, suffix, separator
        );
    }

}
