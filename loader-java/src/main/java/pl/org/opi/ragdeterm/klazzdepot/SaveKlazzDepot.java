package pl.org.opi.ragdeterm.klazzdepot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import pl.org.opi.dbaccess.container.DbConnContainer;
import pl.org.opi.ragdeterm.relation.cooperation.ResolveCooperation;
import pl.org.opi.ragdeterm.relation.inheritance.ResolveInheritance;
import pl.org.opi.ragdeterm.relation.structure.ResolveStructure;
import pl.org.opi.ragdeterm.repo.cooperation.CooperationRepox;
import pl.org.opi.ragdeterm.repo.inheritance.InheritanceRepox;
import pl.org.opi.ragdeterm.repo.klazz.KlazzRepox;
import pl.org.opi.ragdeterm.repo.structure.StructureRepox;

import java.io.File;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Slf4j
public class SaveKlazzDepot {

    private final boolean jdkMode;
    private final KlazzDepotData klazzDepotData;

    private final boolean deleteBefore;
    private final boolean withSrcCode;
    private final String srcCodeFullPath;
    private final boolean resolveInheritance;
    private final boolean resolveStructure;
    private final boolean resolveCooperation;

    public void save() {
        try (var trx = DbConnContainer.newTrx()) {
            var klazzRepox = new KlazzRepox(trx);
            var inheritanceRepox = new InheritanceRepox(trx);
            var structureRepox = new StructureRepox(trx);
            var cooperationRepox = new CooperationRepox(trx);
            beforeMainIteration(klazzRepox);
            for (var klazzDepotElem : klazzDepotData.getElemList()) {
                processOneTypeBeforeSave(klazzRepox, klazzDepotElem);
                saveKlazzDepotElem(klazzRepox, klazzDepotElem);
                processOneTypeAfterSave(klazzRepox, klazzDepotElem, inheritanceRepox);
            }
            afterMainIteration(klazzRepox, inheritanceRepox, structureRepox, cooperationRepox);
            trx.commit();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    private void beforeMainIteration(KlazzRepox klazzRepox) {
        if (deleteBefore) {
            if (!klazzDepotData.getElemList().isEmpty()) {
                var elem = klazzDepotData.getElemList().getFirst();
                klazzRepox.deleteJar(elem.getKlazzEntity().getJarSimpleName());
            }
        }
    }

    private void afterMainIteration(KlazzRepox klazzRepox, InheritanceRepox inheritanceRepox, StructureRepox structureRepox, CooperationRepox cooperationRepox) {
        if (resolveInheritance) {
            for (var klazzDepotElem : klazzDepotData.getElemList()) {
                ResolveInheritance resolveInheritance = new ResolveInheritance(jdkMode, klazzDepotElem, klazzDepotData, klazzRepox, inheritanceRepox);
                resolveInheritance.exec();
            }
        }
        if (resolveStructure) {
            for (var klazzDepotElem : klazzDepotData.getElemList()) {
                ResolveStructure resolveStructure = new ResolveStructure(jdkMode, klazzDepotElem, klazzDepotData, klazzRepox, structureRepox);
                resolveStructure.exec();
            }
        }
        if (resolveCooperation) {
            for (var klazzDepotElem : klazzDepotData.getElemList()) {
                ResolveCooperation resolveCooperation = new ResolveCooperation(jdkMode, klazzDepotElem, klazzDepotData, klazzRepox, cooperationRepox);
                resolveCooperation.exec();
            }
        }
    }

    private void processOneTypeBeforeSave(KlazzRepox klazzRepox, KlazzDepotElem klazzDepotElem) {
        var en = klazzDepotElem.getKlazzEntity();
        if (withSrcCode) {
            en.setSrcCode(findKlazzSource(klazzDepotElem.getK(), srcCodeFullPath));
        }
    }

    private void processOneTypeAfterSave(KlazzRepox klazzRepox, KlazzDepotElem klazzDepotElem, InheritanceRepox inheritanceRepox) {
    }

    private void saveKlazzDepotElem(KlazzRepox klazzRepox, KlazzDepotElem klazzDepotElem) {
        if (deleteBefore) {
            var en = klazzDepotElem.getKlazzEntity();
            klazzRepox.create(en);
        } else {
            var en = klazzDepotElem.getKlazzEntity();
            var existingEn = klazzRepox.findByJarCanonical(en.getJarSimpleName(), en.getFullCanonicalName());
            if (existingEn == null) {
                klazzRepox.create(en);
            } else {
                en.setIdUid(existingEn.getIdUid());
                klazzRepox.update(en);
            }
        }
    }

    private String findKlazzSource(Class<?> k, String sourceFullPath) {
        try {
            String canName = k.getCanonicalName();
            String canName2 = StringUtils.replaceChars(canName, ".", "/");
            String fname = sourceFullPath + "/" + canName2 + ".java";
            File f = new File(fname);
            if (f.exists()) {
                return FileUtils.readFileToString(f, StandardCharsets.UTF_8);
            }
        } catch (Exception ex) {
            // OK
        }
        return "";
    }

}
