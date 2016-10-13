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
     * Extracts the values of each argument and store them in `parsedArguments`.
     * This method requires `parsingData` to be fully reflects all prefixes in the
     * `argumentsString` and sorted according to each argument starting position.
     */
    private ParsedArguments extractArgumentValues(String argumentsString,
                                                  List<PrefixParsingData> prefixParsingDatas) {
        ParsedArguments parsedArguments = new ParsedArguments();

        extractNonPrefixedArgumentValue(argumentsString, prefixParsingDatas, parsedArguments);

        for (int i = 0; i < prefixParsingDatas.size() - 1; i++) {
            extractMiddleArgumentValue(argumentsString, prefixParsingDatas, i, parsedArguments);
        }

        extractLastArgumentValue(argumentsString, prefixParsingDatas, parsedArguments);

        return parsedArguments;
    }

    private void extractNonPrefixedArgumentValue(String argumentsString,
                                                 List<PrefixParsingData> prefixParsingDatas,
                                                 ParsedArguments parsedArguments) {
        if (prefixParsingDatas.isEmpty()) {
            parsedArguments.addArgument(this.nonPrefixedArgument, argumentsString.trim());
            return;
        }

        String value = "";
        PrefixParsingData firstArg = prefixParsingDatas.get(0);
        if (firstArg.getStartPos() > 0) {
            value = argumentsString.substring(0, firstArg.getStartPos()).trim();
        }
        parsedArguments.addArgument(this.nonPrefixedArgument, value);
    }

    private void extractMiddleArgumentValue(String argumentsString,
                                            List<PrefixParsingData> prefixParsingDatas,
                                            int prefixIndex,
                                            ParsedArguments parsedArguments) {
        PrefixParsingData currentArg = prefixParsingDatas.get(prefixIndex);
        PrefixParsingData nextArg = prefixParsingDatas.get(prefixIndex + 1);
        int valueStartPos = currentArg.getStartPos() + currentArg.getPrefix().length();
        String value = argumentsString.substring(valueStartPos, nextArg.getStartPos());

        parsedArguments.addArgument(currentArg.getArgument(), value.trim());
    }

    private void extractLastArgumentValue(String argumentsString,
                                          List<PrefixParsingData> prefixParsingDatas,
                                          ParsedArguments parsedArguments) {
        if (prefixParsingDatas.isEmpty()) {
            return;
        }
        PrefixParsingData lastArg = prefixParsingDatas.get(prefixParsingDatas.size() - 1);
        int valueStartPos = lastArg.getStartPos() + lastArg.getPrefix().length();
        String value = argumentsString.substring(valueStartPos);

        parsedArguments.addArgument(lastArg.getArgument(), value.trim());
    }
}
