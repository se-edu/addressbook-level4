package seedu.address.commons.util;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Utility methods related to Integers
 */
public class IntegerUtil {

    /** Applies an integer {@code offset} to each integer element of {@code source}. */
    public static void applyOffset(Collection<Integer> source, int offset) {
        Collection<Integer> offsetted = source.stream().map(i -> i + offset).collect(Collectors.toList());
        source.clear();
        source.addAll(offsetted);
    }

}
