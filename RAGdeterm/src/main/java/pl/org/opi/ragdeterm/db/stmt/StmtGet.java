package pl.org.opi.ragdeterm.db.stmt;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;

public class StmtGet {

    public static String getString(ResultSet rs, String colName) throws SQLException {
        Object obj = rs.getObject(colName);
        if (obj == null) {
            return null;
        } else {
            return rs.getString(colName);
        }
    }

    public static boolean getBoolean(ResultSet rs, String colName) throws SQLException {
        return rs.getBoolean(colName);
    }

    public static Boolean getBooleanNull(ResultSet rs, String colName) throws SQLException {
        Object obj = rs.getObject(colName);
        if (obj == null) {
            return null;
        } else {
            return rs.getBoolean(colName);
        }
    }

    public static int getInt(ResultSet rs, String colName) throws SQLException {
        return rs.getInt(colName);
    }

    public static Integer getIntNull(ResultSet rs, String colName) throws SQLException {
        Object obj = rs.getObject(colName);
        if (obj == null) {
            return null;
        } else {
            return rs.getInt(colName);
        }
    }

    public static long getLong(ResultSet rs, String colName) throws SQLException {
        return rs.getLong(colName);
    }

    public static Long getLongNull(ResultSet rs, String colName) throws SQLException {
        Object obj = rs.getObject(colName);
        if (obj == null) {
            return null;
        } else {
            return rs.getLong(colName);
        }
    }

    public static double getDouble(ResultSet rs, String colName) throws SQLException {
        return rs.getDouble(colName);
    }

    public static Double getDoubleNull(ResultSet rs, String colName) throws SQLException {
        Object obj = rs.getObject(colName);
        if (obj == null) {
            return null;
        } else {
            return rs.getDouble(colName);
        }
    }

    public static BigDecimal getBigDecimal(ResultSet rs, String colName) throws SQLException {
        Object obj = rs.getObject(colName);
        if (obj == null) {
            return null;
        } else {
            return rs.getBigDecimal(colName);
        }
    }

    public static LocalDate getLocalDate(ResultSet rs, String colName) throws SQLException {
        Object obj = rs.getObject(colName);
        if (obj == null) {
            return null;
        } else {
            return rs.getDate(colName).toLocalDate();
        }
    }

    public static LocalTime getLocalTime(ResultSet rs, String colName) throws SQLException {
        Object obj = rs.getObject(colName);
        if (obj == null) {
            return null;
        } else {
            return rs.getTime(colName).toLocalTime();
        }
    }

    public static LocalDateTime getLocalDateTime(ResultSet rs, String colName) throws SQLException {
        Object obj = rs.getObject(colName);
        if (obj == null) {
            return null;
        } else {
            return rs.getTimestamp(colName).toLocalDateTime();
        }
    }

    public static OffsetDateTime getOffsetDateTime(ResultSet rs, String colName) throws SQLException {
        Object obj = rs.getObject(colName);
        if (obj == null) {
            return null;
        } else {
            return rs.getObject(colName, OffsetDateTime.class);
        }
    }

}
