package pl.org.opi.ragdeterm.service.rslt;

import lombok.RequiredArgsConstructor;
import org.codehaus.plexus.util.StringUtils;
import pl.org.opi.ragdeterm.repository.klazz.KlazzEntity;

import java.util.List;

@RequiredArgsConstructor
public class RsltLongName {

    private final List<KlazzEntity> klazzEntityList;
    private final String prefix;
    private final String suffix;
    private final String separator;

    public String exec() {
        String s = "";
        if (StringUtils.isNotBlank(prefix)) {
            s += prefix;
        }
        for(var i = 0; i < klazzEntityList.size(); i++) {
            String _n = klazzEntityList.get(i).getFullCanonicalName();
            s += _n;
            if (i < (klazzEntityList.size() - 1)) {
                if (StringUtils.isNotEmpty(separator)) {
                    s += separator;
                } else {
                    s += " ";
                }
            }
        }
        if (StringUtils.isNotBlank(suffix)) {
            s += suffix;
        }
        return s;
    }

}
