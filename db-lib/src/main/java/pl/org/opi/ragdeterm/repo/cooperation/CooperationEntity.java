package pl.org.opi.ragdeterm.repo.cooperation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CooperationEntity {
    private long idAuto;
    private String idUid;
    private String idKlazz;
    private String klazzSimpleName;
    private String idKlazzOfParameter;
    private String klazzOfParameterSimpleName;
}
