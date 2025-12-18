package pl.org.opi.ragdeterm.service;

import pl.org.opi.ragdeterm.repository.klazz.KlazzEntity;
import pl.org.opi.ragdeterm.repository.klazz.KlazzService;
import pl.org.opi.ragdeterm.service.rslt.RsltId;
import pl.org.opi.ragdeterm.service.rslt.RsltLongName;
import pl.org.opi.ragdeterm.service.rslt.RsltShortName;
import pl.org.opi.ragdeterm.service.rslt.RsltSourceCode;
import pl.org.opi.ragdeterm.service.universal.EnumAnswerType;

import java.util.ArrayList;
import java.util.List;

public class PackageClassesAnyLevelService {

    public String exec(String pckgName, EnumAnswerType answerType, String prefix, String suffix, String separator) {
        try {
            return execCore(pckgName, answerType, prefix, suffix, separator);
        } catch(Exception ex) {
            return ex.getMessage();
        }
    }

    private String execCore(String pckg, EnumAnswerType answerType, String prefix, String suffix, String separator) {
        List<KlazzEntity> rslt = new ArrayList<>();

        KlazzService klazzService = new KlazzService();
        List<KlazzEntity> klazzAll = klazzService.findAll();

        for(var k: klazzAll) {
            if (k.getPckg().startsWith(pckg)) {
                rslt.add(k);
            }
        }

        return switch (answerType) {
            case SHORT_NAME -> new RsltShortName(rslt, prefix, suffix, separator).exec();
            case LONG_NAME -> new RsltLongName(rslt, prefix, suffix, separator).exec();
            case ID -> new RsltId(rslt, prefix, suffix, separator).exec();
            case SOURCE_CODE -> new RsltSourceCode(rslt, prefix, suffix, separator).exec();
        };
    }

}
