package pl.org.opi.ragdeterm.repository.klazzExtend;

import pl.org.opi.ragdeterm.db.repo.BaseRepo;
import pl.org.opi.ragdeterm.db.stmt.StmtGet;
import pl.org.opi.ragdeterm.db.stmt.StmtSet;
import pl.org.opi.ragdeterm.db.trx.Trx;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KlazzExtendRepo extends BaseRepo {

    public KlazzExtendRepo(Trx trx) {
        super(trx);
    }

    public void create(KlazzExtendEntity en) {
        executeWrite(
                " insert into rag.klazz_extend ( " +
                        " id_uid, rel_type, id_from, simple_name_from, id_to, simple_name_to " +
                        " ) values ( " +
                        " ?, ?, ?, ?, ?, ? " +
                        " )"
                ,
                (stmt) -> {
                    entity2Stmt(en, stmt, false);
                }
        );
    }

    public List<KlazzExtendEntity> findAll() {
        List<KlazzExtendEntity> rsltList = new ArrayList<>();
        String sql = " select * from rag.klazz_extend order by id_auto ";
        executeSelect(sql,
                (stmt) -> {
                },
                (rs) -> {
                    while (rs.next()) {
                        KlazzExtendEntity en = new KlazzExtendEntity();
                        rs2Entity(rs, en);
                        rsltList.add(en);
                    }
                });
        return rsltList;
    }

    public void deleteAll() {
        executeWrite(
                " delete from rag.klazz_extend "
                ,
                (stmt) -> {
                }
        );
    }

    private void entity2Stmt(KlazzExtendEntity en, PreparedStatement stmt, boolean update) throws SQLException {
        int offset = 0;
        if (update) offset = -1;
        if (!update) StmtSet.setString(stmt, offset + 1, en.getIdUid());
        StmtSet.setString(stmt, offset + 2, en.getRelType());
        StmtSet.setString(stmt, offset + 3, en.getIdFrom());
        StmtSet.setString(stmt, offset + 4, en.getSimpleNameFrom());
        StmtSet.setString(stmt, offset + 5, en.getIdTo());
        StmtSet.setString(stmt, offset + 6, en.getSimpleNameTo());
        if (update) StmtSet.setString(stmt, offset + 7, en.getIdUid());
    }

    protected void rs2Entity(ResultSet rs, KlazzExtendEntity en) throws SQLException {
        en.setIdAuto(StmtGet.getLong(rs, "id_auto"));
        en.setIdUid(StmtGet.getString(rs, "id_uid"));

        en.setRelType(StmtGet.getString(rs, "rel_type"));
        en.setIdFrom(StmtGet.getString(rs, "id_from"));
        en.setSimpleNameFrom(StmtGet.getString(rs, "simple_name_from"));
        en.setIdTo(StmtGet.getString(rs, "id_to"));
        en.setSimpleNameTo(StmtGet.getString(rs, "simple_name_to"));
    }

}
