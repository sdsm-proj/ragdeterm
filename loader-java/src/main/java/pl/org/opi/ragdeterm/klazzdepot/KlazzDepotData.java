package pl.org.opi.ragdeterm.klazzdepot;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import pl.org.opi.dbaccess.util.GUID;
import pl.org.opi.ragdeterm.repo.klazz.KlazzEntity;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class KlazzDepotData {

    @Getter
    private final String jarSimpleName;

    @Getter
    private final List<String> jarSimpleNamesOrdered;

    @Getter
    private final List<KlazzDepotElem> elemList = new ArrayList<>();

    public void appendKlazz(Class<?> k, boolean jdk) {
        var en = new KlazzEntity();

        en.setIdUid(GUID.gen());
        en.setIsJdk(jdk ? 1 : 0);
        en.setJarSimpleName(jarSimpleName);
        en.setPckg(k.getPackageName());
        en.setTypex(KlazzResolveTypex.resolve(k));
        en.setSimpleName(k.getSimpleName());
        en.setFullCanonicalName(k.getCanonicalName());
        en.setFullTypeName(k.getTypeName());
        en.setSubLevel(StringUtils.countMatches(k.getTypeName(), '$'));
        if (StringUtils.isBlank(en.getFullCanonicalName())) {
            en.setFullCanonicalName("???__" + en.getIdUid());
        }

        KlazzDepotElem klazzDepotElem = new KlazzDepotElem(k, en);
        elemList.add(klazzDepotElem);
    }



}
