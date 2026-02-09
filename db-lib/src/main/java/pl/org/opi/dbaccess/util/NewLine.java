package pl.org.opi.dbaccess.util;

public class NewLine {

    public static String replaceEscapedNewlines(String text) {
        if (text == null) {
            return null;
        }
        return text.replace("\\n", "\n");
    }

}
