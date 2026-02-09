package pl.org.opi.ragdeterm.repo.inheritance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InheritanceEntity {

    public enum EnumRelType {
        EXTEND, IMPL
    }

    private long idAuto;
    private String idUid;
    private String touchUid;
    private String relType;
    private String idFrom;
    private String simpleNameFrom;
    private String idTo;
    private String simpleNameTo;
}
