package pl.org.opi.ragdeterm.relation.cooperation;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import pl.org.opi.dbaccess.util.GUID;
import pl.org.opi.ragdeterm.klazzdepot.KlazzDepotData;
import pl.org.opi.ragdeterm.klazzdepot.KlazzDepotElem;
import pl.org.opi.ragdeterm.relation.base.ResolveBase;
import pl.org.opi.ragdeterm.repo.cooperation.CooperationEntity;
import pl.org.opi.ragdeterm.repo.cooperation.CooperationRepox;
import pl.org.opi.ragdeterm.repo.klazz.KlazzRepox;

import java.lang.reflect.Method;

@Slf4j
@Getter
public class ResolveCooperation extends ResolveBase {

    private final CooperationRepox cooperationRepox;

    public ResolveCooperation(boolean jdkMode, KlazzDepotElem depotElem, KlazzDepotData depotData, KlazzRepox klazzRepox, CooperationRepox cooperationRepox) {
        super(jdkMode, depotElem, depotData, klazzRepox);
        this.cooperationRepox = cooperationRepox;
    }

    public void exec() {
        try {
            removeExistingCooperation();
            iterateMethods();
            iterateTypes();
        } catch (Exception ex) {
            log.warn(ex.getMessage());
        }
    }

    private void removeExistingCooperation() {
        cooperationRepox.deleteIdKlazz(getDepotElem().getKlazzEntity().getIdUid());
    }

    private void iterateMethods() {
        Class<?> k = getDepotElem().getK();
        for (Method m : k.getDeclaredMethods()) {
            Class<?> returnType = m.getReturnType();
            fillType(returnType);
            Class<?>[] parameters = m.getParameterTypes();
            for (Class<?> parameterType : parameters) {
                fillType(parameterType);
            }
        }
    }

    private void fillType(Class<?> t) {
        if (t.isPrimitive()) {
            return;
        }
        Package pkg = t.getPackage();
        if (pkg != null && IGNORED_PACKAGE.stream()
                .anyMatch(p -> pkg.getName().startsWith(p))) {
            return;
        }

        getFieldTypeSet().add(t.getCanonicalName());
        if (t.isArray()) {
            getFieldTypeSet().add(t.getComponentType().getCanonicalName());
        }
    }

    private void iterateTypes() {
        for (var fld : getFieldTypeSet().toArray()) {
            findAndSave((String) fld);
        }
    }

    private void findAndSave(String fieldCanonicalName) {
        var foundKlazzToEntity = findKlazzByName(fieldCanonicalName);
        if (foundKlazzToEntity != null) {
            CooperationEntity en = new CooperationEntity();
            en.setIdUid(GUID.gen());
            en.setIdKlazz(getDepotElem().getKlazzEntity().getIdUid());
            en.setKlazzSimpleName(getDepotElem().getKlazzEntity().getSimpleName());
            en.setIdKlazzOfParameter(foundKlazzToEntity.getIdUid());
            en.setKlazzOfParameterSimpleName(foundKlazzToEntity.getSimpleName());
            cooperationRepox.create(en);
        }
    }

}
