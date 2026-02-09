package pl.org.opi.dbaccess.util;

public class StrTool {

    static public String normalizeBlankTrim(String s) {
        if (s != null) {
            return s.trim();
        } else {
            return "";
        }
    }

}
