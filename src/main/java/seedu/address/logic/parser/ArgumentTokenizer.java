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
    private class PrefixExtended extends Prefix {
        private int startPos;

        public PrefixExtended(Prefix prefix, int startPos) {
            super(prefix);
            this.startPos = startPos;
        }

        public int getStartPos() {
            return this.startPos;
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
        List<PrefixExtended> extendedPrefixes = new ArrayList<>();

        for (Prefix prefix : this.prefixes) {
            extendedPrefixes.addAll(findPrefixPositions(argsString, prefix));
        }

        extendedPrefixes.sort((prefix1, prefix2) -> prefix1.getStartPos() - prefix2.getStartPos());
        extractArgumentValues(argsString, extendedPrefixes);
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
     * Extracts parsing data for a prefixed argument specific to an arguments string
     */
    private List<PrefixExtended> findPrefixPositions(String argumentsString, Prefix prefix) {
        List<PrefixExtended> extendedPrefixes = new ArrayList<>();

        int argumentStart = argumentsString.indexOf(prefix.getPrefix());
        while (argumentStart != -1) {
            PrefixExtended extendedPrefix = new PrefixExtended(prefix, argumentStart);
            extendedPrefixes.add(extendedPrefix);
            argumentStart = argumentsString.indexOf(prefix.getPrefix(), argumentStart + 1);
        }

        return extendedPrefixes;
    }

    /**
     * Extracts the values of each argument and store them in `tokenizedArguments`.
     * This method requires `extendedPrefixes` to fully reflects all prefixes in the
     * `argsString` and sorted according to each prefix's starting position.
     */
    private void extractArgumentValues(String argsString, List<PrefixExtended> extendedPrefixes) {
        extractPreamble(argsString, extendedPrefixes);

        for (int i = 0; i < extendedPrefixes.size() - 1; i++) {
            extractMiddleArguments(argsString, extendedPrefixes, i);
        }

        extractLastArgument(argsString, extendedPrefixes);
    }

    /**
     * Extracts and stores the preamble part of the `argsString`
     */
    private void extractPreamble(String argsString, List<PrefixExtended> extendedPrefixes) {
        if (extendedPrefixes.isEmpty()) {
            this.preamble = argsString.trim();
            return;
        }

        String value = "";
        PrefixExtended firstPrefix = extendedPrefixes.get(0);
        if (firstPrefix.getStartPos() > 0) {
            value = argsString.substring(0, firstPrefix.getStartPos()).trim();
        }

        this.preamble = value;
    }

    /**
     * Extracts and stores the value of an argument with specified prefix in the arguments string
     * @param argsString: The arguments string
     * @param extendedPrefixes: the list of all prefixes in the string
     * @param prefixIndex: the index of the argument's prefix in `extendedPrefixes` list
     */
    private void extractMiddleArguments(String argsString, List<PrefixExtended> extendedPrefixes, int prefixIndex) {
        PrefixExtended targetPrefix = extendedPrefixes.get(prefixIndex);
        PrefixExtended nextPrefix = extendedPrefixes.get(prefixIndex + 1);

        int valueStartPos = targetPrefix.getStartPos() + targetPrefix.getPrefix().length();
        String value = argsString.substring(valueStartPos, nextPrefix.getStartPos());

        addPrefixValue(targetPrefix, value.trim());
    }

    private void extractLastArgument(String argumentsString, List<PrefixExtended> extendedPrefixes) {
        if (extendedPrefixes.isEmpty()) {
            return;
        }

        PrefixExtended lastPrefix = extendedPrefixes.get(extendedPrefixes.size() - 1);
        int valueStartPos = lastPrefix.getStartPos() + lastPrefix.getPrefix().length();
        String value = argumentsString.substring(valueStartPos);

        addPrefixValue(lastPrefix, value.trim());
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
