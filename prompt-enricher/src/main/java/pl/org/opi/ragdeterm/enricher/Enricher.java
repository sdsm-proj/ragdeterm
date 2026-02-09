package pl.org.opi.ragdeterm.enricher;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;
import pl.org.opi.dbaccess.conn.DbConnConfig;
import pl.org.opi.dbaccess.container.DbConnContainer;
import pl.org.opi.ragdeterm.prompt.PromptProcessor;

import java.io.IOException;

@CommandLine.Command()
@Data
@Slf4j
public class Enricher implements Runnable {

    @CommandLine.Option(names = "--connUrl", required = true)
    private String connUrl;

    @CommandLine.Option(names = "--connUser", required = true)
    private String connUser;

    @CommandLine.Option(names = "--connPsw", required = true)
    private String connPsw;

    @CommandLine.Option(names = "--connDriver", required = true)
    private String connDriver;

    @CommandLine.Option(names = "--prompt", required = true)
    private String prompt;

    private static boolean alreadyInitialized = false;

    private static synchronized void initPool(String connUrl, String connUser, String connPsw, String connDriver) {
        if (alreadyInitialized) return;
        alreadyInitialized = true;
        DbConnContainer.addDbConn(new DbConnConfig("Default",
                connUrl, connUser, connPsw, connDriver,
                false, 20, 180000
        ));
    }

    public void run() {
        exec(prompt);
    }

    public String exec(String prompt) {
        try {
            initPool(connUrl, connUser, connPsw, connDriver);
            return execCore(prompt);
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        }
        return "ERROR";
    }

    private String execCore(String prompt) throws IOException {
        String rslt = PromptProcessor.process(prompt);
        //System.out.println(rslt);
        return rslt;
    }

}
