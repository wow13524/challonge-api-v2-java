package main.java;

import javax.lang.model.type.NullType;

import org.json.simple.JSONObject;

import main.java.Exceptions.UnexpectedTypeException;

final class TypeUtils {
    private TypeUtils() {};

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

    public static <T> T requireOptionalType(Object obj, Class<T> type) throws UnexpectedTypeException {
        if (obj != null) {
            return requireType(obj, type);
        }
        return null;
    }

    public static <T> T requireType(Object obj, Class<T> type, String name) throws UnexpectedTypeException {
        try {
            return requireType(obj, type);
        }
        catch (UnexpectedTypeException e) {
            throw new UnexpectedTypeException(type, getClass(obj), name);
        }
    }

    public static <T> T requireOptionalType(Object obj, Class<T> type, String name) throws UnexpectedTypeException {
        try {
            return requireOptionalType(obj, type);
        }
        catch (UnexpectedTypeException e) {
            throw new UnexpectedTypeException(type, getClass(obj), name);
        }
    }

    public static <T> T requireType(JSONObject obj, String field, Class<T> type) throws UnexpectedTypeException {
        try {
            return requireType(obj.get(field), type);
        }
        catch (UnexpectedTypeException e) {
            throw new UnexpectedTypeException(type, getClass(obj), field);
        }
    }

    public static <T> T requireOptionalType(JSONObject obj, String field, Class<T> type) throws UnexpectedTypeException {
        try {
            return requireOptionalType(obj.get(field), type);
        }
        catch (UnexpectedTypeException e) {
            throw new UnexpectedTypeException(type, getClass(obj), field);
        }
    }
}
