package seedu.address.commons.util;

/**
 * Contains utility methods for dealing with indexes.
 */
public class IndexUtil {

    /**
     * Returns the zero-based index that corresponds to the {@code oneBasedIndex}.
     */
    public static int oneToZeroIndex(int oneBasedIndex) {
        if (oneBasedIndex < 1) {
            assert false : "One-based index must be of at least size 1.";
        }
        return oneBasedIndex - 1;
    }
}
