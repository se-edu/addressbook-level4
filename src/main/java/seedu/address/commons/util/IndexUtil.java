package seedu.address.commons.util;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Utility methods related to Integers
 */
public class IndexUtil {

    /** Returns a new {@code List} with each {@code Integer} element of {@code source} subtracting {@code 1}. */
    public static Collection<Integer> oneToZeroIndex(Collection<Integer> source) {
        return source.stream().map(i -> i - 1).collect(Collectors.toList());
    }

}
