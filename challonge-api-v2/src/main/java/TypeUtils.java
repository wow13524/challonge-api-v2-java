package main.java;

import javax.lang.model.type.NullType;
import main.java.Exceptions.UnexpectedTypeException;

class TypeUtils {
    private static Class<?> getClass(Object obj) {
        return obj == null ? NullType.class : obj.getClass();
    }

    @SuppressWarnings("unchecked")
    public static <T> T requireType(Object obj, Class<T> type) throws UnexpectedTypeException {
        if (!type.isInstance(obj)) {
            throw new UnexpectedTypeException(type, getClass(obj));
        }
        return (T)obj;
    }

    public static <T> T requireType(Object obj, Class<T> type, String name) throws UnexpectedTypeException {
        try {
            return requireType(obj, type);
        }
        catch (UnexpectedTypeException e) {
            throw new UnexpectedTypeException(type, getClass(obj), name);
        }
    }
}
