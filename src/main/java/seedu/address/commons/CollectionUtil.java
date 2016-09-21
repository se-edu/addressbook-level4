package seedu.address.commons;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Utility methods
 */
public class CollectionUtil {

    /**
     * Returns true if any of the given items are null.
     */
    public static boolean isAnyNull(Object... items) {
        for (Object item : items) {
            if (item == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Throws an assertion error if any of the given arguments is null.
     */
    public static void assertNotNull(Object... items) {
        int argIndex = 0;
        for (Object item : items) {
            if (Objects.isNull(item)) {
                throw new AssertionError("Argument at index " + argIndex + " is null");
            }
            argIndex++;
        }
    }

    /**
     * Throws an assertion error if the collection or any item in it is null.
     */
    public static void assertNoNullElements(Collection<?> items) {
        assertNotNull(items);
        for (Object item : items) {
            if (Objects.isNull(item)) {
                throw new AssertionError("Collection has null element(s)");
            }
        }
    }

    /**
     * Returns true if every element in a collection are unique by {@link Object#equals(Object)}.
     */
    public static boolean elementsAreUnique(Collection<?> items) {
        final Set<Object> testSet = new HashSet<>();
        for (Object item : items) {
            final boolean itemAlreadyExists = !testSet.add(item); // see Set documentation
            if (itemAlreadyExists) {
                return false;
            }
        }
        return true;
    }
}
