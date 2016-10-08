package seedu.address.logic.parser;

import java.util.*;

/**
 * Parses arguments string of the form: non-prefix-args <prefix>value <prefix>value ...
 * An argument's value is assumed to not contain its prefix and any leading or trailing
 * whitespaces will be discarded. Argument may be allowed to be repeated or not. If a
 * non-repeatable argument is repeated, its last value will take precedence.
 */
public class CommandTokenizer {
    /**
     * Describes an argument to be parsed
     */
    public abstract static class Argument {
        protected final String name;

        public Argument(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public abstract boolean isPrefixed();
    }

    /**
     * Describes a non-prefixed argument.
     */
    public static class NonPrefixedArgument extends Argument {
        public NonPrefixedArgument(String name) {
            super(name);
        }

        @Override
        public boolean isPrefixed() {
            return false;
        }
    }

    /**
     * Describes a prefixed argument. Each argument should have a unique name and prefix.
     * A prefix should not be a substring of any other prefixes. Otherwise, behaviour is undefined.
     */
    public abstract static class PrefixedArgument extends Argument {
        private final String prefix;

        public PrefixedArgument(String name, String prefix) {
            super(name);
            this.prefix = prefix;
        }

        public String getPrefix() {
            return this.prefix;
        }

        @Override
        public boolean isPrefixed() {
            return true;
        }

        public abstract boolean isRepeatable();
    }

    /**
     * Describes a repeatable prefixed argument. All values of this argument are accumulated
     */
    public static class RepeatableArgument extends PrefixedArgument {
        public RepeatableArgument(String name, String prefix) {
            super(name, prefix);
        }

        @Override
        public boolean isRepeatable() {
            return true;
        }
    }

    /**
     * Describes a non-repeatable prefixed argument. Later value of this argument overrides earlier ones.
     */
    public static class NonRepeatableArgument extends PrefixedArgument {
        public NonRepeatableArgument(String name, String prefix) {
            super(name, prefix);
        }

        @Override
        public boolean isRepeatable() {
            return false;
        }
    }

    /**
     * Stores values for all arguments extracted from the arguments string
     */
    public static class ParsedArguments {
        private String nonPrefixArgument = "";
        private Map<String, String> nonRepeatableArguments = new HashMap<>();
        private Map<String, List<String>> repeatableArguments = new HashMap<>();

        /**
         * Adds the result value for an argument
         * @return false if the argument is non-repeatable and already exists
         */
        public boolean addArgument(Argument argument, String value) {
            if (!argument.isPrefixed()) {
                addNonPrefixArgument(value);
                return true;
            }
            if (((PrefixedArgument) argument).isRepeatable()) {
                addRepeatableArgument(argument.name, value);
                return true;
            }
            return addNonRepeatableArgument(argument.name, value);
        }

        private void addNonPrefixArgument(String value) {
            this.nonPrefixArgument = value;
        }

        /**
         * @param name name of the argument
         * @param value value of the argument
         * @return false if the argument already exists. New value overrides old one
         */
        private boolean addNonRepeatableArgument(String name, String value) {
            boolean isExisted = this.nonRepeatableArguments.containsKey(name);
            this.nonRepeatableArguments.put(name, value);
            return isExisted;
        }

        /**
         * @param name name of the argument
         * @param value value of the argument
         */
        private void addRepeatableArgument(String name, String value) {
            if (this.repeatableArguments.containsKey(name)) {
                this.repeatableArguments.get(name).add(value);
                return;
            }
            List<String> argumentValues = new ArrayList<>();
            argumentValues.add(value);
            this.repeatableArguments.put(name, argumentValues);
        }

        public Optional<String> getArgumentValue(NonRepeatableArgument argument) {
            if (!this.nonRepeatableArguments.containsKey(argument.getName())) {
                return Optional.empty();
            }
            return Optional.of(this.nonRepeatableArguments.get(argument.getName()));
        }

        public Optional<List<String>> getArgumentValue(RepeatableArgument argument) {
            if (!this.repeatableArguments.containsKey(argument.getName())) {
                return Optional.empty();
            }
            return Optional.of(this.repeatableArguments.get(argument.getName()));
        }

        public Optional<String> getArgumentValue(NonPrefixedArgument argument) {
            if (this.nonPrefixArgument.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(this.nonPrefixArgument);
        }
    }

    /**
     * Intermediate data for each prefix argument in the arguments string during parsing phase
     */
    private class PrefixParsingData {
        private int startPos;
        private PrefixedArgument argument;

        public PrefixParsingData(PrefixedArgument argument, int startPos) {
            this.argument = argument;
            this.startPos = startPos;
        }

        public String getPrefix() {
            return this.argument.getPrefix();
        }

        public int getStartPos() {
            return this.startPos;
        }

        public PrefixedArgument getArgument() {
            return this.argument;
        }
    }

    private final List<PrefixedArgument> prefixedArguments;
    private final NonPrefixedArgument nonPrefixedArgument;

    /**
     * Creates an ArgumentParser that can parse arguments string as described by the `arguments` list
     */
    public CommandTokenizer(List<Argument> arguments) {
        this.prefixedArguments = filterPrefixedArguments(arguments);
        this.nonPrefixedArgument = getNonPrefixedArgument(arguments);
    }

    public List<PrefixedArgument> filterPrefixedArguments(List<Argument> arguments) {
        List<PrefixedArgument> prefixedArguments = new ArrayList<>();
        for (Argument arg : arguments) {
            if (arg.isPrefixed()) {
                prefixedArguments.add((PrefixedArgument) arg);
            }
        }
        return prefixedArguments;
    }

    private NonPrefixedArgument getNonPrefixedArgument(List<Argument> arguments) {
        for (Argument arg : arguments) {
            if (!arg.isPrefixed()) {
                return (NonPrefixedArgument) arg;
            }
        }
        return new NonPrefixedArgument("");
    }

    /**
     * @param argumentsString arguments string of the form: non-prefix-args <prefix>data <prefix>data ...
     */
    public ParsedArguments parse(String argumentsString) {
        List<PrefixParsingData> prefixParsingDatas = new ArrayList<>();

        for (PrefixedArgument prefixedArgument : this.prefixedArguments) {
            prefixParsingDatas.addAll(extractPrefixParsingData(argumentsString, prefixedArgument));
        }
        prefixParsingDatas.sort((arg1, arg2) -> arg1.startPos - arg2.startPos);
        return extractArgumentValues(argumentsString, prefixParsingDatas);
    }

    /**
     * Extracts parsing data for a prefixed argument specific to an arguments string
     */
    private List<PrefixParsingData> extractPrefixParsingData(String argumentsString,
                                                             PrefixedArgument prefixedArgument) {
        List<PrefixParsingData> prefixParsingDatas = new ArrayList<>();
        int argumentStart = argumentsString.indexOf(prefixedArgument.getPrefix());
        while (argumentStart != -1) {
            PrefixParsingData prefixParsingData = new PrefixParsingData(prefixedArgument, argumentStart);
            prefixParsingDatas.add(prefixParsingData);
            argumentStart = argumentsString.indexOf(prefixedArgument.getPrefix(), argumentStart + 1);
        }
        return prefixParsingDatas;
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
