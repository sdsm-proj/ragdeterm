package pl.org.opi.ragdeterm.db.trx;

import lombok.extern.slf4j.Slf4j;
import pl.org.opi.ragdeterm.db.exception.DbException;

import java.sql.Connection;

@Slf4j
public class TrxImpl implements Trx {

    private final Connection conn;

    public TrxImpl(Connection conn) {
        this.conn = conn;
        if (conn == null) {
            throw new DbException("Connection is null.");
        }
    }

    @Override
    public Connection getConn() {
        return this.conn;
    }

    @Override
    public void commit() {
        try {
            this.conn.commit();
        } catch (Exception ex) {
            throw new DbException(ex);
        }
    }

    @Override
    public void rollback() {
        try {
            this.conn.rollback();
        } catch (Exception ex) {
            throw new DbException(ex);
        }
    }

    @Override
    public void close() {
        if (this.conn != null) {
            log.debug("TrxImpl.close");
            try {
                this.conn.rollback();
                this.conn.close();
            } catch (Exception ex) {
                throw new DbException(ex);
            }
        }
    }

}
