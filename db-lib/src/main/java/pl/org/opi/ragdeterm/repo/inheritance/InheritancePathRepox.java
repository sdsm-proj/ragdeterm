package pl.org.opi.ragdeterm.repo.inheritance;

import pl.org.opi.dbaccess.trx.Trx;
import pl.org.opi.ragdeterm.repo.klazz.KlazzEntity;
import pl.org.opi.ragdeterm.repo.klazz.KlazzRepox;

import java.util.*;

public class InheritancePathRepox extends InheritanceRepo {

    public InheritancePathRepox(Trx trx) {
        super(trx);
    }

    public LinkedHashSet<String> findPathOfType(String canonicalTypeName, String preferredJarSimpleName, EnumExtendImpl extImp, boolean recursive) {
        LinkedHashSet<String> klazzIdList = new LinkedHashSet<>();

        KlazzRepox klazzRepox = new KlazzRepox(this.getTrx());
        InheritanceRepox inheritanceRepox = new InheritanceRepox(this.getTrx());
        KlazzEntity firstType = klazzRepox.findFirstKlazzByNamePreferredJar(canonicalTypeName, preferredJarSimpleName);
        klazzIdList.add(firstType.getIdUid());
        List<InheritanceEntity> relList = inheritanceRepox.findByIdFromRelType(firstType.getIdUid(), extImp);
        for (var rel: relList) {
            klazzIdList.add(rel.getIdTo());
        }
        if (recursive) {
            findBaseTypesRcr(relList, extImp, inheritanceRepox, klazzIdList);
        }
        return klazzIdList;
    }

    private void findBaseTypesRcr(List<InheritanceEntity> parentRelList, EnumExtendImpl extImp, InheritanceRepox inheritanceRepox, LinkedHashSet<String> pathEntityIdSet) {
        for (var parentRel: parentRelList) {
            List<InheritanceEntity> relList = inheritanceRepox.findByIdFromRelType(parentRel.getIdTo(), extImp);
            for (var rel: relList) {
                pathEntityIdSet.add(rel.getIdTo());
                findBaseTypesRcr(relList, extImp, inheritanceRepox, pathEntityIdSet);
            }
        }
    }


}
