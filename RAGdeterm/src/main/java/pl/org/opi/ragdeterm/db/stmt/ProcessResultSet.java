package pl.org.opi.ragdeterm.db.stmt;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ProcessResultSet {
    void process(ResultSet rs) throws SQLException;
}
