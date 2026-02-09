package pl.org.opi.dbaccess.stmt;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ProcessResultSet {
    void process(ResultSet rs) throws SQLException;
}
