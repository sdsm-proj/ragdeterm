package pl.org.opi.ragdeterm.repo.ctx;

import pl.org.opi.dbaccess.repo.BaseRepo;
import pl.org.opi.dbaccess.trx.Trx;

import java.util.LinkedHashSet;

public class FullCtxRepox extends BaseRepo {

    public FullCtxRepox(Trx trx) {
        super(trx);
    }

    public LinkedHashSet<String> findFullCtxOfType(String canonicalTypeName, String preferredJarSimpleName) {
        LinkedHashSet<String> klazzIdList = new LinkedHashSet<>();

        var structureCtxRepox = new StructureCtxRepox(getTrx());
        klazzIdList.addAll(structureCtxRepox.findStructureOfType(canonicalTypeName, preferredJarSimpleName));

        var cooperationCtxRepox = new CooperationCtxRepox(getTrx());
        klazzIdList.addAll(cooperationCtxRepox.findCooperationOfType(canonicalTypeName, preferredJarSimpleName));

        return klazzIdList;
    }

}
