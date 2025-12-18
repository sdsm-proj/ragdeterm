package pl.org.opi.ragdeterm.service.universal;

import pl.org.opi.ragdeterm.db.exception.DbException;
import pl.org.opi.ragdeterm.repository.klazz.KlazzEntity;
import pl.org.opi.ragdeterm.repository.klazz.KlazzService;
import pl.org.opi.ragdeterm.repository.klazzExtend.KlazzExtendEntity;
import pl.org.opi.ragdeterm.repository.klazzExtend.KlazzExtendService;
import pl.org.opi.ragdeterm.service.rslt.RsltLongName;
import pl.org.opi.ragdeterm.service.rslt.RsltId;
import pl.org.opi.ragdeterm.service.rslt.RsltShortName;
import pl.org.opi.ragdeterm.service.rslt.RsltSourceCode;

import java.util.ArrayList;
import java.util.List;

public class KlazzExtendImplUniversal {

    public enum EnumExtendImpl {
        EXTEND, IMPL
    }

    public String exec(EnumExtendImpl extImp, boolean recursive, String canonicalBaseClassName, EnumAnswerType answerType, String prefix, String suffix, String separator) {
        try {
            return execCore(extImp, recursive, canonicalBaseClassName , answerType, prefix, suffix, separator);
        } catch(Exception ex) {
            return ex.getMessage();
        }
    }

    private String execCore(EnumExtendImpl extImp, boolean recursive, String canonicalBaseClassName, EnumAnswerType answerType, String prefix, String suffix, String separator) {
        List<KlazzEntity> rslt = new ArrayList<>();

        KlazzService klazzService = new KlazzService();
        KlazzEntity baseKlazzEntity = klazzService.findType(canonicalBaseClassName);
        if (baseKlazzEntity == null) {
            throw new DbException("Base class/iface not found: " + canonicalBaseClassName);
        }

        KlazzExtendService klazzExtendService = new KlazzExtendService();
        List<KlazzExtendEntity> klazzExtendList = klazzExtendService.findAll();
        for(var kex: klazzExtendList) {
            if (kex.getIdTo().equals(baseKlazzEntity.getIdUid()) && kex.getRelType().equalsIgnoreCase(extImp.toString())) {
                var found = klazzService.findById(kex.getIdFrom());
                if (found != null) {
                    rslt.add(found);
                    if (recursive) {
                        goDeepRcr(extImp, found, rslt, klazzExtendList, klazzService);
                    }
                }
            }
        }

        return switch (answerType) {
            case SHORT_NAME -> new RsltShortName(rslt, prefix, suffix, separator).exec();
            case LONG_NAME -> new RsltLongName(rslt, prefix, suffix, separator).exec();
            case ID -> new RsltId(rslt, prefix, suffix, separator).exec();
            case SOURCE_CODE -> new RsltSourceCode(rslt, prefix, suffix, separator).exec();
        };

    }

    private void goDeepRcr(EnumExtendImpl extImp, KlazzEntity klazz, List<KlazzEntity> rslt, List<KlazzExtendEntity> klazzExtendList, KlazzService klazzService) {
        for(var kex: klazzExtendList) {
            if (kex.getIdTo().equals(klazz.getIdUid()) && kex.getRelType().equalsIgnoreCase(extImp.toString())) {
                var found = klazzService.findById(kex.getIdFrom());
                if (found != null) {
                    rslt.add(found);
                    goDeepRcr(extImp, found, rslt, klazzExtendList, klazzService);
                }
            }
        }
    }

}
