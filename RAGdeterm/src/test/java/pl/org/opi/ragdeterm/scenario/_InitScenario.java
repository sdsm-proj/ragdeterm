package pl.org.opi.ragdeterm.scenario;

import org.junit.jupiter.api.Test;
import pl.org.opi.ragdeterm.db.conn.DbConnConfig;
import pl.org.opi.ragdeterm.db.container.DbConnContainer;
import org.junit.jupiter.api.BeforeAll;
import pl.org.opi.ragdeterm.codeanalyser.classdepot.KlazzDepot;
import pl.org.opi.ragdeterm.codeanalyser.classdepot.KlazzDepotImpl;
import pl.org.opi.ragdeterm.codeanalyser.classresolve.ResolveKlazzExtends;
import pl.org.opi.ragdeterm.codeanalyser.classresolve.ResolveKlazzImplements;
import pl.org.opi.ragdeterm.codeanalyser.fields.ResolveKlazzFields;
import pl.org.opi.ragdeterm.codeanalyser.inMemKlazzExtend.KlazzInExtendMemGraph;
import pl.org.opi.ragdeterm.repository.klazz.KlazzService;
import pl.org.opi.ragdeterm.repository.klazzExtend.KlazzExtendService;
import pl.org.opi.ragdeterm.repository.klazzExtend.KlazzExtendStrings;
import pl.org.opi.ragdeterm.repository.klazzField.KlazzFieldService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static pl.org.opi.ragdeterm.scenario.Consta.*;

public class _InitScenario {

    @BeforeAll
    public static void prepare() {
        DbConnContainer.addDbConn(new DbConnConfig(CONN_ID,
                CONN_URL, CONN_USER, CONN_PSW, CONN_DRIVER,
                false, 20, 180000
        ));
    }

    @Test
    public void clearAndLoad() throws IOException, ClassNotFoundException {
        clearAll();
        if (LOAD_JDK) {
            loadJdk();
        }
        loadProjectJars();
        var listOfJars = List.of(JAR_01);
        resolveExtends(listOfJars);
        resolveImplements(listOfJars);
        relationAsString();
        resolveFields(listOfJars);
    }

    private void clearAll() {
        KlazzService klazzService = new KlazzService();
        KlazzExtendService klazzExtendService = new KlazzExtendService();
        KlazzFieldService klazzFieldService = new KlazzFieldService();
        klazzService.deleteAll();
        klazzExtendService.deleteAll();
        klazzFieldService.deleteAll();
    }

    private void loadJdk() throws IOException, ClassNotFoundException {
        KlazzDepot klazzDepot = new KlazzDepotImpl();
        klazzDepot.loadJdk2Table();
    }

    private void loadProjectJars() throws IOException, ClassNotFoundException {
        KlazzDepot klazzDepot = new KlazzDepotImpl();
        klazzDepot.loadJarKlazzes2Table(JAR_01, JAR_01_SRC, true);
    }

    private void resolveExtends(List<String> jarFullPathList) {
        KlazzService klazzService = new KlazzService();
        var jarNames = klazzService.findAllSimpleJarNames();
        for (var jarName : jarNames) {
            if (jarName.equalsIgnoreCase("JDK")) {
                continue;
            }

            var klazzList = klazzService.findAll4Jar(jarName);
            List<String> fullTypeNameList = new ArrayList<>();
            for (var k : klazzList) {
                fullTypeNameList.add(k.getFullTypeName());
            }
            ResolveKlazzExtends rslv = new ResolveKlazzExtends(
                    jarFullPathList,
                    List.of(jarName),
                    fullTypeNameList
            );
            rslv.exec();
        }
    }

    private void resolveImplements(List<String> jarFullPathList) {
        KlazzService klazzService = new KlazzService();
        var jarNames = klazzService.findAllSimpleJarNames();
        for (var jarName : jarNames) {
            if (jarName.equalsIgnoreCase("JDK")) {
                continue;
            }

            var klazzList = klazzService.findAll4Jar(jarName);
            List<String> fullTypeNameList = new ArrayList<>();
            for (var k : klazzList) {
                fullTypeNameList.add(k.getFullTypeName());
            }
            ResolveKlazzImplements rslv = new ResolveKlazzImplements(
                    jarFullPathList,
                    List.of(jarName),
                    fullTypeNameList
            );
            rslv.exec();
        }
    }

    private void relationAsString() {
        KlazzInExtendMemGraph memGraph = new KlazzInExtendMemGraph();
        memGraph.longLoadProcess();
        List<KlazzExtendStrings> klazzExtendStrings = memGraph.makeAllExtends();
        KlazzService klazzService = new KlazzService();
        klazzService.updateKlazzExtendStrings(klazzExtendStrings);
    }

    private void resolveFields(List<String> jarFullPathList) {
        KlazzService klazzService = new KlazzService();
        var jarNames = klazzService.findAllSimpleJarNames();
        for (var jarName : jarNames) {
            if (jarName.equalsIgnoreCase("JDK")) {
                continue;
            }

            var klazzList = klazzService.findAll4Jar(jarName);
            ResolveKlazzFields rslv = new ResolveKlazzFields(
                    jarFullPathList,
                    List.of(jarName),
                    klazzList
            );
            rslv.exec();
        }
    }

}
