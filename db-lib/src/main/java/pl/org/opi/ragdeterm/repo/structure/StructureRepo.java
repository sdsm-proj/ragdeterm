package pl.org.opi.ragdeterm.repo.structure;

import pl.org.opi.dbaccess.repo.BaseRepo;
import pl.org.opi.dbaccess.stmt.StmtGet;
import pl.org.opi.dbaccess.stmt.StmtSet;
import pl.org.opi.dbaccess.trx.Trx;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class StructureRepo extends BaseRepo {

    public StructureRepo(Trx trx) {
        super(trx);
    }

    public void create(StructureEntity en) {
        executeWrite(
                " insert into rag.structure ( " +
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

    public StructureEntity createAndReturn(StructureEntity en) {
        create(en);
        return findByKey(en.getIdUid());
    }

    public void update(StructureEntity en) {
        executeWrite(
                " update rag.structure set " +
                        " id_klazz = ?, klazz_simple_name = ?, id_klazz_field = ?, klazz_field_simple_name = ? " +
                        " where id_uid = ? "
                ,
                (stmt) -> {
                    entity2Stmt(en, stmt, true);
                }
        );
    }

    public List<StructureEntity> findAll() {
        List<StructureEntity> rsltList = new ArrayList<>();
        String sql = " select * from rag.structure order by id_auto ";
        executeSelect(sql,
                (stmt) -> {
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

    public StructureEntity findByKey(String key) {
        AtomicReference<StructureEntity> rslt = new AtomicReference<>();
        String sql = " select * from rag.structure where id_uid = ? ";
        executeSelect(sql,
                (stmt) -> {
                    stmt.setString(1, key);
                },
                (rs) -> {
                    if (rs.next()) {
                        StructureEntity en = new StructureEntity();
                        rs2Entity(rs, en);
                        rslt.set(en);
                    }
                });
        return rslt.get();
    }

    public StructureEntity findByAutoinc(long autoinc) {
        AtomicReference<StructureEntity> rslt = new AtomicReference<>();
        String sql = " select * from rag.structure where id_auto = ? ";
        executeSelect(sql,
                (stmt) -> {
                    stmt.setLong(1, autoinc);
                },
                (rs) -> {
                    if (rs.next()) {
                        StructureEntity en = new StructureEntity();
                        rs2Entity(rs, en);
                        rslt.set(en);
                    }
                });
        return rslt.get();
    }

    private void entity2Stmt(StructureEntity en, PreparedStatement stmt, boolean update) throws SQLException {
        int offset = 0;
        if (update) offset = -1;
        if (!update) StmtSet.setString(stmt, offset + 1, en.getIdUid());
        StmtSet.setString(stmt, offset + 2, en.getIdKlazz());
        StmtSet.setString(stmt, offset + 3, en.getKlazzSimpleName());
        StmtSet.setString(stmt, offset + 4, en.getIdKlazzField());
        StmtSet.setString(stmt, offset + 5, en.getKlazzFieldSimpleName());
        if (update) StmtSet.setString(stmt, offset + 6, en.getIdUid());
    }

    protected void rs2Entity(ResultSet rs, StructureEntity en) throws SQLException {
        en.setIdAuto(StmtGet.getLong(rs, "id_auto"));
        en.setIdUid(StmtGet.getString(rs, "id_uid"));
        en.setIdKlazz(StmtGet.getString(rs, "id_klazz"));
        en.setKlazzSimpleName(StmtGet.getString(rs, "klazz_simple_name"));
        en.setIdKlazzField(StmtGet.getString(rs, "id_klazz_field"));
        en.setKlazzFieldSimpleName(StmtGet.getString(rs, "klazz_field_simple_name"));
    }

}
