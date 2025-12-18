package pl.org.opi.ragdeterm.db.conn;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.org.opi.ragdeterm.db.exception.DbException;
import pl.org.opi.ragdeterm.db.trx.Trx;
import pl.org.opi.ragdeterm.db.trx.TrxImpl;

import java.sql.Connection;

@Slf4j
@Data
@RequiredArgsConstructor
public class DbConnHikari implements DbConn {

    private final DbConnConfig connConfig;

    private HikariDataSource ds;

    @Override
    public String getId() {
        return connConfig.getId();
    }

    public void init() {
        _initConnPool();
    }

    @Override
    public Trx newTrx() {
        return _newTrx();
    }

    private void _initConnPool() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(connConfig.getUrl());
        hikariConfig.setUsername(connConfig.getUser());
        hikariConfig.setPassword(connConfig.getPsw());
        hikariConfig.setAutoCommit(connConfig.isDbPoolAutoCommit());
        hikariConfig.setMaximumPoolSize(connConfig.getDbPoolMaximumPoolSize());
        hikariConfig.setLeakDetectionThreshold(connConfig.getDbPoolLeakDetectionThreshold());
        ds = new HikariDataSource(hikariConfig);
    }

    private Connection _getConn() {
        try {
            Connection conn = ds.getConnection();
            conn.setAutoCommit(false);
            conn.rollback();
            return conn;
        } catch (Exception ex) {
            throw new DbException(ex);
        }
    }

    private Trx _newTrx() {
        Connection conn = null;
        try {
            conn = _getConn();
            return new TrxImpl(conn);
        } catch (Exception ex) {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception ex2) {
                    // OK
                }
            }
            throw new DbException(ex);
        }
    }

}
