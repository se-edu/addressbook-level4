package seedu.address.logic.parser;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

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

    /** Arguments found after tokenizing **/
    private final Map<Prefix, List<String>> tokenizedArguments = new HashMap<>();
    private final Map<String, String> tokenizedPreamble = new HashMap<>();

    /**
     * Creates an ArgumentTokenizer that can tokenize arguments string as described by prefixes
     */
    public ArgumentTokenizer(Prefix... prefixes) {
        this.prefixes = Arrays.asList(prefixes);
    }

    /**
     * @param argsString arguments string of the form: preamble <prefix>value <prefix>value ...
     */
    public void tokenize(String argsString) {
        resetTokenizerState();
        List<PrefixPosition> positions = findAllPrefixPositions(argsString);
        extractArguments(argsString, positions);
    }

    /**
     * Tokenizes preamble for {@code EditCommand}. Example: This string "3 Bob Han" will be split
     * into "3" (index) and "Bob Han" (name).
     */
    public void tokenizePreamble() {
        Optional<String> storedPreamble = getPreamble();
        Matcher matcher = generateMatcherForTokenizingPreamble(storedPreamble);
        if (matcher.matches()) {
            tokenizedPreamble.put(Person.INDEX_KEY, matcher.group(Person.INDEX_KEY));
            if (matcher.group(Name.KEY) != null) {
                tokenizedPreamble.put(Name.KEY, matcher.group(Name.KEY).trim());
            }
        }
    }

    /**
     * Generates and returns the matcher that matches with {@code storedPreamble}.
     *
     * @param storedPreamble the string to be matched against the regex
     * @return matcher that matches {@code storedPreamble} against the regex.
     */
    private Matcher generateMatcherForTokenizingPreamble(Optional<String> storedPreamble) {
        assert storedPreamble != null;

        String regex = "(?<index>\\d+)(?<name>.+)?";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(storedPreamble.get());
        return matcher;
    }

    /**
     * Returns last value of given prefix.
     */
    public Optional<String> getValue(Prefix prefix) {
        return getAllValues(prefix).flatMap((values) -> Optional.of(values.get(values.size() - 1)));
    }

    /**
     * Returns all values of given prefix.
     */
    public Optional<List<String>> getAllValues(Prefix prefix) {
        if (!this.tokenizedArguments.containsKey(prefix)) {
            return Optional.empty();
        }
        List<String> values = new ArrayList<>(this.tokenizedArguments.get(prefix));
        return Optional.of(values);
    }

    /**
     * Returns the preamble (text before the first valid prefix), if any. Leading/trailing spaces will be trimmed.
     *     If the string before the first prefix is empty, Optional.empty() will be returned.
     */
    public Optional<String> getPreamble() {

        Optional<String> storedPreamble = getValue(new Prefix(""));

        /* An empty preamble is considered 'no preamble present' */
        if (storedPreamble.isPresent() && !storedPreamble.get().isEmpty()) {
            return storedPreamble;
        } else {
            return Optional.empty();
        }
    }

    /**
     * Returns the value of {@code tokenizedPreamble} as mapped by {@code key}, if any.
     *
     * @return the value of {@code tokenizedPreamble} as stored in the {@code key}.
     *      If there is no mapping for {@code key}, Optional.empty() will be returned.
     */
    public Optional<String> getTokenizedPreambleValue(String key) {
        assert key != null;

        if (tokenizedPreamble.get(key) != null) {
            return Optional.of(tokenizedPreamble.get(key));
        } else {
            return Optional.empty();
        }
    }

    private void resetTokenizerState() {
        this.tokenizedArguments.clear();
        this.tokenizedPreamble.clear();
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
     * Extracts the preamble/arguments and stores them in local variables.
     * @param prefixPositions must contain all prefixes in the {@code argsString}
     */
    private void extractArguments(String argsString, List<PrefixPosition> prefixPositions) {

        // Sort by start position
        prefixPositions.sort((prefix1, prefix2) -> prefix1.getStartPosition() - prefix2.getStartPosition());

        // Insert a PrefixPosition to represent the preamble
        PrefixPosition preambleMarker = new PrefixPosition(new Prefix(""), 0);
        prefixPositions.add(0, preambleMarker);

        // Add a dummy PrefixPosition to represent the end of the string
        PrefixPosition endPositionMarker = new PrefixPosition(new Prefix(""), argsString.length());
        prefixPositions.add(endPositionMarker);

        // Extract the prefixed arguments and preamble (if any)
        for (int i = 0; i < prefixPositions.size() - 1; i++) {
            String argValue = extractArgumentValue(argsString, prefixPositions.get(i), prefixPositions.get(i + 1));
            saveArgument(prefixPositions.get(i).getPrefix(), argValue);
        }

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
     * Stores the value of the given prefix in the state of this tokenizer
     */
    private void saveArgument(Prefix prefix, String value) {
        if (this.tokenizedArguments.containsKey(prefix)) {
            this.tokenizedArguments.get(prefix).add(value);
            return;
        }

        List<String> values = new ArrayList<>();
        values.add(value);
        this.tokenizedArguments.put(prefix, values);
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
