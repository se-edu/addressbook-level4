package seedu.address.testutil;

import java.lang.reflect.Field;

/**
 *  A utility class to access and modify private methods and fields
 */
public class ReflectionUtil {

    /**
     * Set the targetObject's private fieldName field to the targetValue
     */
    public static void setPrivateField(Class targetClass, String fieldName, Object targetObject, Object targetValue)
            throws Exception {
        Field field = targetClass.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(targetObject, targetValue);
    }

}
