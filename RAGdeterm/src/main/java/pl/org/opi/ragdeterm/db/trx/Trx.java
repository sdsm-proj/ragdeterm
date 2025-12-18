package pl.org.opi.ragdeterm.db.trx;

import java.sql.Connection;

public interface Trx extends AutoCloseable {
    Connection getConn();
    void commit();
    void rollback();
}
