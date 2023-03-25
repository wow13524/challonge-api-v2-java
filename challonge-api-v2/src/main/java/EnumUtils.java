package main.java;

final class EnumUtils {
    private EnumUtils() {};

    interface SearchableEnum {
        String getName();
    }

    public interface StringComparator {
        boolean op(String a, String b);
    }

    public static <T extends SearchableEnum> T valueFromString(Class<T> enumClass, String string, StringComparator op) {
        string = string.toLowerCase();
        for (T obj : enumClass.getEnumConstants()) {
            if (op.op(string, obj.getName())) {
                return obj;
            }
        }
        throw new IllegalArgumentException(String.format(
            "No enum '%s' matching '%s'",
            enumClass.getName(),
            string
        ));
    }

    public static <T extends SearchableEnum> T valueFromString(Class<T> enumClass, String string) {
        return valueFromString(enumClass, string, String::equalsIgnoreCase);
    }
}