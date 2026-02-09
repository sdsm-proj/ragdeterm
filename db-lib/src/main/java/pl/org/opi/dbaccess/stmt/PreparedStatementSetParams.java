package pl.org.opi.dbaccess.stmt;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface PreparedStatementSetParams {
    void setStmtParams(PreparedStatement stmt) throws SQLException;
}
