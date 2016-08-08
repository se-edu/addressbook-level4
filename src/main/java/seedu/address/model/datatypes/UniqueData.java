package seedu.address.model.datatypes;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Indicates datatypes that require some form of uniqueness enforcement.
 * Includes utility methods for checking uniqueness of collections of data.
 *
 * This is an abstract class to force implementations of the core methods below.
 */
public abstract class UniqueData {

    /**
     * Checks that the argument collection fulfills the set property.
     *
     * @param items collection of items to be tested
     * @return true if no duplicates found in items
     */
    public static <D extends UniqueData> boolean itemsAreUnique(Collection<D> items) {
        final Set<D> test = new HashSet<>();
        for (D item : items) {
            if (!test.add(item)) return false;
        }
        return true;
    }

    @SafeVarargs
    public static <D extends UniqueData> boolean canCombineWithoutDuplicates(Collection<D> first,
                                                                             Collection<D>... rest) {
        return areUniqueAndDisjoint(first, rest);
    }

    /**
     * Checks that the argument collections fulfill the Set property and are disjoint.
     *
     * @return true if every collection in the arguments contains no duplicates and are disjoint
     */
    @SafeVarargs
    public static <D extends UniqueData> boolean areUniqueAndDisjoint(Collection<D> head, Collection<D>... tail) {
        final Set<D> test = new HashSet<>();
        for (D item : head) {
            if (!test.add(item)) return false;
        }
        for (Collection<D> items : tail) {
            for (D item : items) {
                if (!test.add(item)) return false;
            }
        }
        return true;
    }

    // force implementation of custom equals and hashcode
    @Override
    public abstract boolean equals(Object other);
    @Override
    public abstract int hashCode();

    /**
     * Override with meaningful string representation of object
     */
    @Override
    public abstract String toString();
}
