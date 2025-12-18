package pl.org.opi.ragdeterm.repository.klazzExtend;

import lombok.Data;

@Data
public class KlazzExtendEntity {

    public enum EnumRelType {
        EXTEND, IMPL
    }

    private long idAuto;
    private String idUid;
    private String relType;
    private String idFrom;
    private String simpleNameFrom;
    private String idTo;
    private String simpleNameTo;
}
