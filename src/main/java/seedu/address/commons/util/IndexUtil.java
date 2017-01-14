package seedu.address.commons.util;

import java.util.Collection;
import java.util.List;
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

    /** Returns true if every index in {@code zeroBasedIndices} is within bounds of {@code bounds}. */
    public static boolean areIndicesWithinBounds(Collection<Integer> zeroBasedIndices, List<?> bounds) {
        return areIndicesWithinBounds(zeroBasedIndices, 0, bounds.size());
    }

    /** Returns true if every index in {@code zeroBasedIndices} is between {@code start} and {@code endExclusive}. */
    public static boolean areIndicesWithinBounds(Collection<Integer> zeroBasedIndices, int start, int endExclusive) {
        assert start >= 0;
        assert start <= endExclusive;
        return zeroBasedIndices.stream().allMatch(i -> i >= start && i < endExclusive);
    }

}
