package pl.org.opi.ragdeterm.repo.ctx;

import pl.org.opi.dbaccess.trx.Trx;
import pl.org.opi.ragdeterm.repo.cooperation.CooperationRepo;
import pl.org.opi.ragdeterm.repo.cooperation.CooperationRepox;
import pl.org.opi.ragdeterm.repo.inheritance.EnumExtendImpl;
import pl.org.opi.ragdeterm.repo.inheritance.InheritancePathRepox;

import java.util.LinkedHashSet;

public class CooperationCtxRepox extends CooperationRepo {

    public CooperationCtxRepox(Trx trx) {
        super(trx);
    }

    public LinkedHashSet<String> findCooperationOfType(String canonicalTypeName, String preferredJarSimpleName) {
        LinkedHashSet<String> klazzIdList = new LinkedHashSet<>();

        var inheritancePathRepox = new InheritancePathRepox(this.getTrx());
        var cooperationRepox = new CooperationRepox(this.getTrx());
        LinkedHashSet<String> klazzPath = inheritancePathRepox.findPathOfType(
                canonicalTypeName, preferredJarSimpleName,
                EnumExtendImpl.ANY, true);

        klazzIdList.addAll(klazzPath);
        for(var k: klazzPath) {
            var fields = cooperationRepox.findByIdKlazz(k);
            for(var f: fields) {
                if (!klazzIdList.contains(f.getIdKlazzOfParameter())) {
                    klazzIdList.add(f.getIdKlazzOfParameter());
                    findMethodsRcr(f.getIdKlazzOfParameter(), cooperationRepox, klazzIdList);
                }
            }
        }

        return klazzIdList;
    }

    private void findMethodsRcr(String klazzId, CooperationRepox cooperationRepox, LinkedHashSet<String> klazzIdList) {
        var fields = cooperationRepox.findByIdKlazz(klazzId);
        for(var f: fields) {
            if (!klazzIdList.contains(f.getIdKlazzOfParameter())) {
                klazzIdList.add(f.getIdKlazzOfParameter());
                findMethodsRcr(f.getIdKlazzOfParameter(), cooperationRepox, klazzIdList);
            }
        }
    }


}
