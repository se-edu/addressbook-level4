package seedu.address.commons.util;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Utility methods related to Lists
 */
public class ListUtil {

    /**
     * Returns a sublist of items from {@code list} specified by {@code indices}.<br>
     * Original list order is maintained in the extracted list.<br>
     */
    public static <T> List<T> subList(List<T> list, Set<Integer> indices) {
        assert IndexUtil.areIndicesWithinBounds(indices, list);
        return indices.stream()
                .sorted()
                .map(list::get)
                .collect(Collectors.toList());
    }

}
