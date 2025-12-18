package pl.org.opi.ragdeterm.prompt;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.org.opi.ragdeterm.db.conn.DbConnConfig;
import pl.org.opi.ragdeterm.db.container.DbConnContainer;

import static pl.org.opi.ragdeterm.scenario.Consta.*;

public class PromptProcessorTest {

    @BeforeAll
    public static void prepare() {
        DbConnContainer.addDbConn(new DbConnConfig(CONN_ID,
                CONN_URL, CONN_USER, CONN_PSW, CONN_DRIVER,
                false, 20, 180000
        ));
    }

    @Test
    public void test01() {
        String input = """
        ClassContext:
        [*RG ClassContext("pl.org.opi.vehicles.land.car.Car", LONG_NAME, "\\n----------\\n", "\\n----------\\n", "\\n") *RG]
        ClassesInheritedAnyLevel:
        [*RG ClassesInheritedAnyLevel("pl.org.opi.vehicles.Vehicle", SHORT_NAME, "\\n----------\\n", "\\n----------\\n", "\\n") *RG]
        ClassesInheritedDirectly:
        [*RG ClassesInheritedDirectly("pl.org.opi.vehicles.Vehicle", SHORT_NAME, "\\n----------\\n", "\\n----------\\n", "\\n") *RG]
        IfaceImplementationsAnyLevel:
        [*RG IfaceImplementationsAnyLevel("pl.org.opi.hierarchy.TheSameLetters", SHORT_NAME, "\\n----------\\n", "\\n----------\\n", "\\n") *RG]
        IfaceImplementationsDirectly:
        [*RG IfaceImplementationsDirectly("pl.org.opi.hierarchy.TheSameLetters", LONG_NAME, "\\n----------\\n", "\\n----------\\n", "\\n") *RG]
        PackageClassesAnyLevel:
        [*RG PackageClassesAnyLevel("pl.org.opi.hierarchy", SHORT_NAME, "\\n----------\\n", "\\n----------\\n", "\\n") *RG]
        PackageClassesDirectly:
        [*RG PackageClassesDirectly("pl.org.opi.hierarchy", SHORT_NAME, "\\n----------\\n", "\\n----------\\n", "\\n") *RG]
        """;

        String output = PromptProcessor.process(input);
        System.out.println(output); // System.out.println - intentionally
    }

}
