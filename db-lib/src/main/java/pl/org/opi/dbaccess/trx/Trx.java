package pl.org.opi.dbaccess.trx;

import java.sql.Connection;

public interface Trx extends AutoCloseable {
    Connection getConn();
    void commit();
    void rollback();
}
