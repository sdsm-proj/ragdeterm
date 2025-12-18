package pl.org.opi.ragdeterm.repository.klazz;

import pl.org.opi.ragdeterm.db.stmt.StmtGet;
import pl.org.opi.ragdeterm.db.trx.Trx;

import java.util.ArrayList;
import java.util.List;

public class KlazzRepox extends KlazzRepo {

    public KlazzRepox(Trx trx) {
        super(trx);
    }

    public List<KlazzEntity> findType(String fullCanonicalName) {
        List<KlazzEntity> rsltList = new ArrayList<>();
        String sql = " select * from rag.klazz where full_canonical_name = ? ";
        executeSelect(sql,
                (stmt) -> {
                    stmt.setString(1, fullCanonicalName);
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

    public List<KlazzEntity> findAll4Jar(String simpleJarName) {
        List<KlazzEntity> rsltList = new ArrayList<>();
        String sql = " select * from rag.klazz where jar_simple_name = ? order by full_canonical_name ";
        executeSelect(sql,
                (stmt) -> {
                    stmt.setString(1, simpleJarName);
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

    public List<String> findAllSimpleJarNames() {
        List<String> rsltList = new ArrayList<>();
        String sql = " select distinct jar_simple_name from rag.klazz order by jar_simple_name ";
        executeSelect(sql,
                (stmt) -> {
                },
                (rs) -> {
                    while (rs.next()) {
                        rsltList.add(StmtGet.getString(rs, "jar_simple_name"));
                    }
                });
        return rsltList;
    }

}
