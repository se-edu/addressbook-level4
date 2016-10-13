package seedu.address.logic.parser;

import java.util.*;

/**
 * Tokenizes arguments string of the form: preamble <prefix>value <prefix>value ...
 * An argument's value is assumed to not contain its prefix and any leading or trailing
 * whitespaces will be discarded. An argument may be repeated and all its values will be accumulated.
 */
public class ArgumentTokenizer {
    /**
     * A valid prefix that may appear in the arguments string
     */
    public static class Prefix {
        protected final String prefix;

        public Prefix(String prefix) {
            this.prefix = prefix;
        }

        public Prefix(Prefix prefix) {
            this(prefix.getPrefix());
        }

        public String getPrefix() {
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
     * Extra data needed for each prefix encountered during tokenization process
     */
    private class PrefixPosition {
        private int startPos;
        private final Prefix prefix;

        public PrefixPosition(Prefix prefix, int startPos) {
            this.prefix = prefix;
            this.startPos = startPos;
        }

        public int getStartPos() {
            return this.startPos;
        }

        public Prefix getPrefix() {
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
        List<PrefixPosition> positions = findAllPrefixPositions(argsString);
        positions.sort((prefix1, prefix2) -> prefix1.getStartPos() - prefix2.getStartPos());
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
        List<PrefixPosition> extendedPrefixes = new ArrayList<>();

        int argumentStart = argsString.indexOf(prefix.getPrefix());
        while (argumentStart != -1) {
            PrefixPosition extendedPrefix = new PrefixPosition(prefix, argumentStart);
            extendedPrefixes.add(extendedPrefix);
            argumentStart = argsString.indexOf(prefix.getPrefix(), argumentStart + 1);
        }

        return extendedPrefixes;
    }

    /**
     * Extracts the values of each argument and store them in `tokenizedArguments`.
     * This method requires `prefixPositions` to fully reflects all prefixes in the
     * `argsString` and sorted according to each prefix's starting position.
     */
    private void extractArguments(String argsString, List<PrefixPosition> prefixPositions) {
        extractPreamble(argsString, prefixPositions);

        for (int i = 0; i < prefixPositions.size() - 1; i++) {
            extractMiddleArguments(argsString, prefixPositions, i);
        }

        extractLastArgument(argsString, prefixPositions);
    }

    /**
     * Extracts and stores the preamble part of the `argsString`
     */
    private void extractPreamble(String argsString, List<PrefixPosition> prefixPositions) {
        if (prefixPositions.isEmpty()) {
            this.preamble = argsString.trim();
            return;
        }

        String value = "";
        PrefixPosition firstPrefixPosition = prefixPositions.get(0);
        if (firstPrefixPosition.getStartPos() > 0) {
            value = argsString.substring(0, firstPrefixPosition.getStartPos()).trim();
        }

        this.preamble = value;
    }

    /**
     * Extracts and stores the value of an argument with specified prefix in the arguments string
     * @param argsString: The arguments string
     * @param prefixPositions: the list of all prefixes positions in the string
     * @param prefixIndex: the index of the argument's prefix in `prefixPositions` list
     */
    private void extractMiddleArguments(String argsString, List<PrefixPosition> prefixPositions, int prefixIndex) {
        PrefixPosition targetPrefixPosition = prefixPositions.get(prefixIndex);
        PrefixPosition nextPrefixPosition = prefixPositions.get(prefixIndex + 1);
        Prefix prefix = targetPrefixPosition.getPrefix();

        int valueStartPos = targetPrefixPosition.getStartPos() + prefix.getPrefix().length();
        String value = argsString.substring(valueStartPos, nextPrefixPosition.getStartPos());

        addPrefixValue(prefix, value.trim());
    }

    private void extractLastArgument(String argumentsString, List<PrefixPosition> extendedPrefixes) {
        if (extendedPrefixes.isEmpty()) {
            return;
        }

        PrefixPosition lastPrefixPosition = extendedPrefixes.get(extendedPrefixes.size() - 1);
        Prefix prefix = lastPrefixPosition.getPrefix();

        int valueStartPos = lastPrefixPosition.getStartPos() + prefix.getPrefix().length();
        String value = argumentsString.substring(valueStartPos);

        addPrefixValue(prefix, value.trim());
    }

    private void addPrefixValue(Prefix prefix, String value) {
        if (this.tokenizedArguments.containsKey(prefix)) {
            this.tokenizedArguments.get(prefix).add(value);
            return;
        }

        List<String> values = new ArrayList<>();
        values.add(value);
        this.tokenizedArguments.put(prefix, values);
    }
}
