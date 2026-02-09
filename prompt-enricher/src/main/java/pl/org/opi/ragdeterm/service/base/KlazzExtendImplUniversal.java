package pl.org.opi.ragdeterm.service.base;

import lombok.extern.slf4j.Slf4j;
import pl.org.opi.dbaccess.container.DbConnContainer;
import pl.org.opi.dbaccess.exception.DbException;
import pl.org.opi.ragdeterm.repo.inheritance.EnumExtendImpl;
import pl.org.opi.ragdeterm.repo.inheritance.InheritanceEntity;
import pl.org.opi.ragdeterm.repo.inheritance.InheritanceRepox;
import pl.org.opi.ragdeterm.repo.klazz.KlazzEntity;
import pl.org.opi.ragdeterm.repo.klazz.KlazzRepox;
import pl.org.opi.ragdeterm.service.util.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class KlazzExtendImplUniversal {

    public String exec(EnumExtendImpl extImp, boolean recursive,
                       String canonicalBaseClassName, String preferredJarSimpleName,
                       EnumAnswerType answerType, String prefix, String suffix, String separator) {
        try (var trx = DbConnContainer.newTrx()) {
            var klazzRepox = new KlazzRepox(trx);
            var inheritanceRepox = new InheritanceRepox(trx);
            return execCore(klazzRepox, inheritanceRepox,
                    extImp, recursive,
                    canonicalBaseClassName, preferredJarSimpleName,
                    answerType, prefix, suffix, separator);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new DbException(ex.getMessage(), ex);
        }
    }

    private String execCore(KlazzRepox klazzRepox,
                            InheritanceRepox inheritanceRepox,
                            EnumExtendImpl extImp, boolean recursive,
                            String canonicalBaseClassName, String preferredJarSimpleName,
                            EnumAnswerType answerType, String prefix, String suffix, String separator) {
        List<KlazzEntity> rslt = new ArrayList<>();

        KlazzEntity firstKlazzByName = klazzRepox.findFirstKlazzByNamePreferredJar(canonicalBaseClassName, preferredJarSimpleName);
        List<InheritanceEntity> inheritanceEntityList = inheritanceRepox.findAll();
        for(var inh: inheritanceEntityList) {
            if (inh.getIdTo().equals(firstKlazzByName.getIdUid()) && inh.getRelType().equalsIgnoreCase(extImp.toString())) {
                var found = klazzRepox.findByKey(inh.getIdFrom());
                if (found != null) {
                    rslt.add(found);
                    if (recursive) {
                        goDeepRcr(extImp, found, rslt, inheritanceEntityList, klazzRepox);
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

    private void goDeepRcr(EnumExtendImpl extImp, KlazzEntity klazz, List<KlazzEntity> rslt, List<InheritanceEntity> inheritanceEntityList, KlazzRepox klazzRepox) {
        for (var inh : inheritanceEntityList) {
            if (inh.getIdTo().equals(klazz.getIdUid()) && inh.getRelType().equalsIgnoreCase(extImp.toString())) {
                var found = klazzRepox.findByKey(inh.getIdFrom());
                if (found != null) {
                    rslt.add(found);
                    goDeepRcr(extImp, found, rslt, inheritanceEntityList, klazzRepox);
                }
            }
        }
    }

}
