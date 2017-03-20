package seedu.address.logic.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Stores mapping of prefixes to their respective arguments.
 */
public class ArgumentMap {

    /** Prefixes mapped to their respective arguments**/
    private final Map<Prefix, List<String>> argMap;

    /**
     * Creates an ArgumentMap object that stores the mapping of prefixes to their respective arguments.
     */
    public ArgumentMap() {
        argMap = new HashMap<>();
    }

    /**
     * Associates the specified argument value with the specified prefix key in this map.
     * If the map previously contained a mapping for the key, the old value is replaced.
     *
     * @param prefix    Prefix key with which the specified argument value is to be associated
     * @param arguments Argument value to be associated with the specified prefix key
     * @return          The previous value associated with key, or null if there was no mapping for key.
     *                  (A null return can also indicate that the map previously associated null with key.)
     */
    public List<String> put(Prefix prefix, List<String> arguments) {
        return argMap.put(prefix, arguments);
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
        if (!argMap.containsKey(prefix)) {
            return Collections.emptyList();
        }
        return new ArrayList<>(argMap.get(prefix));
    }

    /**
     * Returns the preamble (text before the first valid prefix). Trims any leading/trailing spaces.
     */
    public String getPreamble() {
        Optional<String> storedPreamble = getValue(new Prefix(""));
        return storedPreamble.orElse("");
    }
}
