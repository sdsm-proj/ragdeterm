package pl.org.opi.dbaccess.stmt;

import java.sql.CallableStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface CallableStatementAfterExec {
    void afterExec(CallableStatement stmt) throws SQLException;
}
