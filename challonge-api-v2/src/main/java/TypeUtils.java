package main.java;

import java.lang.Byte;

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
        if (type.isInstance(obj)) {
            return (T)obj;
        }
        throw new UnexpectedTypeException(type, getClass(obj));
    }

    public static <T> T requireOptionalType(Object obj, Class<T> type) throws UnexpectedTypeException {
        if (obj != null) {
            return requireType(obj, type);
        }
        return null;
    }

    public static <T> T requireOptionalType(Object obj, Class<T> type, T defaultObj) throws UnexpectedTypeException {
        if (obj != null) {
            return requireType(obj, type);
        }
        return defaultObj;
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

    public static <T> T requireOptionalType(Object obj, Class<T> type, String name, T defaultObj) throws UnexpectedTypeException {
        try {
            return requireOptionalType(obj, type, defaultObj);
        }
        catch (UnexpectedTypeException e) {
            throw new UnexpectedTypeException(type, getClass(obj), name);
        }
    }

    public static <T> T requireType(JSONObject jobj, String field, Class<T> type) throws UnexpectedTypeException {
        Object obj = jobj.get(field);
        try {
            return requireType(obj, type);
        }
        catch (UnexpectedTypeException e) {
            throw new UnexpectedTypeException(type, getClass(obj), field);
        }
    }

    public static <T> T requireOptionalType(JSONObject jobj, String field, Class<T> type) throws UnexpectedTypeException {
        Object obj = jobj.get(field);
        try {
            return requireOptionalType(obj, type);
        }
        catch (UnexpectedTypeException e) {
            throw new UnexpectedTypeException(type, getClass(obj), field);
        }
    }

    public static <T> T requireOptionalType(JSONObject jobj, String field, Class<T> type, T defaultObj) throws UnexpectedTypeException {
        Object obj = jobj.get(field);
        try {
            return requireOptionalType(obj, type, defaultObj);
        }
        catch (UnexpectedTypeException e) {
            throw new UnexpectedTypeException(type, getClass(obj), field);
        }
    }
}