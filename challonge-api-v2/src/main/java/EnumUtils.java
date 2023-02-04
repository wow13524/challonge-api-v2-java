package main.java;

final class EnumUtils {
    private EnumUtils() {};

    public static <T extends Enum<T>> T valueFromString(Class<T> enumClass, String string) {
        string = string.toLowerCase();
        for (T obj : enumClass.getEnumConstants()) {
            if (obj.toString().toLowerCase().equals(string)) {
                return obj;
            }
        }
        throw new IllegalArgumentException(String.format(
            "No %s matching '%s'",
            enumClass.getClass().getName(),
            string
        ));
    }
}