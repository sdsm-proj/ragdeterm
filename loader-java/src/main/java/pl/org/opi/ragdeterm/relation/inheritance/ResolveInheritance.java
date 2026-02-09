package pl.org.opi.ragdeterm.relation.inheritance;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import pl.org.opi.dbaccess.util.GUID;
import pl.org.opi.ragdeterm.klazzdepot.KlazzDepotData;
import pl.org.opi.ragdeterm.klazzdepot.KlazzDepotElem;
import pl.org.opi.ragdeterm.relation.base.ResolveBase;
import pl.org.opi.ragdeterm.repo.inheritance.InheritanceEntity;
import pl.org.opi.ragdeterm.repo.inheritance.InheritanceRepox;
import pl.org.opi.ragdeterm.repo.klazz.KlazzRepox;

@Slf4j
@Getter
public class ResolveInheritance extends ResolveBase {

    private final InheritanceRepox inheritanceRepox;

    public ResolveInheritance(boolean jdkMode, KlazzDepotElem depotElem, KlazzDepotData depotData, KlazzRepox klazzRepox, InheritanceRepox inheritanceRepox) {
        super(jdkMode, depotElem, depotData, klazzRepox);
        this.inheritanceRepox = inheritanceRepox;
    }

    public void exec() {
        try {
            removeExistingInheritance();
            findExtends();
            findImplements();
        } catch (Exception ex) {
            log.warn(ex.getMessage());
        }
    }

    private void removeExistingInheritance() {
        inheritanceRepox.deleteFromId(getDepotElem().getKlazzEntity().getIdUid());
    }

    private void findExtends() {
        Class<?> klazzFrom = getDepotElem().getK();
        Class<?> klazzTo = klazzFrom.getSuperclass();

        if (klazzTo == null) {
            return;
        }
        if (IGNORED_CLASSES.contains(klazzTo.getCanonicalName())) {
            return;
        }
        var foundKlazzToEntity = findKlazzByName(klazzTo.getCanonicalName());
        if (foundKlazzToEntity != null) {
            InheritanceEntity en = new InheritanceEntity();
            en.setIdUid(GUID.gen());
            en.setRelType(InheritanceEntity.EnumRelType.EXTEND.toString());
            en.setIdFrom(getDepotElem().getKlazzEntity().getIdUid());
            en.setSimpleNameFrom(getDepotElem().getKlazzEntity().getSimpleName());
            en.setIdTo(foundKlazzToEntity.getIdUid());
            en.setSimpleNameTo(foundKlazzToEntity.getSimpleName());
            inheritanceRepox.create(en);
        }
    }

    private void findImplements() {
        Class<?> klazzFrom = getDepotElem().getK();
        Class<?>[] ifacesTo = klazzFrom.getInterfaces();
        for (var ifaceTo : ifacesTo) {
            var foundKlazzToEntity = findKlazzByName(ifaceTo.getCanonicalName());
            if (foundKlazzToEntity != null) {
                InheritanceEntity en = new InheritanceEntity();
                en.setIdUid(GUID.gen());
                en.setRelType(InheritanceEntity.EnumRelType.IMPL.toString());
                en.setIdFrom(getDepotElem().getKlazzEntity().getIdUid());
                en.setSimpleNameFrom(getDepotElem().getKlazzEntity().getSimpleName());
                en.setIdTo(foundKlazzToEntity.getIdUid());
                en.setSimpleNameTo(foundKlazzToEntity.getSimpleName());
                inheritanceRepox.create(en);
            }
        }
    }

}
