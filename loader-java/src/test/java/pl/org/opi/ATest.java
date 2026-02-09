package pl.org.opi;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.org.opi.dbaccess.conn.DbConnConfig;
import pl.org.opi.dbaccess.container.DbConnContainer;

@Slf4j
public class ATest {

    static public final String CONN_ID = "Default";
    static public final String CONN_URL = "jdbc:postgresql://localhost:5432/ragdeterm?currentSchema=rag";
    static public final String CONN_USER = "ragdeterm";
    static public final String CONN_PSW = "ragdeterm";
    static public final String CONN_DRIVER = "org.postgresql.ds.PGSimpleDataSource";

    @BeforeAll
    public static void prepare() {
        DbConnContainer.addDbConn(new DbConnConfig(CONN_ID,
                CONN_URL, CONN_USER, CONN_PSW, CONN_DRIVER,
                false, 20, 180000
        ));
    }

    @Test
    public void test() {
        log.info("Test begin");



        log.info("Test end");
    }

}
