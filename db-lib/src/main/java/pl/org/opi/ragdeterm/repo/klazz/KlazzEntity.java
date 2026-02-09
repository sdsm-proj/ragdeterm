package pl.org.opi.ragdeterm.repo.klazz;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KlazzEntity {

    public enum EnumTypex {
        CLASS, CLASS_MEMBER, CLASS_LOCAL, CLASS_ANONYMOUS, IFACE, ENUM, RECORD
    }

    private long idAuto;
    private String idUid;
    private String touchUid;
    private int isJdk;
    private String jarSimpleName;
    private String pckg;
    private String typex;
    private String simpleName;
    private String fullCanonicalName;
    private String fullTypeName;
    private int subLevel;
    private String srcCode;
}
