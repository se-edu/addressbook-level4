package seedu.address.logic.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Tokenizes arguments string of the form: {@code preamble <prefix>value <prefix>value ...}<br>
 *     e.g. {@code some preamble text /t 11.00/dToday /t 12.00 /k /m July}  where prefixes are {@code /t /d /k /m}.<br>
 * 1. An argument's value can be an empty string e.g. the value of {@code /k} in the above example.<br>
 * 2. Leading and trailing whitespaces of an argument value will be discarded.<br>
 * 3. A prefix need not have leading and trailing spaces e.g. the {@code /d in 11.00/dToday} in the above example<br>
 * 4. An argument may be repeated and all its values will be accumulated e.g. the value of {@code /t}
 *    in the above example.<br>
 */
public class ArgumentTokenizer {

    /** Given prefixes **/
    private final List<Prefix> prefixes;

    /**
     * Creates an ArgumentTokenizer that can tokenize arguments string as described by prefixes
     */
    public ArgumentTokenizer(Prefix... prefixes) {
        this.prefixes = Arrays.asList(prefixes);
    }

    /**
     * @param argsString arguments string of the form: preamble <prefix>value <prefix>value ...
     */
    public Arguments tokenize(String argsString) {
        List<PrefixPosition> positions = findAllPrefixPositions(argsString);
        Arguments arguments = extractArguments(argsString, positions);
        return arguments;
    }

    /**
     * Finds all positions in an arguments string at which any prefix appears
     */
    private List<PrefixPosition> findAllPrefixPositions(String argsString) {
        List<PrefixPosition> positions = new ArrayList<>();

        for (Prefix prefix : this.prefixes) {
            positions.addAll(findPrefixPositions(argsString, prefix));
        }

        return positions;
    }

    /**
     * Finds all positions in an arguments string at which a given {@code prefix} appears
     */
    private List<PrefixPosition> findPrefixPositions(String argsString, Prefix prefix) {
        List<PrefixPosition> positions = new ArrayList<>();

        int argumentStart = argsString.indexOf(prefix.getPrefix());
        while (argumentStart != -1) {
            PrefixPosition extendedPrefix = new PrefixPosition(prefix, argumentStart);
            positions.add(extendedPrefix);
            argumentStart = argsString.indexOf(prefix.getPrefix(), argumentStart + 1);
        }

        return positions;
    }

    /**
     * Extracts the argument values, based on the 0-based positions of the prefixes in the arguments string.
     * @param argsString      Arguments string of the form: preamble <prefix>value <prefix>value ...
     * @param prefixPositions 0-based positions of prefixes, contains all prefixes in the {@code argsString}.
     * @return                The argument values in the arguments string, with their prefixes as keys.
     */
    private Arguments extractArguments(String argsString, List<PrefixPosition> prefixPositions) {

        // Sort by start position
        prefixPositions.sort((prefix1, prefix2) -> prefix1.getStartPosition() - prefix2.getStartPosition());

        // Insert a PrefixPosition to represent the preamble
        PrefixPosition preambleMarker = new PrefixPosition(new Prefix(""), 0);
        prefixPositions.add(0, preambleMarker);

        // Add a dummy PrefixPosition to represent the end of the string
        PrefixPosition endPositionMarker = new PrefixPosition(new Prefix(""), argsString.length());
        prefixPositions.add(endPositionMarker);

        // Extract and store the prefixed arguments and preamble (if any)
        Map<Prefix, List<String>> tokenizedArguments = new HashMap<>();
        for (int i = 0; i < prefixPositions.size() - 1; i++) {
            String argValue = extractArgumentValue(argsString, prefixPositions.get(i), prefixPositions.get(i + 1));
            Prefix argPrefix = prefixPositions.get(i).getPrefix();
            List<String> values = tokenizedArguments.getOrDefault(argPrefix, new ArrayList<>());
            values.add(argValue);
            tokenizedArguments.put(argPrefix, values);
        }

        return new Arguments(tokenizedArguments);
    }

    /**
     * Returns the trimmed value of the argument specified by {@code currentPrefixPosition}.
     *    The end position of the value is determined by {@code nextPrefixPosition}
     */
    private String extractArgumentValue(String argsString,
                                        PrefixPosition currentPrefixPosition,
                                        PrefixPosition nextPrefixPosition) {
        Prefix prefix = currentPrefixPosition.getPrefix();

        int valueStartPos = currentPrefixPosition.getStartPosition() + prefix.getPrefix().length();
        String value = argsString.substring(valueStartPos, nextPrefixPosition.getStartPosition());

        return value.trim();
    }

    /**
     * A prefix that marks the beginning of an argument.
     * e.g. '/t' in 'add James /t friend'
     */
    public static class Prefix {
        final String prefix;

        Prefix(String prefix) {
            this.prefix = prefix;
        }

        String getPrefix() {
            return this.prefix;
        }

        @Override
        public int hashCode() {
            return this.prefix == null ? 0 : this.prefix.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Prefix)) {
                return false;
            }
            if (obj == this) {
                return true;
            }

            Prefix otherPrefix = (Prefix) obj;
            return otherPrefix.getPrefix().equals(getPrefix());
        }
    }

    /**
     * Represents a prefix's position in an arguments string
     */
    private class PrefixPosition {
        private int startPosition;
        private final Prefix prefix;

        PrefixPosition(Prefix prefix, int startPosition) {
            this.prefix = prefix;
            this.startPosition = startPosition;
        }

        int getStartPosition() {
            return this.startPosition;
        }

        Prefix getPrefix() {
            return this.prefix;
        }
    }

}
