package pl.org.opi.ragdeterm.scenario;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.org.opi.ragdeterm.db.conn.DbConnConfig;
import pl.org.opi.ragdeterm.db.container.DbConnContainer;
import pl.org.opi.ragdeterm.service.IfaceImplementationsDirectlyService;
import pl.org.opi.ragdeterm.service.universal.EnumAnswerType;

import static pl.org.opi.ragdeterm.scenario.Consta.*;

public class IfaceImplementationsDirectlySample {

    @BeforeAll
    public static void prepare() {
        DbConnContainer.addDbConn(new DbConnConfig(CONN_ID,
                CONN_URL, CONN_USER, CONN_PSW, CONN_DRIVER,
                false, 20, 180000
        ));
    }

    @Test
    public void test01() {
        IfaceImplementationsDirectlyService service = new IfaceImplementationsDirectlyService();
        var s = service.exec("pl.org.opi.hierarchy.TheSameLetters",
                EnumAnswerType.LONG_NAME, "\n----------\n", "\n----------\n", "\n");
        System.out.println(s); // System.out.println - intentionally
    }

}
