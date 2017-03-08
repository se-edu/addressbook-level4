package seedu.address.commons.util;

/**
 * Contains utility method for one-based to zero-based index transformation
 */
public class IndexUtil {

    /**
     * Returns the zero-based index that corresponds to the {@code oneBasedIndex}
     */
    public static int oneToZeroIndex(int oneBasedIndex) {
        return oneBasedIndex - 1;
    }
}
