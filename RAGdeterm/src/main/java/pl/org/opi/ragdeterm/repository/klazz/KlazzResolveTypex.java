package pl.org.opi.ragdeterm.repository.klazz;

public class KlazzResolveTypex {

    static public String resolve(Class<?> klazz) {
        try {
            if (klazz.isInterface()) {
                return KlazzEntity.EnumTypex.IFACE.toString();
            } else if (klazz.isEnum()) {
                return KlazzEntity.EnumTypex.ENUM.toString();
            } else if (klazz.isRecord()) {
                return KlazzEntity.EnumTypex.RECORD.toString();
            } else if (klazz.isMemberClass()) {
                return KlazzEntity.EnumTypex.CLASS_MEMBER.toString();
            } else if (klazz.isLocalClass()) {
                return KlazzEntity.EnumTypex.CLASS_LOCAL.toString();
            } else if (klazz.isAnonymousClass()) {
                return KlazzEntity.EnumTypex.CLASS_ANONYMOUS.toString();
            } else {
                return KlazzEntity.EnumTypex.CLASS.toString();
            }
        } catch(Throwable ex) {
            return "ERROR: " + ex.getMessage();
        }
    }

}
