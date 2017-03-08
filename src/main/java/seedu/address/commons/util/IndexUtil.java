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
            throw new IndexOutOfBoundsException("One-based index must be 1 or bigger than 1.");
        }
        return oneBasedIndex - 1;
    }
}
