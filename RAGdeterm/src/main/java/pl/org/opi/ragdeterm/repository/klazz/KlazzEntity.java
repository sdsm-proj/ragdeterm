package pl.org.opi.ragdeterm.repository.klazz;

import lombok.Data;

@Data
public class KlazzEntity implements Comparable {

    public enum EnumTypex {
        CLASS, CLASS_MEMBER, CLASS_LOCAL, CLASS_ANONYMOUS, IFACE, ENUM, RECORD
    }

    private long idAuto;
    private String idUid;

    private String jarSimpleName;
    private String pckg;
    private String typex;
    private String simpleName;
    private String fullCanonicalName;
    private String fullTypeName;
    private int subLevel;
    private String extendSimpleNames;
    private String extendFullNames;
    private String extendIds;
    private String implSimpleNames;
    private String implFullNames;
    private String implIds;
    private String srcCode;

    @Override
    public int compareTo(Object o) {
        return simpleName.compareTo(((KlazzEntity)o).getSimpleName());
    }
}
