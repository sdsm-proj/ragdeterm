package pl.org.opi.dbaccess.conn;

import pl.org.opi.dbaccess.trx.Trx;

public interface DbConn {
    String getId();
    void init();
    Trx newTrx();
}
