package seedu.address.commons.util;

/**
 * Contains utility methods for dealing with indexes.
 */
public class IndexUtil {

    /**
     * Returns the zero-based index that corresponds to the {@code oneBasedIndex}.
     */
    public static int oneToZeroIndex(int oneBasedIndex) throws IndexOutOfBoundsException {
        if (oneBasedIndex < 1) {
            throw new IndexOutOfBoundsException("One-based index cannot be smaller than 1.");
        }
        return oneBasedIndex - 1;
    }
}
