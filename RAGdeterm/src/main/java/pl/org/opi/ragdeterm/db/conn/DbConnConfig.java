package pl.org.opi.ragdeterm.db.conn;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DbConnConfig {
    private String id;
    private String url;
    private String user;
    private String psw;
    private String driverClassName;
    private boolean dbPoolAutoCommit;
    private int dbPoolMaximumPoolSize;
    private long dbPoolLeakDetectionThreshold;
}
