package pl.org.opi.ragdeterm;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import pl.org.opi.ragdeterm.db.conn.DbConnConfig;
import pl.org.opi.ragdeterm.db.container.DbConnContainer;

@SpringBootApplication
public class RAGdetermApplication {

    public static void main(String[] args) {
        SpringApplication.run(RAGdetermApplication.class, args);
    }

    @Autowired
    private Environment env;

    @PostConstruct
    private void initConn() {
        DbConnContainer.addDbConn(new DbConnConfig(env.getProperty("conn.id"),
                env.getProperty("conn.url"), env.getProperty("conn.user"),
                env.getProperty("conn.psw"), env.getProperty("conn.driver"),
                false, 20, 180000
        ));
    }

}
