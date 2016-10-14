package seedu.address.logic.parser;

import java.util.*;

/**
 * Tokenizes arguments string of the form: preamble <prefix>value <prefix>value ...
 * An argument's value is assumed to not contain its prefix and any leading or trailing
 * whitespaces will be discarded. An argument may be repeated and all its values will be accumulated.
 */
public class ArgumentTokenizer {
    /**
     * A prefix that marks the beginning of an argument
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
     * Contains a prefix's position in an arguments string and corresponding prefix
     */
    private class PrefixPosition {
        private int startPos;
        private final Prefix prefix;

        PrefixPosition(Prefix prefix, int startPos) {
            this.prefix = prefix;
            this.startPos = startPos;
        }

        int getStartPosition() {
            return this.startPos;
        }

        Prefix getPrefix() {
            return this.prefix;
        }
    }

    private final List<Prefix> prefixes;
    private String preamble = "";
    private final Map<Prefix, List<String>> tokenizedArguments = new HashMap<>();

    /**
     * Creates an ArgumentTokenizer that can tokenize arguments string as described by prefixes
     */
    public ArgumentTokenizer(Prefix... prefixes) {
        this.prefixes = Arrays.asList(prefixes);
    }

    /**
     * @param argsString arguments string of the form: preamble <prefix>data <prefix>data ...
     */
    public void tokenize(String argsString) {
        resetTokenizerState();
        List<PrefixPosition> positions = findAllPrefixPositions(argsString);
        extractArguments(argsString, positions);
    }

    /**
     * Returns last value of given prefix.
     */
    public Optional<String> getValue(Prefix prefix) {
        return getValues(prefix).flatMap((values) -> Optional.of(values.get(values.size() - 1)));
    }

    /**
     * Returns all values of given prefix
     */
    public Optional<List<String>> getValues(Prefix prefix) {
        if (!this.tokenizedArguments.containsKey(prefix)) {
            return Optional.empty();
        }
        List<String> values = new ArrayList<>(this.tokenizedArguments.get(prefix));
        return Optional.of(values);
    }

    /**
     * Returns the text before the first valid prefix, if any
     */
    public Optional<String> getPreamble() {
        if (this.preamble.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(this.preamble);
    }

    private void resetTokenizerState() {
        this.preamble = "";
        this.tokenizedArguments.clear();
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
     * Finds all positions in an arguments string at which a given `prefix` appears
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

        // If no prefixes, the whole string is taken as preamble
        if (prefixPositions.isEmpty()){
            this.preamble = argsString.trim();
            return;
        }

        // Sort by start position
        prefixPositions.sort((prefix1, prefix2) -> prefix1.getStartPosition() - prefix2.getStartPosition());

        this.preamble = extractPreamble(argsString, prefixPositions.get(0));

        for (int i = 0; i < prefixPositions.size() - 1; i++) {
            PrefixPosition currentPrefixPosition = prefixPositions.get(i);
            PrefixPosition nextPrefixPosition = prefixPositions.get(i + 1);
            String argValue = extractMiddleArgument(argsString, currentPrefixPosition, nextPrefixPosition);
            saveArgument(currentPrefixPosition.getPrefix(), argValue);
        }

        PrefixPosition lastPrefixPosition = prefixPositions.get(prefixPositions.size() - 1);
        String argValue = extractLastArgument(argsString, lastPrefixPosition);
        saveArgument(lastPrefixPosition.getPrefix(), argValue);
    }

    /**
     * Returns the preamble part of the {@code argsString}
     */
    private String extractPreamble(String argsString, PrefixPosition firstPrefixPosition) {
        return argsString.substring(0, firstPrefixPosition.getStartPosition()).trim();
    }

    private String extractMiddleArgument(String argsString,
                                         PrefixPosition currentPrefixPosition,
                                         PrefixPosition nextPrefixPosition) {
        Prefix prefix = currentPrefixPosition.getPrefix();

        int valueStartPos = currentPrefixPosition.getStartPosition() + prefix.getPrefix().length();
        String value = argsString.substring(valueStartPos, nextPrefixPosition.getStartPosition());

        return value.trim();
    }

    private String extractLastArgument(String argsString, PrefixPosition lastPrefixPosition) {
        Prefix prefix = lastPrefixPosition.getPrefix();

        int valueStartPos = lastPrefixPosition.getStartPosition() + prefix.getPrefix().length();
        String value = argsString.substring(valueStartPos);

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
}
