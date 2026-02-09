package pl.org.opi.dbaccess.stmt;

import java.sql.CallableStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface CallableStatementSetParams {
    void setStmtParams(CallableStatement stmt) throws SQLException;
}
