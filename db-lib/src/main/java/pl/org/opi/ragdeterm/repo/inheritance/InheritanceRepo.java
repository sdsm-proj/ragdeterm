package pl.org.opi.ragdeterm.repo.inheritance;

import pl.org.opi.dbaccess.repo.BaseRepo;
import pl.org.opi.dbaccess.stmt.StmtGet;
import pl.org.opi.dbaccess.stmt.StmtSet;
import pl.org.opi.dbaccess.trx.Trx;
import pl.org.opi.ragdeterm.repo.inheritance.InheritanceEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class InheritanceRepo extends BaseRepo {

    public InheritanceRepo(Trx trx) {
        super(trx);
    }

    public void create(InheritanceEntity en) {
        executeWrite(
                " insert into rag.inheritance ( " +
                        " id_uid, touch_uid, rel_type, id_from, simple_name_from, id_to, " +
                        " simple_name_to " +
                        " ) values ( " +
                        " ?, ?, ?, ?, ?, ?, ? " +
                        " )"
                ,
                (stmt) -> {
                    entity2Stmt(en, stmt, false);
                }
        );
    }

    public InheritanceEntity createAndReturn(InheritanceEntity en) {
        create(en);
        return findByKey(en.getIdUid());
    }

    public void update(InheritanceEntity en) {
        executeWrite(
                " update rag.inheritance set " +
                        " touch_uid = ?, rel_type = ?, id_from = ?, simple_name_from = ?, " +
                        " id_to = ?, simple_name_to = ? " +
                        " where id_uid = ? "
                ,
                (stmt) -> {
                    entity2Stmt(en, stmt, true);
                }
        );
    }

    public List<InheritanceEntity> findAll() {
        List<InheritanceEntity> rsltList = new ArrayList<>();
        String sql = " select * from rag.inheritance order by id_auto ";
        executeSelect(sql,
                (stmt) -> {
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

    public InheritanceEntity findByKey(String key) {
        AtomicReference<InheritanceEntity> rslt = new AtomicReference<>();
        String sql = " select * from rag.inheritance where id_uid = ? ";
        executeSelect(sql,
                (stmt) -> {
                    stmt.setString(1, key);
                },
                (rs) -> {
                    if (rs.next()) {
                        InheritanceEntity en = new InheritanceEntity();
                        rs2Entity(rs, en);
                        rslt.set(en);
                    }
                });
        return rslt.get();
    }

    public InheritanceEntity findByAutoinc(long autoinc) {
        AtomicReference<InheritanceEntity> rslt = new AtomicReference<>();
        String sql = " select * from rag.inheritance where id_auto = ? ";
        executeSelect(sql,
                (stmt) -> {
                    stmt.setLong(1, autoinc);
                },
                (rs) -> {
                    if (rs.next()) {
                        InheritanceEntity en = new InheritanceEntity();
                        rs2Entity(rs, en);
                        rslt.set(en);
                    }
                });
        return rslt.get();
    }

    private void entity2Stmt(InheritanceEntity en, PreparedStatement stmt, boolean update) throws SQLException {
        int offset = 0;
        if (update) offset = -1;
        if (!update) StmtSet.setString(stmt, offset + 1, en.getIdUid());
        StmtSet.setString(stmt, offset + 2, en.getTouchUid());
        StmtSet.setString(stmt, offset + 3, en.getRelType());
        StmtSet.setString(stmt, offset + 4, en.getIdFrom());
        StmtSet.setString(stmt, offset + 5, en.getSimpleNameFrom());
        StmtSet.setString(stmt, offset + 6, en.getIdTo());
        StmtSet.setString(stmt, offset + 7, en.getSimpleNameTo());
        if (update) StmtSet.setString(stmt, offset + 8, en.getIdUid());
    }

    protected void rs2Entity(ResultSet rs, InheritanceEntity en) throws SQLException {
        en.setIdAuto(StmtGet.getLong(rs, "id_auto"));
        en.setIdUid(StmtGet.getString(rs, "id_uid"));
        en.setTouchUid(StmtGet.getString(rs, "touch_uid"));
        en.setRelType(StmtGet.getString(rs, "rel_type"));
        en.setIdFrom(StmtGet.getString(rs, "id_from"));
        en.setSimpleNameFrom(StmtGet.getString(rs, "simple_name_from"));
        en.setIdTo(StmtGet.getString(rs, "id_to"));
        en.setSimpleNameTo(StmtGet.getString(rs, "simple_name_to"));
    }

}
