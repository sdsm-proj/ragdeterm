package pl.org.opi.ragdeterm.repo.structure;

import pl.org.opi.dbaccess.trx.Trx;

import java.util.ArrayList;
import java.util.List;

public class StructureRepox extends StructureRepo {

    public StructureRepox(Trx trx) {
        super(trx);
    }

    public List<StructureEntity> findByIdKlazz(String idKlazz) {
        List<StructureEntity> rsltList = new ArrayList<>();
        String sql = " select * from rag.structure where id_klazz = ? order by id_auto ";
        executeSelect(sql,
                (stmt) -> {
                    stmt.setString(1, idKlazz);
                },
                (rs) -> {
                    while (rs.next()) {
                        StructureEntity en = new StructureEntity();
                        rs2Entity(rs, en);
                        rsltList.add(en);
                    }
                });
        return rsltList;
    }

    public void deleteIdKlazz(String idKlazz) {
        executeWrite(
                " delete from rag.structure where id_klazz = ? "
                ,
                (stmt) -> {
                    stmt.setString(1, idKlazz);
                }
        );
    }

}
