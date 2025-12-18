package pl.org.opi.ragdeterm.scenario;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.org.opi.ragdeterm.db.conn.DbConnConfig;
import pl.org.opi.ragdeterm.db.container.DbConnContainer;
import pl.org.opi.ragdeterm.service.ClassesInheritedDirectlyService;
import pl.org.opi.ragdeterm.service.universal.EnumAnswerType;

public class ClassesInheritedDirectlySample {

    @BeforeAll
    public static void prepare() {
        DbConnContainer.addDbConn(new DbConnConfig(Consta.CONN_ID,
                Consta.CONN_URL, Consta.CONN_USER, Consta.CONN_PSW, Consta.CONN_DRIVER,
                false, 20, 180000
        ));
    }

    @Test
    public void test01() {
        ClassesInheritedDirectlyService service = new ClassesInheritedDirectlyService();
        var s = service.exec("pl.org.opi.vehicles.Vehicle",
                EnumAnswerType.SHORT_NAME, "\n----------\n", "\n----------\n", "\n");
        System.out.println(s); // System.out.println - intentionally
    }

    @Test
    public void test02() {
        ClassesInheritedDirectlyService service = new ClassesInheritedDirectlyService();
        var s = service.exec("pl.org.opi.vehicles.Vehicle",
                EnumAnswerType.LONG_NAME, "\n----------\n", "\n----------\n", "\n");
        System.out.println(s); // System.out.println - intentionally
    }

    @Test
    public void test03() {
        ClassesInheritedDirectlyService service = new ClassesInheritedDirectlyService();
        var s = service.exec("pl.org.opi.vehicles.Vehicle",
                EnumAnswerType.ID, "\n----------\n", "\n----------\n", "\n");
        System.out.println(s); // System.out.println - intentionally
    }

    @Test
    public void test04() {
        ClassesInheritedDirectlyService service = new ClassesInheritedDirectlyService();
        var s = service.exec("pl.org.opi.vehicles.Vehicle",
                EnumAnswerType.SOURCE_CODE, "\n----------\n", "\n----------\n", "\n-----\n");
        System.out.println(s); // System.out.println - intentionally
    }

}
