package seedu.address.commons.util;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Utility methods related to indices
 */
public class IndexUtil {

    /** Transforms a set of one based indices into a set of zero based indices. */
    public static Set<Integer> oneToZeroIndex(Set<Integer> oneBasedIndexSet) {
        return oneBasedIndexSet.stream().map(IndexUtil::oneToZeroIndex).collect(Collectors.toSet());
    }

    /** Transforms a one based index into a zero based index. */
    public static int oneToZeroIndex(int oneBasedIndex) {
        assert oneBasedIndex > 0; // ensuring indices are truly one based.
        return oneBasedIndex - 1;
    }

}
