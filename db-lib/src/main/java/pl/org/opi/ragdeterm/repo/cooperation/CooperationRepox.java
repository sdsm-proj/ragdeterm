package pl.org.opi.ragdeterm.repo.cooperation;

import pl.org.opi.dbaccess.trx.Trx;
import pl.org.opi.ragdeterm.repo.structure.StructureEntity;

import java.util.ArrayList;
import java.util.List;

public class CooperationRepox extends CooperationRepo {

    public CooperationRepox(Trx trx) {
        super(trx);
    }

    public List<CooperationEntity> findByIdKlazz(String idKlazz) {
        List<CooperationEntity> rsltList = new ArrayList<>();
        String sql = " select * from rag.cooperation where id_klazz = ? order by id_auto ";
        executeSelect(sql,
                (stmt) -> {
                    stmt.setString(1, idKlazz);
                },
                (rs) -> {
                    while (rs.next()) {
                        CooperationEntity en = new CooperationEntity();
                        rs2Entity(rs, en);
                        rsltList.add(en);
                    }
                });
        return rsltList;
    }

    public void deleteIdKlazz(String idKlazz) {
        executeWrite(
                " delete from rag.cooperation where id_klazz = ? "
                ,
                (stmt) -> {
                    stmt.setString(1, idKlazz);
                }
        );
    }

}
