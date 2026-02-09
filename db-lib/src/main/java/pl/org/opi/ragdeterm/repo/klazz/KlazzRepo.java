package pl.org.opi.ragdeterm.repo.klazz;

import pl.org.opi.dbaccess.repo.BaseRepo;
import pl.org.opi.dbaccess.stmt.StmtGet;
import pl.org.opi.dbaccess.stmt.StmtSet;
import pl.org.opi.dbaccess.trx.Trx;
import pl.org.opi.ragdeterm.repo.klazz.KlazzEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class KlazzRepo extends BaseRepo {

    public KlazzRepo(Trx trx) {
        super(trx);
    }

    public void create(KlazzEntity en) {
        executeWrite(
                " insert into rag.klazz ( " +
                        " id_uid, touch_uid, is_jdk, jar_simple_name, pckg, typex, simple_name, " +
                        " full_canonical_name, full_type_name, sub_level, src_code " +
                        " ) values ( " +
                        " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? " +
                        " )"
                ,
                (stmt) -> {
                    entity2Stmt(en, stmt, false);
                }
        );
    }

    public KlazzEntity createAndReturn(KlazzEntity en) {
        create(en);
        return findByKey(en.getIdUid());
    }

    public void update(KlazzEntity en) {
        executeWrite(
                " update rag.klazz set " +
                        " touch_uid = ?, is_jdk = ?, jar_simple_name = ?, pckg = ?, typex = ?, " +
                        " simple_name = ?, full_canonical_name = ?, full_type_name = ?, " +
                        " sub_level = ?, src_code = ? " +
                        " where id_uid = ? "
                ,
                (stmt) -> {
                    entity2Stmt(en, stmt, true);
                }
        );
    }

    public List<KlazzEntity> findAll() {
        List<KlazzEntity> rsltList = new ArrayList<>();
        String sql = " select * from rag.klazz order by id_auto ";
        executeSelect(sql,
                (stmt) -> {
                },
                (rs) -> {
                    while (rs.next()) {
                        KlazzEntity en = new KlazzEntity();
                        rs2Entity(rs, en);
                        rsltList.add(en);
                    }
                });
        return rsltList;
    }

    public KlazzEntity findByKey(String key) {
        AtomicReference<KlazzEntity> rslt = new AtomicReference<>();
        String sql = " select * from rag.klazz where id_uid = ? ";
        executeSelect(sql,
                (stmt) -> {
                    stmt.setString(1, key);
                },
                (rs) -> {
                    if (rs.next()) {
                        KlazzEntity en = new KlazzEntity();
                        rs2Entity(rs, en);
                        rslt.set(en);
                    }
                });
        return rslt.get();
    }

    public KlazzEntity findByAutoinc(long autoinc) {
        AtomicReference<KlazzEntity> rslt = new AtomicReference<>();
        String sql = " select * from rag.klazz where id_auto = ? ";
        executeSelect(sql,
                (stmt) -> {
                    stmt.setLong(1, autoinc);
                },
                (rs) -> {
                    if (rs.next()) {
                        KlazzEntity en = new KlazzEntity();
                        rs2Entity(rs, en);
                        rslt.set(en);
                    }
                });
        return rslt.get();
    }

    private void entity2Stmt(KlazzEntity en, PreparedStatement stmt, boolean update) throws SQLException {
        int offset = 0;
        if (update) offset = -1;
        if (!update) StmtSet.setString(stmt, offset + 1, en.getIdUid());
        StmtSet.setString(stmt, offset + 2, en.getTouchUid());
        StmtSet.setInt(stmt, offset + 3, en.getIsJdk());
        StmtSet.setString(stmt, offset + 4, en.getJarSimpleName());
        StmtSet.setString(stmt, offset + 5, en.getPckg());
        StmtSet.setString(stmt, offset + 6, en.getTypex());
        StmtSet.setString(stmt, offset + 7, en.getSimpleName());
        StmtSet.setString(stmt, offset + 8, en.getFullCanonicalName());
        StmtSet.setString(stmt, offset + 9, en.getFullTypeName());
        StmtSet.setInt(stmt, offset + 10, en.getSubLevel());
        StmtSet.setString(stmt, offset + 11, en.getSrcCode());
        if (update) StmtSet.setString(stmt, offset + 12, en.getIdUid());
    }

    protected void rs2Entity(ResultSet rs, KlazzEntity en) throws SQLException {
        en.setIdAuto(StmtGet.getLong(rs, "id_auto"));
        en.setIdUid(StmtGet.getString(rs, "id_uid"));
        en.setTouchUid(StmtGet.getString(rs, "touch_uid"));
        en.setIsJdk(StmtGet.getInt(rs, "is_jdk"));
        en.setJarSimpleName(StmtGet.getString(rs, "jar_simple_name"));
        en.setPckg(StmtGet.getString(rs, "pckg"));
        en.setTypex(StmtGet.getString(rs, "typex"));
        en.setSimpleName(StmtGet.getString(rs, "simple_name"));
        en.setFullCanonicalName(StmtGet.getString(rs, "full_canonical_name"));
        en.setFullTypeName(StmtGet.getString(rs, "full_type_name"));
        en.setSubLevel(StmtGet.getInt(rs, "sub_level"));
        en.setSrcCode(StmtGet.getString(rs, "src_code"));
    }

}
