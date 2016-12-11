package seedu.address.commons.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Utility methods related to Lists
 */
public class ListUtil {

    /**
     * Returns a sublist of items from {@code list} specified by {@code indices} with an {@code offset}.<br>
     * <ul>
     *   <li>{@code indices} come in an {@code Integer} array.</li>
     *   <li>{@code offset} is applied before item extraction.</li>
     *   <li>Original list order is maintained in the extracted list.</li>
     *   <li>Duplicate indices are ignored.</li>
     * </ul>
     */
    public static <T> List<T> sublistFromIndices(List<T> list, int offset, Integer... indices) {
        assert list != null;
        assert !CollectionUtil.isAnyNull((Object[]) indices);
        assert areValidIndicesOf(list, offset, Arrays.asList(indices));
        return Stream.of(indices)
                .distinct()
                .sorted()
                .map(i -> list.get(i + offset))
                .collect(Collectors.toList());
    }

    /**
     * Returns a sublist of items from {@code list} specified by {@code indices} with an {@code offset}.<br>
     * <ul>
     *   <li>{@code indices} come in a {@code List} of {@code Integer}s.</li>
     *   <li>{@code offset} is applied before item extraction.</li>
     *   <li>Original list order is maintained in the extracted list.</li>
     *   <li>Duplicate indices are ignored.</li>
     * </ul>
     */
    public static <T> List<T> sublistFromIndices(List<T> list, int offset, List<Integer> indices) {
        assert list != null;
        assert !CollectionUtil.isAnyNull(indices);
        assert areValidIndicesOf(list, offset, indices);
        return indices.stream()
                .distinct()
                .sorted()
                .map(i -> list.get(i + offset))
                .collect(Collectors.toList());
    }

    /** Returns true if every index in {@code indices} is within bounds of {@list} after applying the {@code offset} */
    private static <T> boolean areValidIndicesOf(List<T> list, int offset, List<Integer> indices) {
        return Collections.min(indices) + offset >= 0 && Collections.max(indices) + offset < list.size();
    }
}
