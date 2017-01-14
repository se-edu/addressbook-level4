package seedu.address.logic.parser;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.*;
import seedu.address.logic.parser.ArgumentTokenizer.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

/**
 * Parses user input.
 */
public class Parser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Pattern PERSON_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    private static final String PERSON_INDEX_RANGE_INDICATOR = "-";

    private static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

    private static final Prefix phoneNumberPrefix = new Prefix("p/");
    private static final Prefix emailPrefix = new Prefix("e/");
    private static final Prefix addressPrefix = new Prefix("a/");
    private static final Prefix tagsPrefix = new Prefix("t/");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return prepareAdd(arguments);

        case SelectCommand.COMMAND_WORD:
            return prepareSelect(arguments);

        case DeleteCommand.COMMAND_WORD:
            return prepareDelete(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return prepareFind(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Parses arguments in the context of the add person command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareAdd(String args) {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(phoneNumberPrefix, emailPrefix,
                                                                addressPrefix, tagsPrefix);
        argsTokenizer.tokenize(args);
        try {
            return new AddCommand(
                    argsTokenizer.getPreamble().get(),
                    argsTokenizer.getValue(phoneNumberPrefix).get(),
                    argsTokenizer.getValue(emailPrefix).get(),
                    argsTokenizer.getValue(addressPrefix).get(),
                    toSet(argsTokenizer.getAllValues(tagsPrefix))
            );
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    private Set<String> toSet(Optional<List<String>> tagsOptional) {
        List<String> tags = tagsOptional.orElse(Collections.emptyList());
        return new HashSet<>(tags);
    }

    /**
     * Parses arguments in the context of the delete person command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDelete(String args) {
        final List<String> tokenized = Arrays.asList(args.trim().split("\\s+"));
        final Optional<List<IndexRange>> indexRanges = parseRangedIndices(tokenized);
        if (!indexRanges.isPresent()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
        return new DeleteCommand(IndexRange.getAllValues(indexRanges.get()));
    }

    /**
     * Parses arguments in the context of the select person command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareSelect(String args) {
        Optional<Integer> index = parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }

        return new SelectCommand(index.get());
    }

    /**
     * Returns a {@code List} of {@code IndexRanges} extracted from {@code tokens}.
     *
     * Returns an {@code Optional.empty()} otherwise.
     * @see #parseRangedIndex(String)
     */
    private Optional<List<IndexRange>> parseRangedIndices(List<String> tokens) {
        final List<IndexRange> indexRanges = new ArrayList<>();
        for (String token : tokens) {
            final Optional<IndexRange> indexRange = parseRangedIndex(token);
            if (!indexRange.isPresent()) {
                return Optional.empty();
            }

            indexRanges.add(indexRange.get());
        }
        return Optional.of(indexRanges);
    }

    /**
     * Returns an {@code IndexRange} extracted from {@code token} if it contains either
     * unsigned integers surrounding a {@code PERSON_INDEX_RANGE_INDICATOR}, or just
     * unsigned integers alone, e.g. 1-2 or 10.<br>
     *
     * If the latter is encountered, an {@code IndexRange} with the same start and end will be used
     * to represent it. e.g. 5 -> 5-5.<br>
     *
     * Returns an {@code Optional.empty()} otherwise.
     */
    private Optional<IndexRange> parseRangedIndex(String token) {
        // splitting with a negative limit to keep trailing empty strings when indices surrounding the
        // PERSON_INDEX_RANGE_INDICATOR are missing e.g. "5-" splits into ["5", ""].
        final String[] components = token.split(PERSON_INDEX_RANGE_INDICATOR, -1);
        final Optional<Integer> startIndex = parseIndex(components[0]);
        final Optional<Integer> endIndex = components.length > 1 ? parseIndex(components[1]) : startIndex;
        if (components.length > 2 || !startIndex.isPresent() || !endIndex.isPresent()) {
            return Optional.empty();
        }
        return Optional.of(new IndexRange(startIndex.get(), endIndex.get()));
    }

    /**
     * Returns the specified index in the {@code command} if it is an unsigned integer
     *
     * Returns an {@code Optional.empty()} otherwise.
     */
    private Optional<Integer> parseIndex(String command) {
        final Matcher matcher = PERSON_INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String index = matcher.group("targetIndex");
        if (!StringUtil.isUnsignedInteger(index)) {
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(index));

    }

    /**
     * Parses arguments in the context of the find person command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareFind(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new FindCommand(keywordSet);
    }

    /**
     * Represents an ascending index range with a start and end (inclusive).
     */
    private static class IndexRange {
        private final int start;
        private final int end;

        private IndexRange(int start, int end) {
            assert start > 0 && end > 0;
            // Forcing ascending order.
            this.start = Math.min(start, end);
            this.end = Math.max(start, end);
        }

        /**
         * Returns all intermediate values of this index range's start and end (inclusive) in a {@code List}.<br>
         * E.g. for an {@code IndexRange} with start 2 and end 6.<br>
         * This method returns an {@code Integer} list: {@code [2, 3, 4, 5, 6]}.
         */
        private List<Integer> getAllValues() {
            return IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
        }

        /**
         * Returns all intermediate values of every {@code IndexRange}'s start and end (inclusive) of {@code ranges}
         * in a {@code List}. The original order of indices is maintained from {@code ranges}. <br>
         * Essentially, this method calls {@link #getAllValues()} of each {@code IndexRange} in {@code ranges}
         * and flattens the resulting lists into a single list.<br>
         * E.g. {@code ranges} containing the following index ranges (start, end): {@code [(1, 5), (3, 7), (2, 3)]}.<br>
         * This method returns an {@code Integer} list: {@code [1, 2, 3, 4, 5, 3, 4, 5, 6, 7, 2, 3]}.
         * @see #getAllValues()
         */
        private static List<Integer> getAllValues(List<IndexRange> ranges) {
            return ranges.stream().flatMap(range -> range.getAllValues().stream()).collect(Collectors.toList());
        }
    }

}
