package seedu.address.commons.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility methods related to Lists
 */
public class ListUtil {

    /**
     * Returns a sublist of items from {@code list} specified by {@code indices}.<br>
     * - Original list order is maintained in the extracted list.<br>
     * - Duplicate indices are ignored.<br>
     */
    public static <T> List<T> subList(List<T> list, Integer... indices) {
        return subList(list, Arrays.asList(indices));
    }

    /**
     * Returns a sublist of items from {@code list} specified by {@code indices}.<br>
     * - Original list order is maintained in the extracted list.<br>
     * - Duplicate indices are ignored.<br>
     */
    public static <T> List<T> subList(List<T> list, Collection<Integer> indices) {
        assert areIndicesWithinBounds(list, indices);
        return indices.stream()
                .distinct()
                .sorted()
                .map(list::get)
                .collect(Collectors.toList());
    }

    /** Returns true if every index in {@code indices} is within bounds of {@code list}. */
    public static boolean areIndicesWithinBounds(List<?> list, Collection<Integer> indices) {
        return indices.stream().allMatch(i -> i >= 0 && i < list.size());
    }
}
