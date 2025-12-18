package pl.org.opi.ragdeterm.scenario;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.org.opi.ragdeterm.db.conn.DbConnConfig;
import pl.org.opi.ragdeterm.db.container.DbConnContainer;
import pl.org.opi.ragdeterm.service.ClassesInheritedAnyLevelService;
import pl.org.opi.ragdeterm.service.universal.EnumAnswerType;

import static pl.org.opi.ragdeterm.scenario.Consta.*;

public class ClassesInheritedAnyLevelSample {

    @BeforeAll
    public static void prepare() {
        DbConnContainer.addDbConn(new DbConnConfig(CONN_ID,
                CONN_URL, CONN_USER, CONN_PSW, CONN_DRIVER,
                false, 20, 180000
        ));
    }

    @Test
    public void test01() {
        ClassesInheritedAnyLevelService service = new ClassesInheritedAnyLevelService();
        var s = service.exec("pl.org.opi.vehicles.Vehicle",
                EnumAnswerType.SHORT_NAME, "\n----------\n", "\n----------\n", "\n");
        System.out.println(s); // System.out.println - intentionally
    }

    @Test
    public void test02() {
        ClassesInheritedAnyLevelService service = new ClassesInheritedAnyLevelService();
        var s = service.exec("pl.org.opi.vehicles.Vehicle",
                EnumAnswerType.LONG_NAME, "\n----------\n", "\n----------\n", "\n");
        System.out.println(s); // System.out.println - intentionally
    }

    @Test
    public void test03() {
        ClassesInheritedAnyLevelService service = new ClassesInheritedAnyLevelService();
        var s = service.exec("pl.org.opi.vehicles.Vehicle",
                EnumAnswerType.ID, "\n----------\n", "\n----------\n", "\n");
        System.out.println(s); // System.out.println - intentionally
    }

    @Test
    public void test04() {
        ClassesInheritedAnyLevelService service = new ClassesInheritedAnyLevelService();
        var s = service.exec("pl.org.opi.vehicles.Vehicle",
                EnumAnswerType.SOURCE_CODE, "\n----------\n", "\n----------\n", "\n-----\n");
        System.out.println(s); // System.out.println - intentionally
    }

}
