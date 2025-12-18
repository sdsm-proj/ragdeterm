package pl.org.opi.ragdeterm.repository.klazzField;

import pl.org.opi.ragdeterm.db.repo.BaseRepo;
import pl.org.opi.ragdeterm.db.stmt.StmtGet;
import pl.org.opi.ragdeterm.db.stmt.StmtSet;
import pl.org.opi.ragdeterm.db.trx.Trx;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KlazzFieldRepo extends BaseRepo {

    public KlazzFieldRepo(Trx trx) {
        super(trx);
    }

    public void create(KlazzFieldEntity en) {
        executeWrite(
                " insert into rag.klazz_field ( " +
                        " id_uid, id_klazz, klazz_simple_name, id_klazz_field, klazz_field_simple_name " +
                        " ) values ( " +
                        " ?, ?, ?, ?, ? " +
                        " )"
                ,
                (stmt) -> {
                    entity2Stmt(en, stmt, false);
                }
        );
    }

    public List<KlazzFieldEntity> findAll() {
        List<KlazzFieldEntity> rsltList = new ArrayList<>();
        String sql = " select * from rag.klazz_field order by id_auto ";
        executeSelect(sql,
                (stmt) -> {
                },
                (rs) -> {
                    while (rs.next()) {
                        KlazzFieldEntity en = new KlazzFieldEntity();
                        rs2Entity(rs, en);
                        rsltList.add(en);
                    }
                });
        return rsltList;
    }

    public void deleteAll() {
        executeWrite(
                " delete from rag.klazz_field "
                ,
                (stmt) -> {
                }
        );
    }

    private void entity2Stmt(KlazzFieldEntity en, PreparedStatement stmt, boolean update) throws SQLException {
        int offset = 0;
        if (update) offset = -1;
        if (!update) StmtSet.setString(stmt, offset + 1, en.getIdUid());
        StmtSet.setString(stmt, offset + 2, en.getIdKlazz());
        StmtSet.setString(stmt, offset + 3, en.getKlazzSimpleName());
        StmtSet.setString(stmt, offset + 4, en.getIdKlazzField());
        StmtSet.setString(stmt, offset + 5, en.getKlazzFieldSimpleName());
        if (update) StmtSet.setString(stmt, offset + 6, en.getIdUid());
    }

    protected void rs2Entity(ResultSet rs, KlazzFieldEntity en) throws SQLException {
        en.setIdAuto(StmtGet.getLong(rs, "id_auto"));
        en.setIdUid(StmtGet.getString(rs, "id_uid"));
        en.setIdKlazz(StmtGet.getString(rs, "id_klazz"));
        en.setKlazzSimpleName(StmtGet.getString(rs, "klazz_simple_name"));
        en.setIdKlazzField(StmtGet.getString(rs, "id_klazz_field"));
        en.setKlazzFieldSimpleName(StmtGet.getString(rs, "klazz_field_simple_name"));
    }

}
