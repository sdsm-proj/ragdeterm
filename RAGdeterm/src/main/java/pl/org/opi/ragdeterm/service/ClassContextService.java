package pl.org.opi.ragdeterm.service;

import pl.org.opi.ragdeterm.codeanalyser.inMemKlazzContext.KlazzContextMemGraph;
import pl.org.opi.ragdeterm.codeanalyser.inMemKlazzContext.KlazzCtx;
import pl.org.opi.ragdeterm.repository.klazz.KlazzService;
import pl.org.opi.ragdeterm.service.rslt.RsltId;
import pl.org.opi.ragdeterm.service.rslt.RsltLongName;
import pl.org.opi.ragdeterm.service.rslt.RsltShortName;
import pl.org.opi.ragdeterm.service.rslt.RsltSourceCode;
import pl.org.opi.ragdeterm.service.universal.EnumAnswerType;

import java.util.List;

public class ClassContextService {

    public String exec(String canonicalClassName, EnumAnswerType answerType, String prefix, String suffix, String separator) {
        KlazzService klazzService = new KlazzService();
        var klazzEntity = klazzService.findType(canonicalClassName);

        KlazzContextMemGraph kcmg = new KlazzContextMemGraph();
        KlazzCtx kc = kcmg.createCtx(List.of(
                klazzEntity.getIdUid()
        ));
        var entityList = kc.getKlazzEntityList();
        return switch (answerType) {
            case SHORT_NAME -> new RsltShortName(entityList, prefix, suffix, separator).exec();
            case LONG_NAME -> new RsltLongName(entityList, prefix, suffix, separator).exec();
            case ID -> new RsltId(entityList, prefix, suffix, separator).exec();
            case SOURCE_CODE -> new RsltSourceCode(entityList, prefix, suffix, separator).exec();
        };
    }

}
