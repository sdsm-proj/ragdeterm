package pl.org.opi.ragdeterm.repo.cooperation;

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

public class CooperationRepo extends BaseRepo {

    public CooperationRepo(Trx trx) {
        super(trx);
    }

    public void create(CooperationEntity en) {
        executeWrite(
                " insert into rag.cooperation ( " +
                        " id_uid, id_klazz, klazz_simple_name, id_klazz_of_parameter, klazz_of_parameter_simple_name " +
                        " ) values ( " +
                        " ?, ?, ?, ?, ? " +
                        " )"
                ,
                (stmt) -> {
                    entity2Stmt(en, stmt, false);
                }
        );
    }

    public CooperationEntity createAndReturn(CooperationEntity en) {
        create(en);
        return findByKey(en.getIdUid());
    }

    public void update(CooperationEntity en) {
        executeWrite(
                " update rag.cooperation set " +
                        " id_klazz = ?, klazz_simple_name = ?, id_klazz_of_parameter = ?, " +
                        " klazz_of_parameter_simple_name = ? " +
                        " where id_uid = ? "
                ,
                (stmt) -> {
                    entity2Stmt(en, stmt, true);
                }
        );
    }

    public List<CooperationEntity> findAll() {
        List<CooperationEntity> rsltList = new ArrayList<>();
        String sql = " select * from rag.cooperation order by id_auto ";
        executeSelect(sql,
                (stmt) -> {
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

    public CooperationEntity findByKey(String key) {
        AtomicReference<CooperationEntity> rslt = new AtomicReference<>();
        String sql = " select * from rag.cooperation where id_uid = ? ";
        executeSelect(sql,
                (stmt) -> {
                    stmt.setString(1, key);
                },
                (rs) -> {
                    if (rs.next()) {
                        CooperationEntity en = new CooperationEntity();
                        rs2Entity(rs, en);
                        rslt.set(en);
                    }
                });
        return rslt.get();
    }

    public CooperationEntity findByAutoinc(long autoinc) {
        AtomicReference<CooperationEntity> rslt = new AtomicReference<>();
        String sql = " select * from rag.cooperation where id_auto = ? ";
        executeSelect(sql,
                (stmt) -> {
                    stmt.setLong(1, autoinc);
                },
                (rs) -> {
                    if (rs.next()) {
                        CooperationEntity en = new CooperationEntity();
                        rs2Entity(rs, en);
                        rslt.set(en);
                    }
                });
        return rslt.get();
    }

    private void entity2Stmt(CooperationEntity en, PreparedStatement stmt, boolean update) throws SQLException {
        int offset = 0;
        if (update) offset = -1;
        if (!update) StmtSet.setString(stmt, offset + 1, en.getIdUid());
        StmtSet.setString(stmt, offset + 2, en.getIdKlazz());
        StmtSet.setString(stmt, offset + 3, en.getKlazzSimpleName());
        StmtSet.setString(stmt, offset + 4, en.getIdKlazzOfParameter());
        StmtSet.setString(stmt, offset + 5, en.getKlazzOfParameterSimpleName());
        if (update) StmtSet.setString(stmt, offset + 6, en.getIdUid());
    }

    protected void rs2Entity(ResultSet rs, CooperationEntity en) throws SQLException {
        en.setIdAuto(StmtGet.getLong(rs, "id_auto"));
        en.setIdUid(StmtGet.getString(rs, "id_uid"));
        en.setIdKlazz(StmtGet.getString(rs, "id_klazz"));
        en.setKlazzSimpleName(StmtGet.getString(rs, "klazz_simple_name"));
        en.setIdKlazzOfParameter(StmtGet.getString(rs, "id_klazz_of_parameter"));
        en.setKlazzOfParameterSimpleName(StmtGet.getString(rs, "klazz_of_parameter_simple_name"));
    }

}
