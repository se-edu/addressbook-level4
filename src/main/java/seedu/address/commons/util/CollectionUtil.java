package seedu.address.commons.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Utility methods related to Collections
 */
public class CollectionUtil {

    /**
     * Returns true if {@code items} itself or any element of {@code items} is null.
     */
    public static boolean isAnyNull(Object... items) {
        return items == null || Stream.of(items).anyMatch(Objects::isNull);
    }

    /**
     * Returns true if {@code items} itself or any element of {@code items} is null.
     */
    public static boolean isAnyNull(Collection<?> items) {
        return items == null || items.stream().anyMatch(Objects::isNull);
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
