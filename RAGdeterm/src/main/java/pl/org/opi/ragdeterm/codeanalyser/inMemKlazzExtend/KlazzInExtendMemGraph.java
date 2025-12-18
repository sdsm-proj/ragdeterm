package pl.org.opi.ragdeterm.codeanalyser.inMemKlazzExtend;

import lombok.Getter;
import pl.org.opi.ragdeterm.repository.klazz.KlazzEntity;
import pl.org.opi.ragdeterm.repository.klazz.KlazzService;
import pl.org.opi.ragdeterm.repository.klazzExtend.KlazzExtendEntity;
import pl.org.opi.ragdeterm.repository.klazzExtend.KlazzExtendService;
import pl.org.opi.ragdeterm.repository.klazzExtend.KlazzExtendStrings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class KlazzInExtendMemGraph {
    private final Map<String, KlazzNode> bigKlazzMap = new HashMap<>();

    public void longLoadProcess() {
        bigKlazzMap.clear();

        KlazzService klazzService = new KlazzService();
        List<KlazzEntity> klazzList = klazzService.findAll();
        for (var klazz: klazzList) {
            KlazzNode klazzNode = new KlazzNode(klazz.getIdUid(), klazz.getJarSimpleName(), klazz.getSimpleName(), klazz.getFullCanonicalName());
            bigKlazzMap.put(klazzNode.getKlazzId(), klazzNode);
        }

        KlazzExtendService klazzExtendService = new KlazzExtendService();
        List<KlazzExtendEntity> extendList = klazzExtendService.findAll();
        for (var exte: extendList) {

            KlazzNode klazzNode = bigKlazzMap.get(exte.getIdFrom());
            if (klazzNode == null) {
                continue;
            }

            KlazzNode klazzNodeTo = bigKlazzMap.get(exte.getIdTo());
            if (klazzNodeTo == null) {
                continue;
            }

            KlazzSubNode sub = new KlazzSubNode(exte.getRelType(), klazzNodeTo);
            klazzNode.getSubs().put(klazzNodeTo.getKlazzId(), sub);
        }
    }

    public List<KlazzExtendStrings> makeAllExtends() {
        List<KlazzExtendStrings> ksList = new ArrayList<>();
        for(var klazz: bigKlazzMap.values()) {
            if ("JDK".equalsIgnoreCase(klazz.getJarSimpleName())) {
                continue;
            }
            KlazzExtendStrings ks = makeOneExtend(klazz);
            ksList.add(ks);
        }
        return ksList;
    }

    private KlazzExtendStrings makeOneExtend(KlazzNode klazzNode) {
        KlazzExtendStrings ks = new KlazzExtendStrings();
        ks.setKlazzId(klazzNode.getKlazzId());
        ks.setSimpleName(klazzNode.getSimpleName());
        ks.setFullCanonicalName(klazzNode.getFullCanonicalName());
        _fillSubsRqr(ks, klazzNode);
        return ks;
    }

    private void _fillSubsRqr(KlazzExtendStrings ks, KlazzNode klazzNode) {
        for(var sub: klazzNode.getSubs().values()) {
            if (sub.getRelType().equals(KlazzExtendEntity.EnumRelType.EXTEND.toString())) {
                ks.appendExtendSimpleNames(sub.getKlazzNode().getSimpleName());
                ks.appendExtendFullNames(sub.getKlazzNode().getFullCanonicalName());
                ks.appendExtendIds(sub.getKlazzNode().getKlazzId());
            } else if (sub.getRelType().equals(KlazzExtendEntity.EnumRelType.IMPL.toString())) {
                ks.appendImplSimpleNames(sub.getKlazzNode().getSimpleName());
                ks.appendImplFullNames(sub.getKlazzNode().getFullCanonicalName());
                ks.appendImplIds(sub.getKlazzNode().getKlazzId());
            }
            _fillSubsRqr(ks,sub.getKlazzNode());
        }
    }

}
