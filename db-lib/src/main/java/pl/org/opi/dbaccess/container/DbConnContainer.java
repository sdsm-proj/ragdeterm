package pl.org.opi.dbaccess.container;

import pl.org.opi.dbaccess.conn.DbConn;
import pl.org.opi.dbaccess.conn.DbConnConfig;
import pl.org.opi.dbaccess.conn.DbConnHikari;
import pl.org.opi.dbaccess.trx.Trx;

import java.util.LinkedHashMap;

public class DbConnContainer {

    static private final DbConnContainer instance = new DbConnContainer();

    static public DbConn addDbConn(DbConnConfig config) {
        return instance._addDbConn(config);
    }

    static public DbConn getDbConn(String id) {
        return instance.mapConn.get(id);
    }

    static private DbConn getDefaultDbConn() {
        return instance.mapConn.firstEntry().getValue();
    }

    static public Trx newTrx() {
        return getDefaultDbConn().newTrx();
    }

    private final LinkedHashMap<String, DbConn> mapConn = new LinkedHashMap<>();

    private DbConn _addDbConn(DbConnConfig config) {
        DbConn dbconn = new DbConnHikari(config);
        dbconn.init();
        _insertConn2Map(dbconn);
        return dbconn;
    }

    private void _insertConn2Map(DbConn dbconn) {
        mapConn.put(dbconn.getId(), dbconn);
    }

}
