package pl.org.opi.ragdeterm.repo.ctx;

import pl.org.opi.dbaccess.trx.Trx;
import pl.org.opi.ragdeterm.repo.inheritance.EnumExtendImpl;
import pl.org.opi.ragdeterm.repo.inheritance.InheritancePathRepox;
import pl.org.opi.ragdeterm.repo.structure.StructureRepo;
import pl.org.opi.ragdeterm.repo.structure.StructureRepox;

import java.util.LinkedHashSet;

public class StructureCtxRepox extends StructureRepo {

    public StructureCtxRepox(Trx trx) {
        super(trx);
    }

    public LinkedHashSet<String> findStructureOfType(String canonicalTypeName, String preferredJarSimpleName) {
        LinkedHashSet<String> klazzIdList = new LinkedHashSet<>();

        var inheritancePathRepox = new InheritancePathRepox(this.getTrx());
        var structureRepox = new StructureRepox(this.getTrx());
        LinkedHashSet<String> klazzPath = inheritancePathRepox.findPathOfType(
                canonicalTypeName, preferredJarSimpleName,
                EnumExtendImpl.ANY, true);

        klazzIdList.addAll(klazzPath);
        for(var k: klazzPath) {
            var fields = structureRepox.findByIdKlazz(k);
            for(var f: fields) {
                if (!klazzIdList.contains(f.getIdKlazzField())) {
                    klazzIdList.add(f.getIdKlazzField());
                    findFieldsRcr(f.getIdKlazzField(), structureRepox, klazzIdList);
                }
            }
        }

        return klazzIdList;
    }

    private void findFieldsRcr(String klazzId, StructureRepox structureRepox, LinkedHashSet<String> klazzIdList) {
        var fields = structureRepox.findByIdKlazz(klazzId);
        for(var f: fields) {
            if (!klazzIdList.contains(f.getIdKlazzField())) {
                klazzIdList.add(f.getIdKlazzField());
                findFieldsRcr(f.getIdKlazzField(), structureRepox, klazzIdList);
            }
        }
    }

}
