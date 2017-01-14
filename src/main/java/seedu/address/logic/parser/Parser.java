package seedu.address.logic.parser;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.logic.parser.ArgumentTokenizer.Prefix;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
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

        case EditCommand.COMMAND_WORD:
            return prepareEdit(arguments);

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
    * Splits a preamble string into ordered fields.
    * @return A list of size {@code numFields} where the ith element is the ith field value if specified in
    *         the input, {@code Optional.empty()} otherwise.
    */
    private List<Optional<String>> splitPreamble(String preamble, int numFields) {
        return Arrays.stream(Arrays.copyOf(preamble.split("\\s+", numFields), numFields))
                .map(Optional::ofNullable)
                .collect(Collectors.toList());
    }

    /**
     * Parses arguments in the context of the edit person command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareEdit(String args) {
        assert args != null;
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(phoneNumberPrefix, emailPrefix,
                                                                addressPrefix, tagsPrefix);
        argsTokenizer.tokenize(args);
        List<Optional<String>> preambleFields = splitPreamble(argsTokenizer.getPreamble().orElse(""), 2);

        Optional<Integer> index = preambleFields.get(0).flatMap(this::parseIndex);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        try {
            editPersonDescriptor.setName(parseName(preambleFields.get(1)));
            editPersonDescriptor.setPhone(parsePhone(argsTokenizer.getValue(phoneNumberPrefix)));
            editPersonDescriptor.setEmail(parseEmail(argsTokenizer.getValue(emailPrefix)));
            editPersonDescriptor.setAddress(parseAddress(argsTokenizer.getValue(addressPrefix)));
            editPersonDescriptor.setTags(parseTagsForEdit(toSet(argsTokenizer.getAllValues(tagsPrefix))));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            return new IncorrectCommand(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index.get(), editPersonDescriptor);
    }

    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>} if {@code name} is present.
     */
    private Optional<Name> parseName(Optional<String> name) throws IllegalValueException {
        assert name != null;
        return name.isPresent() ? Optional.of(new Name(name.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> phone} into an {@code Optional<Phone>} if {@code phone} is present.
     */
    private Optional<Phone> parsePhone(Optional<String> phone) throws IllegalValueException {
        assert phone != null;
        return phone.isPresent() ? Optional.of(new Phone(phone.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> address} into an {@code Optional<Address>} if {@code address} is present.
     */
    private Optional<Address> parseAddress(Optional<String> address) throws IllegalValueException {
        assert address != null;
        return address.isPresent() ? Optional.of(new Address(address.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> email} into an {@code Optional<Email>} if {@code email} is present.
     */
    private Optional<Email> parseEmail(Optional<String> email) throws IllegalValueException {
        assert email != null;
        return email.isPresent() ? Optional.of(new Email(email.get())) : Optional.empty();
    }

    /**
     * Parses {@code Collection<String> tags} into an {@code Optional<UniqueTagList>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Optional<UniqueTagList>} containing zero tags.
     */
    private Optional<UniqueTagList> parseTagsForEdit(Collection<String> tags) throws IllegalValueException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(parseTags(tagSet));
    }

    /**
     * Parses {@code Collection<String> tags} into an {@code UniqueTagList}.
     */
    private UniqueTagList parseTags(Collection<String> tags) throws IllegalValueException {
        assert tags != null;
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        return new UniqueTagList(tagSet);
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
