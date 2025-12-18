package pl.org.opi.ragdeterm.scenario;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.org.opi.ragdeterm.db.conn.DbConnConfig;
import pl.org.opi.ragdeterm.db.container.DbConnContainer;
import pl.org.opi.ragdeterm.service.PackageClassesDirectlyService;
import pl.org.opi.ragdeterm.service.universal.EnumAnswerType;

public class PackageClassesDirectlySample {

    @BeforeAll
    public static void prepare() {
        DbConnContainer.addDbConn(new DbConnConfig(Consta.CONN_ID,
                Consta.CONN_URL, Consta.CONN_USER, Consta.CONN_PSW, Consta.CONN_DRIVER,
                false, 20, 180000
        ));
    }

    @Test
    public void test01() {
        PackageClassesDirectlyService service = new PackageClassesDirectlyService();
        var s = service.exec("pl.org.opi.hierarchy",
                EnumAnswerType.SHORT_NAME, "\n----------\n", "\n----------\n", "\n");
        System.out.println(s); // System.out.println - intentionally
    }

}
