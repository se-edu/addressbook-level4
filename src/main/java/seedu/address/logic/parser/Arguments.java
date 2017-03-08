package seedu.address.logic.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import seedu.address.logic.parser.ArgumentTokenizer.Prefix;

/**
 * Stores arguments tokenized by their respective prefixes.
 */
public class Arguments {

    /** Arguments found after tokenizing **/
    private final Map<Prefix, List<String>> tokenizedArguments;

    /**
     * Creates Arguments object that stores arguments tokenized by their prefixes.
     *
     * @param tokenizedArguments Map with {@code Prefix} keys and {@code List<String>} argument values.
     */
    public Arguments(Map<Prefix, List<String>> tokenizedArguments) {
        this.tokenizedArguments = tokenizedArguments;
    }

    /**
     * Returns last value of given prefix.
     */
    public Optional<String> getValue(Prefix prefix) {
        List<String> values = getAllValues(prefix);
        return values.isEmpty() ? Optional.empty() : Optional.of(values.get(values.size() - 1));
    }

    /**
     * Returns all values of given prefix, if any.
     * If the prefix does not exist or has no values, returns an empty list.
     */
    public List<String> getAllValues(Prefix prefix) {
        if (!tokenizedArguments.containsKey(prefix)) {
            return Collections.emptyList();
        }
        return new ArrayList<>(tokenizedArguments.get(prefix));
    }

    /**
     * Returns the preamble (text before the first valid prefix). Trims any leading/trailing spaces.
     */
    public String getPreamble() {
        Optional<String> storedPreamble = getValue(new Prefix(""));
        return storedPreamble.orElse("");
    }
}
