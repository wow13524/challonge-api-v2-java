package main.java;

import javax.lang.model.type.NullType;
import main.java.Exceptions.UnexpectedTypeException;

class TypeUtils {
    @SuppressWarnings("unchecked")
    public static <T> T requireType(Object obj, Class<T> type) throws UnexpectedTypeException {
        if (!type.isInstance(obj)) {
            Class<?> objClass = obj == null ? NullType.class : obj.getClass();
            throw new UnexpectedTypeException(type, objClass);
        }
        return (T)obj;
    }
}
