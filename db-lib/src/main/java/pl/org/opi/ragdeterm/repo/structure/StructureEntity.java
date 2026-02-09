package pl.org.opi.ragdeterm.repo.structure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StructureEntity {
    private long idAuto;
    private String idUid;
    private String idKlazz;
    private String klazzSimpleName;
    private String idKlazzField;
    private String klazzFieldSimpleName;
}
