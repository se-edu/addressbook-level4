package seedu.address.logic.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Stores mapping of prefixes to their respective arguments.
 */
public class ArgumentMap {

    /** Prefixes mapped to their respective arguments**/
    private final Map<Prefix, List<String>> arguments;

    /**
     * Creates an ArgumentMap object that stores the mapping of prefixes to their respective arguments.
     *
     * @param arguments Map with {@code Prefix} keys and {@code List<String>} argument values.
     */
    public ArgumentMap(Map<Prefix, List<String>> arguments) {
        this.arguments = arguments;
    }

    /**
     * Returns the last value of the given prefix
     */
    public Optional<String> getValue(Prefix prefix) {
        List<String> values = getAllValues(prefix);
        return values.isEmpty() ? Optional.empty() : Optional.of(values.get(values.size() - 1));
    }

    /**
     * Returns all values of the given prefix.
     * If the prefix does not exist or has no values, this will return an empty list.
     */
    public List<String> getAllValues(Prefix prefix) {
        if (!arguments.containsKey(prefix)) {
            return Collections.emptyList();
        }
        return new ArrayList<>(arguments.get(prefix));
    }

    /**
     * Returns the preamble (text before the first valid prefix). Trims any leading/trailing spaces.
     */
    public String getPreamble() {
        Optional<String> storedPreamble = getValue(new Prefix(""));
        return storedPreamble.orElse("");
    }
}
