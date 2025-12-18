package pl.org.opi.ragdeterm.db.conn;

import pl.org.opi.ragdeterm.db.trx.Trx;

public interface DbConn {
    String getId();
    void init();
    Trx newTrx();
}
