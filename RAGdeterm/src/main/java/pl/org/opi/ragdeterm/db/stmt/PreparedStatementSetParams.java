package pl.org.opi.ragdeterm.db.stmt;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface PreparedStatementSetParams {
    void setStmtParams(PreparedStatement stmt) throws SQLException;
}
