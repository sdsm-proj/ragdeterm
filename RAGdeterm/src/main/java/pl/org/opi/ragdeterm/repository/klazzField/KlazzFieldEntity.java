package pl.org.opi.ragdeterm.repository.klazzField;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class KlazzFieldEntity {
    private long idAuto;
    private String idUid;

    private String idKlazz;
    private String klazzSimpleName;
    private String idKlazzField;
    private String klazzFieldSimpleName;


    static public KlazzFieldEntity findFirstByIdKlazz(List<KlazzFieldEntity> list, String id) {
        for(var ke: list) {
            if (id.equalsIgnoreCase(ke.getIdKlazz())) {
                return ke;
            }
        }
        return null;
    }

    static public List<KlazzFieldEntity> findAllByIdKlazz(List<KlazzFieldEntity> list, String id) {
        List<KlazzFieldEntity> rslt = new ArrayList<>();
        for(var ke: list) {
            if (id.equalsIgnoreCase(ke.getIdKlazz())) {
                rslt.add(ke);
            }
        }
        return rslt;
    }

    static public List<KlazzFieldEntity> findAllByIdKlazzezDistinct(List<KlazzFieldEntity> list, List<String> klazzIdList) {
        List<KlazzFieldEntity> rslt = new ArrayList<>();
        for(String id: klazzIdList) {
            var ke = findFirstByIdKlazz(list, id);
            if (ke != null) {
                rslt.add(ke);
            }
        }
        return rslt;
    }

}
