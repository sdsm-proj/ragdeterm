package pl.org.opi.ragdeterm.db.util;

public class NewLine {

    public static String replaceEscapedNewlines(String text) {
        if (text == null) {
            return null;
        }
        return text.replace("\\n", "\n");
    }

}
