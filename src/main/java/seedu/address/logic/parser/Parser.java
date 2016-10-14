package seedu.address.logic.parser;

import seedu.address.logic.commands.*;
import seedu.address.commons.util.StringUtil;
import seedu.address.commons.exceptions.IllegalValueException;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");

    private static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

    private static final Pattern TASK_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<name>[^;]+)"
            		+ "(( (?<isTimePrivate>p?)t;(?<time>[^;]+)))"
                    + "(( et;(?<period>[^;]+))?)"
            		+ "(( (?<isDescriptionPrivate>p?)d;(?<description>[^;]+))?)"
            		+ "(( (?<isAddressPrivate>p?)a;(?<address>[^/]+))?)"
            		+ "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of tags

    private static final Pattern PERSON_EDIT_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<targetIndex>\\d)"
                    + "(( (?<name>(?:[^;]+)))?)"
                    + "(( (?<isPhonePrivate>p?)t;(?<phone>[^;]+))?)"
                    + "(( et;(?<period>[^;]+))?)"
                    + "(( (?<isEmailPrivate>p?)d;(?<email>[^;]+))?)"
                    + "(( (?<isAddressPrivate>p?)a;(?<address>[^/]+))?)"
                    + "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of tags

    public static final String TIME_VALIDATION_REGEX = "((1[012]|0?[1-9])[:.]?[0-5][0-9]([aApP][mM]))|"
            + "(([01]\\d|2[0-3])[:.]?([0-5]\\d))";

    public static final String DATE_VALIDATION_REGEX = "(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]|"
            + "(?:Jan|Mar|May|Jul|Aug|Oct|Dec)))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2]|"
            + "(?:Jan|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec))\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^"
            + "(?:29(\\/|-|\\.)(?:0?2|(?:Feb))\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|"
            + "(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9]|"
            + "(?:Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep))|(?:1[0-2]|(?:Oct|Nov|Dec)))"
            + "\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})";

    public static final String DATE_TIME_VALIDATION_REGEX = DATE_VALIDATION_REGEX
            +"(\\s("+TIME_VALIDATION_REGEX+")(\\s("+TIME_VALIDATION_REGEX+"))?)?";

    public static final String MESSAGE_DATE_TIME_CONSTRAINTS = "Task Dates and Time should be in valid UK-format "
            + "Date [Optional]Time [Optional]Time"
            + "DD/MMM/YYYY or DD/MM/YYYY or DD.MM.YYYY or DD.MMM.YYY or DD-MM-YYYY or DD-MMM-YYYY"
            + "12Hour format with AM/PM required or 24Hour format without AM/PM"
            + "eg: 10-12-2012 09:00AM 23:59PM";
    enum TaskType { UNTIMED, DEADLINE, TIMERANGE }

    public Parser() {}

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

        case ViewCommand.COMMAND_WORD:
        	return prepareView(arguments);

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
        	return new RedoCommand();

        case EditCommand.COMMAND_WORD:
            return prepareEdit(arguments);

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Parses arguments in the context of the add task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareAdd(String args){
        String[] dateTimeArgs;
        final Matcher matcher = TASK_DATA_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        try {
            String validateDateTimeArgs = matcher.group("time");
            if(!validateDateTimeArgs.matches(DATE_TIME_VALIDATION_REGEX)){
                throw new IllegalValueException(MESSAGE_DATE_TIME_CONSTRAINTS);
            }
            dateTimeArgs = prepareAddTimeArgs(validateDateTimeArgs);
            TaskType taskType = TaskType.values()[(dateTimeArgs.length-1)];
            switch (taskType) {
                case UNTIMED:
                    return new AddCommand(
                            matcher.group("name"),
                            matcher.group("time")==null?" ":matcher.group("time"),
                            matcher.group("period")==null?"2359":matcher.group("period"),
                            matcher.group("description")==null?" ":matcher.group("description"),
                            matcher.group("address")==null?" ":matcher.group("address"),
                            getTagsFromArgs(matcher.group("tagArguments"))
                    );
                case DEADLINE:
                    return new AddCommand(
                            matcher.group("name"),
                            dateTimeArgs[0], dateTimeArgs[1],
                            matcher.group("period")==null?"2359":matcher.group("period"),
                            matcher.group("description")==null?" ":matcher.group("description"),
                            matcher.group("address")==null?" ":matcher.group("address"),
                            getTagsFromArgs(matcher.group("tagArguments"))
                    );
                case TIMERANGE:
                    return new AddCommand(
                            matcher.group("name"),
                            dateTimeArgs[0], dateTimeArgs[1], dateTimeArgs[2],
                            matcher.group("period")==null?"2359":matcher.group("period"),
                            matcher.group("description")==null?" ":matcher.group("description"),
                            matcher.group("address")==null?" ":matcher.group("address"),
                            getTagsFromArgs(matcher.group("tagArguments"))
                    );
                default:
                    assert false: "Not suppose to happen.";
                    return null;
            }

        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    private String[] prepareAddTimeArgs(String dateTimeInput) {
        String[] dateTimeSplitted = dateTimeInput.split(" ");
        return dateTimeSplitted;
    }

    /**
     * Extracts the new task's tags from the add command's tag arguments string.
     * Merges duplicate tag strings.
     */
    private static Set<String> getTagsFromArgs(String tagArguments) throws IllegalValueException {
        // no tags
        if (tagArguments.isEmpty()) {
            return Collections.emptySet();
        }
        // replace first delimiter prefix, then split
        final Collection<String> tagStrings = Arrays.asList(tagArguments.replaceFirst(" t/", "").split(" t/"));
        return new HashSet<>(tagStrings);
    }

    /**
     * Parses arguments in the context of the delete task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDelete(String args) {

        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        return new DeleteCommand(index.get());
    }

    /**
     * Parses arguments in the context of the delete task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareView(String args) {

        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }

        return new ViewCommand(index.get());
    }

    /**
     * Parses arguments in the context of the select task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareSelect(String args) {
        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }

        return new SelectCommand(index.get());
    }

    /**
     * Returns the specified index in the {@code command} IF a positive unsigned integer is given as the index.
     *   Returns an {@code Optional.empty()} otherwise.
     */
    private Optional<Integer> parseIndex(String command) {
        final Matcher matcher = TASK_INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String index = matcher.group("targetIndex");
        if(!StringUtil.isUnsignedInteger(index)){
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(index));

    }

    /**
     * Parses arguments in the context of the find task command.
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
     * Parses arguments in the context of the add task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareEdit(String args){
        final Matcher matcher = PERSON_EDIT_DATA_ARGS_FORMAT.matcher(args.trim());
        // Validate arg string format
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        try {
            final Optional<Integer> targetIndex = parseIndex(matcher.group("targetIndex"));
            if(!targetIndex.isPresent()){
                return new IncorrectCommand(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
            }
            return new EditCommand(
                    targetIndex.get(),
                    matcher.group("name")==null?" ":matcher.group("name"),
                    matcher.group("phone")==null?" ":matcher.group("phone"),
                    matcher.group("period")==null?" ":matcher.group("period"),
                    matcher.group("email")==null?" ":matcher.group("email"),
                    matcher.group("address")==null?" ":matcher.group("address"),
                    getTagsFromArgs(matcher.group("tagArguments"))
            );
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

}