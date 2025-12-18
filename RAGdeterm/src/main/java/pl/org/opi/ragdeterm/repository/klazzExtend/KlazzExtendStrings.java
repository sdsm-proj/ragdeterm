package pl.org.opi.ragdeterm.repository.klazzExtend;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class KlazzExtendStrings {
    static public final String SEP = ";";
    private String klazzId;
    private String simpleName;
    private String fullCanonicalName;

    private String extendSimpleNames = "";
    private String extendFullNames = "";
    private String extendIds = "";

    private String implSimpleNames = "";
    private String implFullNames = "";
    private String implIds = "";

    public void appendExtendSimpleNames(String s) {
        if (StringUtils.isBlank(extendSimpleNames)) {
            extendSimpleNames = SEP;
        }
        extendSimpleNames += s + SEP;
    }

    public void appendExtendFullNames(String s) {
        if (StringUtils.isBlank(extendFullNames)) {
            extendFullNames = SEP;
        }
        extendFullNames += s + SEP;
    }

    public void appendExtendIds(String s) {
        if (StringUtils.isBlank(extendIds)) {
            extendIds = SEP;
        }
        extendIds += s + SEP;
    }

    public void appendImplSimpleNames(String s) {
        if (StringUtils.isBlank(implSimpleNames)) {
            implSimpleNames = SEP;
        }
        implSimpleNames += s + SEP;
    }

    public void appendImplFullNames(String s) {
        if (StringUtils.isBlank(implFullNames)) {
            implFullNames = SEP;
        }
        implFullNames += s + SEP;
    }

    public void appendImplIds(String s) {
        if (StringUtils.isBlank(implIds)) {
            implIds = SEP;
        }
        implIds += s + SEP;
    }

}
