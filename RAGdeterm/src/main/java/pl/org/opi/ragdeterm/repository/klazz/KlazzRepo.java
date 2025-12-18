package pl.org.opi.ragdeterm.repository.klazz;

import org.apache.commons.lang3.StringUtils;
import pl.org.opi.ragdeterm.db.repo.BaseRepo;
import pl.org.opi.ragdeterm.db.stmt.StmtGet;
import pl.org.opi.ragdeterm.db.stmt.StmtSet;
import pl.org.opi.ragdeterm.db.trx.Trx;

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
                        " id_uid, jar_simple_name, pckg, typex, simple_name, full_canonical_name, full_type_name, sub_level, " +
                        " extend_simple_names, extend_full_names, extend_ids, impl_simple_names, impl_full_names, impl_ids, src_code " +
                        " ) values ( " +
                        " ?, ?, ?, ?, ?, ?, ?, ?, " +
                        " ?, ?, ?, ?, ?, ?, ? " +
                        " )"
                ,
                (stmt) -> {
                    entity2Stmt(en, stmt, false);
                }
        );
    }

    public void update(KlazzEntity en) {
        executeWrite(
                " update rag.klazz set " +
                        " jar_simple_name = ?, pckg = ?, typex = ?, simple_name = ?, full_canonical_name = ?, full_type_name = ?, sub_level = ?, " +
                        " extend_simple_names = ?, extend_full_names = ?, extend_ids = ?, impl_simple_names = ?, impl_full_names = ?, impl_ids = ?, src_code = ? " +
                        " where id_uid = ? "
                ,
                (stmt) -> {
                    entity2Stmt(en, stmt, true);
                }
        );
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

    public void deleteAll() {
        executeWrite(
                " delete from rag.klazz "
                ,
                (stmt) -> {
                }
        );
    }

    private void entity2Stmt(KlazzEntity en, PreparedStatement stmt, boolean update) throws SQLException {
        if (StringUtils.isBlank(en.getExtendSimpleNames())) en.setExtendSimpleNames("");
        if (StringUtils.isBlank(en.getExtendFullNames())) en.setExtendFullNames("");
        if (StringUtils.isBlank(en.getExtendIds())) en.setExtendIds("");
        if (StringUtils.isBlank(en.getImplSimpleNames())) en.setImplSimpleNames("");
        if (StringUtils.isBlank(en.getImplFullNames())) en.setImplFullNames("");
        if (StringUtils.isBlank(en.getImplIds())) en.setImplIds("");

        int offset = 0;
        if (update) offset = -1;
        if (!update) StmtSet.setString(stmt, offset + 1, en.getIdUid());
        StmtSet.setString(stmt, offset + 2, en.getJarSimpleName());
        StmtSet.setString(stmt, offset + 3, en.getPckg());
        StmtSet.setString(stmt, offset + 4, en.getTypex());
        StmtSet.setString(stmt, offset + 5, en.getSimpleName());
        StmtSet.setString(stmt, offset + 6, en.getFullCanonicalName());
        StmtSet.setString(stmt, offset + 7, en.getFullTypeName());
        StmtSet.setInt(stmt, offset + 8, en.getSubLevel());
        StmtSet.setString(stmt, offset + 9, en.getExtendSimpleNames());
        StmtSet.setString(stmt, offset + 10, en.getExtendFullNames());
        StmtSet.setString(stmt, offset + 11, en.getExtendIds());
        StmtSet.setString(stmt, offset + 12, en.getImplSimpleNames());
        StmtSet.setString(stmt, offset + 13, en.getImplFullNames());
        StmtSet.setString(stmt, offset + 14, en.getImplIds());
        StmtSet.setString(stmt, offset + 15, en.getSrcCode());
        if (update) StmtSet.setString(stmt, offset + 16, en.getIdUid());
    }

    protected void rs2Entity(ResultSet rs, KlazzEntity en) throws SQLException {
        en.setIdAuto(StmtGet.getLong(rs, "id_auto"));
        en.setIdUid(StmtGet.getString(rs, "id_uid"));
        en.setJarSimpleName(StmtGet.getString(rs, "jar_simple_name"));
        en.setPckg(StmtGet.getString(rs, "pckg"));
        en.setTypex(StmtGet.getString(rs, "typex"));
        en.setSimpleName(StmtGet.getString(rs, "simple_name"));
        en.setFullCanonicalName(StmtGet.getString(rs, "full_canonical_name"));
        en.setFullTypeName(StmtGet.getString(rs, "full_type_name"));
        en.setSubLevel(StmtGet.getInt(rs, "sub_level"));
        en.setExtendSimpleNames(StmtGet.getString(rs, "extend_simple_names"));
        en.setExtendFullNames(StmtGet.getString(rs, "extend_full_names"));
        en.setExtendIds(StmtGet.getString(rs, "extend_ids"));
        en.setImplSimpleNames(StmtGet.getString(rs, "impl_simple_names"));
        en.setImplFullNames(StmtGet.getString(rs, "impl_full_names"));
        en.setImplIds(StmtGet.getString(rs, "impl_ids"));
        en.setSrcCode(StmtGet.getString(rs, "src_code"));
    }

}
