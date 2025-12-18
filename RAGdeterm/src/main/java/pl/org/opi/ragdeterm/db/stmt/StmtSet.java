package pl.org.opi.ragdeterm.db.stmt;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class StmtSet {

    public static void setString(PreparedStatement stmt, int idx, String value) throws SQLException {
        if (value != null) {
            stmt.setString(idx, value);
        } else {
            stmt.setObject(idx, null);
        }
    }

    public static void setBoolean(PreparedStatement stmt, int idx, boolean value) throws SQLException {
        stmt.setBoolean(idx, value);
    }

    public static void setBooleanNull(PreparedStatement stmt, int idx, Boolean value) throws SQLException {
        if (value != null) {
            stmt.setBoolean(idx, value);
        } else {
            stmt.setObject(idx, null);
        }
    }

    public static void setInt(PreparedStatement stmt, int idx, int value) throws SQLException {
        stmt.setInt(idx, value);
    }

    public static void setIntNull(PreparedStatement stmt, int idx, Integer value) throws SQLException {
        if (value != null) {
            stmt.setInt(idx, value);
        } else {
            stmt.setObject(idx, null);
        }
    }

    public static void setLong(PreparedStatement stmt, int idx, long value) throws SQLException {
        stmt.setLong(idx, value);
    }

    public static void setLongNull(PreparedStatement stmt, int idx, Long value) throws SQLException {
        if (value != null) {
            stmt.setLong(idx, value);
        } else {
            stmt.setObject(idx, null);
        }
    }

    public static void setDouble(PreparedStatement stmt, int idx, double value) throws SQLException {
        stmt.setDouble(idx, value);
    }

    public static void setDoubleNull(PreparedStatement stmt, int idx, Double value) throws SQLException {
        if (value != null) {
            stmt.setDouble(idx, value);
        } else {
            stmt.setObject(idx, null);
        }
    }

    public static void setBigDecimal(PreparedStatement stmt, int idx, BigDecimal value) throws SQLException {
        if (value != null) {
            stmt.setBigDecimal(idx, value);
        } else {
            stmt.setObject(idx, null);
        }
    }

    public static void setLocalDate(PreparedStatement stmt, int idx, LocalDate value) throws SQLException {
        if (value != null) {
            stmt.setObject(idx, value);
        } else {
            stmt.setObject(idx, null);
        }
    }

    public static void setLocalTime(PreparedStatement stmt, int idx, LocalTime value) throws SQLException {
        if (value != null) {
            stmt.setObject(idx, value);
        } else {
            stmt.setObject(idx, null);
        }
    }

    public static void setLocalDateTime(PreparedStatement stmt, int idx, LocalDateTime value) throws SQLException {
        if (value != null) {
            stmt.setObject(idx, value);
        } else {
            stmt.setObject(idx, null);
        }
    }

}
