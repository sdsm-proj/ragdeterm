package pl.org.opi.ragdeterm.relation.structure;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import pl.org.opi.dbaccess.util.GUID;
import pl.org.opi.ragdeterm.klazzdepot.KlazzDepotData;
import pl.org.opi.ragdeterm.klazzdepot.KlazzDepotElem;
import pl.org.opi.ragdeterm.relation.base.ResolveBase;
import pl.org.opi.ragdeterm.repo.klazz.KlazzRepox;
import pl.org.opi.ragdeterm.repo.structure.StructureEntity;
import pl.org.opi.ragdeterm.repo.structure.StructureRepox;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

@Slf4j
@Getter
public class ResolveStructure extends ResolveBase {

    private final StructureRepox structureRepox;

    public ResolveStructure(boolean jdkMode, KlazzDepotElem depotElem, KlazzDepotData depotData, KlazzRepox klazzRepox, StructureRepox structureRepox) {
        super(jdkMode, depotElem, depotData, klazzRepox);
        this.structureRepox = structureRepox;
    }

    public void exec() {
        try {
            removeExistingStructure();
            fillFields();
            iterateTypes();
        } catch (Exception ex) {
            log.warn(ex.getMessage());
        }
    }

    private void removeExistingStructure() {
        structureRepox.deleteIdKlazz(getDepotElem().getKlazzEntity().getIdUid());
    }

    private void fillFields() {
        Class<?> k = getDepotElem().getK();
        Field[] declaredFields = k.getDeclaredFields();
        for (var f : declaredFields) {
            Class<?> fieldType = f.getType();
            if (fieldType.isPrimitive()) {
                continue;
            }
            Package pkg = fieldType.getPackage();
            if (pkg != null && IGNORED_PACKAGE.stream()
                    .anyMatch(p -> pkg.getName().startsWith(p))) {
                continue;
            }

            getFieldTypeSet().add(f.getType().getCanonicalName());
            if (f.getType().isArray()) {
                getFieldTypeSet().add(f.getType().getComponentType().getCanonicalName());
            }
            if (f.getGenericType() instanceof ParameterizedType pt) {
                var params = pt.getActualTypeArguments();
                for (var p : params) {
                    Class<?> pc = (Class<?>) p;
                    getFieldTypeSet().add(pc.getCanonicalName());
                }
            }
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
            StructureEntity en = new StructureEntity();
            en.setIdUid(GUID.gen());
            en.setIdKlazz(getDepotElem().getKlazzEntity().getIdUid());
            en.setKlazzSimpleName(getDepotElem().getKlazzEntity().getSimpleName());
            en.setIdKlazzField(foundKlazzToEntity.getIdUid());
            en.setKlazzFieldSimpleName(foundKlazzToEntity.getSimpleName());
            structureRepox.create(en);
        }
    }

}
