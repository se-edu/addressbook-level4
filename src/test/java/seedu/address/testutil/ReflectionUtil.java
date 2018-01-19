package seedu.address.testutil;

import java.lang.reflect.Field;

/**
 *  A utility class to access and modify private methods and fields
 */
public class ReflectionUtil {

    /**
     * Set the {@code targetObject}'s private {@code fieldName} field specified in {@code targetClass} to the
     * {@code targetValue}
     */
    public static void setPrivateField(Class targetClass, Object targetObject, String fieldName, Object targetValue)
            throws NoSuchFieldException, IllegalAccessException {
        Field field = targetClass.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(targetObject, targetValue);
    }

}
