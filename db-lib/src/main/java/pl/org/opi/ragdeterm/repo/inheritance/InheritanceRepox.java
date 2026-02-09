package pl.org.opi.ragdeterm.repo.inheritance;

import pl.org.opi.dbaccess.trx.Trx;

import java.util.ArrayList;
import java.util.List;

public class InheritanceRepox extends InheritanceRepo {

    public InheritanceRepox(Trx trx) {
        super(trx);
    }

    public List<InheritanceEntity> findByIdFromRelType(String idFrom, EnumExtendImpl relType) {
        List<InheritanceEntity> rsltList = new ArrayList<>();
        String sql = "";
        if (relType.equals(EnumExtendImpl.ANY)) {
            sql = " select * from rag.inheritance where id_from = ? order by simple_name_from ";
        } else {
            sql = " select * from rag.inheritance where id_from = ? and rel_type = ? order by simple_name_from ";
        }
        executeSelect(sql,
                (stmt) -> {
                    if (relType.equals(EnumExtendImpl.ANY)) {
                        stmt.setString(1, idFrom);
                    } else {
                        stmt.setString(1, idFrom);
                        stmt.setString(2, relType.toString());
                    }
                },
                (rs) -> {
                    while (rs.next()) {
                        InheritanceEntity en = new InheritanceEntity();
                        rs2Entity(rs, en);
                        rsltList.add(en);
                    }
                });
        return rsltList;
    }

    public void deleteFromId(String fromId) {
        executeWrite(
                " delete from rag.inheritance where id_from = ? "
                ,
                (stmt) -> {
                    stmt.setString(1, fromId);
                }
        );
    }

}
