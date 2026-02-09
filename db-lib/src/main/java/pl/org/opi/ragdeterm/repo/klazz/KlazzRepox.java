package pl.org.opi.ragdeterm.repo.klazz;

import org.apache.commons.lang3.StringUtils;
import pl.org.opi.dbaccess.exception.DbException;
import pl.org.opi.dbaccess.stmt.StmtGet;
import pl.org.opi.dbaccess.trx.Trx;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

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

    public List<KlazzEntity> findTypeExceptForJdk(String fullCanonicalName) {
        List<KlazzEntity> rsltList = new ArrayList<>();
        String sql = " select * from rag.klazz where full_canonical_name = ? and is_jdk = 0 ";
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

    public KlazzEntity findByJarCanonical(String jarSimpleName, String fullCanonicalName) {
        AtomicReference<KlazzEntity> rslt = new AtomicReference<>();
        String sql = " select * from rag.klazz where jar_simple_name = ? and full_canonical_name = ? ";
        executeSelect(sql,
                (stmt) -> {
                    stmt.setString(1, jarSimpleName);
                    stmt.setString(2, fullCanonicalName);
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

    public KlazzEntity findFirstKlazzByNamePreferredJar(String canonicalName, String preferredJarSimpleName) {
        if (StringUtils.isBlank(preferredJarSimpleName)) {
            List<KlazzEntity> rslt = this.findTypeExceptForJdk(canonicalName);
            if (rslt.isEmpty()) {
                throw new DbException("Case class/iface not found: " + canonicalName);
            } else if (rslt.size() == 1) {
                return rslt.getFirst();
            } else {
                throw new DbException("More than 1 class/iface found: " + canonicalName);
            }
        } else {
            KlazzEntity rslt = this.findByJarCanonical(preferredJarSimpleName.trim(), canonicalName);
            if (rslt == null) {
                throw new DbException("Class/iface not found: " + canonicalName);
            }
            return rslt;
        }
    }

    public List<KlazzEntity> findPackageStartsWith(String packageName) {
        List<KlazzEntity> rsltList = new ArrayList<>();
        String sql = " select * from rag.klazz where pckg like '" + packageName + "%' ";
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

    public List<KlazzEntity> findJarPackageStartsWith(String jarSimpleName, String packageName) {
        List<KlazzEntity> rsltList = new ArrayList<>();
        String sql = " select * from rag.klazz where jar_simple_name = ? and pckg like '" + packageName + "%' ";
        executeSelect(sql,
                (stmt) -> {
                    stmt.setString(1, jarSimpleName);
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

    public List<KlazzEntity> findPackageEquals(String packageName) {
        List<KlazzEntity> rsltList = new ArrayList<>();
        String sql = " select * from rag.klazz where pckg = ? ";
        executeSelect(sql,
                (stmt) -> {
                    stmt.setString(1, packageName);
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

    public List<KlazzEntity> findTypesByIdList(List<String> idList) {
        List<KlazzEntity> rsltList = new ArrayList<>();
        if (idList == null || idList.isEmpty()) {
            return rsltList;
        }
        String ids = idList.stream()
                .map(id -> "'" + id.replace("'", "''") + "'")
                .collect(Collectors.joining(","));
        String sql = "select * from rag.klazz where id_uid in (" + ids + ") ";
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


    public void deleteJar(String simpleJarName) {
        executeWrite(
                " delete from rag.klazz where jar_simple_name = ? "
                ,
                (stmt) -> {
                    stmt.setString(1, simpleJarName);
                }
        );
    }

}
