package pl.org.opi.ragdeterm.relation.base;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.org.opi.ragdeterm.klazzdepot.KlazzDepotData;
import pl.org.opi.ragdeterm.klazzdepot.KlazzDepotElem;
import pl.org.opi.ragdeterm.repo.klazz.KlazzEntity;
import pl.org.opi.ragdeterm.repo.klazz.KlazzRepox;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Slf4j
@RequiredArgsConstructor
@Getter
public abstract class ResolveBase {

    protected static final Set<String> IGNORED_CLASSES = Set.of(
            "java.lang.Object",
            "java.lang.Enum"
    );

    protected static final Set<String> IGNORED_PACKAGE = Set.of(
            "java.lang"
    );

    private final boolean jdkMode;
    private final KlazzDepotElem depotElem;
    private final KlazzDepotData depotData;
    private final KlazzRepox klazzRepox;

    private final Set<String> fieldTypeSet = new TreeSet<String>();

    protected KlazzEntity findKlazzByName(String canonicalName) {
        List<KlazzEntity> baseKlazzList;

        if (isJdkMode()) {
            baseKlazzList = klazzRepox.findType(canonicalName);
        } else {
            baseKlazzList = klazzRepox.findTypeExceptForJdk(canonicalName);
        }

        for (var jarSimpleName : getDepotData().getJarSimpleNamesOrdered()) {
            for (var en : baseKlazzList) {
                if (jarSimpleName.equals(en.getJarSimpleName())) {
                    return en;
                }
            }
        }
        if (!baseKlazzList.isEmpty()) {
            return baseKlazzList.getFirst();
        }
        return null;
    }

}
