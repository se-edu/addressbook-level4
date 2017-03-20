package seedu.address.logic.parser;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * Tokenizes an arguments string and returns an ArgumentMap object that maps prefixes to their respective argument
     * values. Only the given prefixes will be recognized in the arguments string.
     *
     * @param argsString Arguments string of the form: {@code preamble <prefix>value <prefix>value ...}
     * @param prefixes   Prefixes to tokenize the arguments string with
     * @return           ArgumentMap object that maps prefixes to their arguments
     */
    public static ArgumentMap tokenize(String argsString, Prefix... prefixes) {
        List<PrefixPosition> positions = findAllPrefixPositions(argsString, prefixes);
        ArgumentMap argumentMap = extractArguments(argsString, positions);
        return argumentMap;
    }

    /**
     * Finds all zero-based prefix positions in the given arguments string.
     *
     * @param argsString Arguments string of the form: {@code preamble <prefix>value <prefix>value ...}
     * @param prefixes   Prefixes to find in the arguments string
     * @return           List of zero-based prefix positions in the given arguments string.
     */
    private static List<PrefixPosition> findAllPrefixPositions(String argsString, Prefix... prefixes) {
        List<PrefixPosition> positions = new ArrayList<>();

        for (Prefix prefix : prefixes) {
            positions.addAll(findPrefixPositions(argsString, prefix));
        }

        return positions;
    }

    /**
     * Finds all zero-based prefix positions of the given {@code prefix} in the given arguments string.
     *
     * @param argsString Arguments string of the form: {@code preamble <prefix>value <prefix>value ...}
     * @param prefix     Prefix to find in the arguments string
     * @return           List of zero-based prefix positions of the given prefix in the given arguments string.
     */
    private static List<PrefixPosition> findPrefixPositions(String argsString, Prefix prefix) {
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
     * Extracts prefixes and their argument values, and returns an ArgumentMap object that maps the extracted prefixes
     * to their respective arguments. Prefixes are extracted based on their zero-based positions in {@code argsString}.
     *
     * @param argsString      Arguments string of the form: {@code preamble <prefix>value <prefix>value ...}
     * @param prefixPositions Zero-based positions of all prefixes in {@code argsString}
     * @return                ArgumentMap object that maps prefixes to their arguments
     */
    private static ArgumentMap extractArguments(String argsString, List<PrefixPosition> prefixPositions) {

        // Sort by start position
        prefixPositions.sort((prefix1, prefix2) -> prefix1.getStartPosition() - prefix2.getStartPosition());

        // Insert a PrefixPosition to represent the preamble
        PrefixPosition preambleMarker = new PrefixPosition(new Prefix(""), 0);
        prefixPositions.add(0, preambleMarker);

        // Add a dummy PrefixPosition to represent the end of the string
        PrefixPosition endPositionMarker = new PrefixPosition(new Prefix(""), argsString.length());
        prefixPositions.add(endPositionMarker);

        // Map prefixes to their argument values (if any)
        ArgumentMap argMap = new ArgumentMap();
        for (int i = 0; i < prefixPositions.size() - 1; i++) {
            // Extract prefixes and their arguments
            Prefix argPrefix = prefixPositions.get(i).getPrefix();
            String argValue = extractArgumentValue(argsString, prefixPositions.get(i), prefixPositions.get(i + 1));

            // Append extracted argument to any previously encountered arguments with the same prefix
            List<String> argValues = new ArrayList<>(argMap.getAllValues(argPrefix));
            argValues.add(argValue);
            argMap.put(argPrefix, argValues);
        }

        return argMap;
    }

    /**
     * Returns the trimmed value of the argument in the arguments string specified by {@code currentPrefixPosition}.
     * The end position of the value is determined by {@code nextPrefixPosition}.
     */
    private static String extractArgumentValue(String argsString,
                                        PrefixPosition currentPrefixPosition,
                                        PrefixPosition nextPrefixPosition) {
        Prefix prefix = currentPrefixPosition.getPrefix();

        int valueStartPos = currentPrefixPosition.getStartPosition() + prefix.getPrefix().length();
        String value = argsString.substring(valueStartPos, nextPrefixPosition.getStartPosition());

        return value.trim();
    }

    /**
     * Represents a prefix's position in an arguments string.
     */
    private static class PrefixPosition {
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
