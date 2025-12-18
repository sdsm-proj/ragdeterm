package pl.org.opi.ragdeterm.service.universal;

import pl.org.opi.ragdeterm.db.exception.DbException;
import pl.org.opi.ragdeterm.repository.klazz.KlazzEntity;
import pl.org.opi.ragdeterm.repository.klazz.KlazzService;
import pl.org.opi.ragdeterm.repository.klazzExtend.KlazzExtendEntity;
import pl.org.opi.ragdeterm.repository.klazzExtend.KlazzExtendService;

import java.util.ArrayList;
import java.util.List;

public class KlazzBase {

    public List<KlazzEntity> exec(boolean recursive, String canonicalClassName) {
        try {
            return execCore(recursive, canonicalClassName);
        } catch(Exception ex) {
            return null;
        }
    }

    private List<KlazzEntity> execCore(boolean recursive, String canonicalClassName) {
        List<KlazzEntity> rslt = new ArrayList<>();

        KlazzService klazzService = new KlazzService();
        KlazzEntity klazzEntity = klazzService.findType(canonicalClassName);
        if (klazzEntity == null) {
            throw new DbException("Class/iface not found: " + canonicalClassName);
        }

        KlazzExtendService klazzExtendService = new KlazzExtendService();
        List<KlazzExtendEntity> klazzExtendList = klazzExtendService.findAll();
        for(var kex: klazzExtendList) {
            if (kex.getIdFrom().equals(klazzEntity.getIdUid())) {
                var found = klazzService.findById(kex.getIdTo());
                if (found != null) {
                    rslt.add(found);
                    if (recursive) {
                        goDeepRcr(found, rslt, klazzExtendList, klazzService);
                    }
                }
            }
        }

        return rslt;
    }

    private void goDeepRcr(KlazzEntity klazz, List<KlazzEntity> rslt, List<KlazzExtendEntity> klazzExtendList, KlazzService klazzService) {
        for(var kex: klazzExtendList) {
            if (kex.getIdFrom().equals(klazz.getIdUid())) {
                var found = klazzService.findById(kex.getIdTo());
                if (found != null) {
                    rslt.add(found);
                    goDeepRcr(found, rslt, klazzExtendList, klazzService);
                }
            }
        }
    }

}
