package pl.org.opi.ragdeterm.service.universal;

public enum EnumAnswerType {
    SHORT_NAME, LONG_NAME, ID, SOURCE_CODE;

    public static EnumAnswerType fromString(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }
        try {
            return EnumAnswerType.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown EnumAnswerType: " + value, e);
        }
    }
}
