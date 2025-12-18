package pl.org.opi.ragdeterm.db.util;

import java.util.UUID;

public class GUID {

    public static String gen() {
        return genFull();
    }

    private static String gen6() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 6);
    }

    private static String genFull() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
