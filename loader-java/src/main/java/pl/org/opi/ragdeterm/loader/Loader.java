package pl.org.opi.ragdeterm.loader;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;
import picocli.CommandLine.Option;
import pl.org.opi.dbaccess.conn.DbConnConfig;
import pl.org.opi.dbaccess.container.DbConnContainer;

import java.io.IOException;

@CommandLine.Command()
@Data
@Slf4j
public class Loader implements Runnable {

    /**
     --connUrl="jdbc:postgresql://localhost:5432/ragdeterm?currentSchema=rag" --connUser=ragdeterm
     --connPsw=ragdeterm  --connDriver=org.postgresql.ds.PGSimpleDataSource  --mode=jar
     --jarFullPath="c:\ragdeterm\RAGdata\target\RAGdata-1.0.0.jar" --withSrcCode=true
     --srcCodeFullPath="c:\ragdeterm\RAGdata\src\main\java"
     */

    public enum Mode {
        jdk, jar
    }

    @Option(names = "--connUrl", required = true)
    private String connUrl;

    @Option(names = "--connUser", required = true)
    private String connUser;

    @Option(names = "--connPsw", required = true)
    private String connPsw;

    @Option(names = "--connDriver", required = true)
    private String connDriver;

    @Option(names = "--mode", required = true)
    private Mode mode;

    @Option(names = "--jarFullPath", required = false, defaultValue = "")
    private String jarFullPath;

    @Option(names = "--jarClassPath", required = false, defaultValue = "")
    private String jarClassPath;

    @Option(names = "--deleteBefore", required = false, defaultValue = "false")
    private boolean deleteBefore;

    @Option(names = "--withSrcCode", required = false, defaultValue = "false")
    private boolean withSrcCode;

    @Option(names = "--srcCodeFullPath", required = false, defaultValue = "")
    private String srcCodeFullPath;

    @Option(names = "--resolveInheritance", required = false, defaultValue = "false")
    private boolean resolveInheritance;

    @Option(names = "--resolveStructure", required = false, defaultValue = "false")
    private boolean resolveStructure;

    @Option(names = "--resolveCooperation", required = false, defaultValue = "false")
    private boolean resolveCooperation;

    private static boolean alreadyInitialized = false;

    private static synchronized void initPool(String connUrl, String connUser, String connPsw, String connDriver) {
        if (alreadyInitialized) return;
        alreadyInitialized = true;
        DbConnContainer.addDbConn(new DbConnConfig("Default",
                connUrl, connUser, connPsw, connDriver,
                false, 20, (1000*60*10)
        ));
    }

    public void run() {
        exec();
    }

    public void exec() {
        try {
            initPool(connUrl, connUser, connPsw, connDriver);
            execCore();
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    private void execCore() throws IOException {
        System.out.println("start");
        System.out.println("mode: " + mode);
        switch(mode) {
            case jdk -> new LoaderJdk(deleteBefore, resolveInheritance, resolveStructure, resolveCooperation).exec();
            case jar -> new LoaderJar(jarFullPath, jarClassPath,
                    deleteBefore,
                    withSrcCode, srcCodeFullPath,
                    resolveInheritance, resolveStructure, resolveCooperation).exec();
        }
        System.out.println("stop");
    }

}
