package seedu.address.logic.parser;

import seedu.address.logic.commands.*;
import seedu.address.logic.parser.ArgumentTokenizer.Prefix;
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
            Pattern.compile("(?<name>[^;/]+)"
            		+ "(( (?<isTimePrivate>p?)t;(?<time>[^;]+))?)"
                    + "(( s;(?<period>[^;]+))?)"
            		+ "(( (?<isDescriptionPrivate>p?)d;(?<description>[^;]+))?)"
            		+ "(( (?<isAddressPrivate>p?)a;(?<address>[^/]+))?)"
            		+ "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of tags

    private static final Pattern PERSON_EDIT_DATA_ARGS_FORMAT = // '/' forward slashes are reserved for delimiter prefixes
            Pattern.compile("(?<targetIndex>\\d)"
                    + "(( (?<name>(?:[^;]+)))?)"
                    + "(( (?<isPhonePrivate>p?)t;(?<phone>[^;]+))?)"
                    + "(( s;(?<period>[^;]+))?)"
                    + "(( (?<isEmailPrivate>p?)d;(?<email>[^;]+))?)"
                    + "(( (?<isAddressPrivate>p?)a;(?<address>[^/]+))?)"
                    + "(?<tagArguments>(?: t/[^/]+)*)"); // variable number of tags

    //@@author A0121261Y
    /**
     * Regex validation for time format duplicated from Time class.
     *
     */
    public static final String TIME_VALIDATION_FORMAT = "((1[012]|0?[1-9])[:.]?[0-5][0-9]([aApP][mM]))|"
            + "(([01]\\d|2[0-3])[:.]?([0-5]\\d))";

    /**
     * Regex  validation for date format duplicated from Time class.
     *
     */
    public static final String DATE_VALIDATION_FORMAT = "(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]|"
            + "(?:JAN|MAR|MAY|JUL|AUG|OCT|DEC)))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2]|"
            + "(?:JAN|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC))\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^"
            + "(?:29(\\/|-|\\.)(?:0?2|(?:Feb))\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|"
            + "(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9]|"
            + "(?:JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP))|(?:1[0-2]|(?:OCT|NOV|DEC)))"
            + "\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})";

    public static final String DATE_TIME_VALIDATION_FORMAT = DATE_VALIDATION_FORMAT
            +"(\\s("+TIME_VALIDATION_FORMAT+")(\\s("+TIME_VALIDATION_FORMAT+"))?)?";

    public static final String MESSAGE_DATE_TIME_CONSTRAINTS = "Task Dates and Time should be in valid UK-format "
            + "Date [Optional]Time [Optional]Time \n"
            + "DD/MMM/YYYY or DD/MM/YYYY or DD.MM.YYYY or DD.MMM.YYY or DD-MM-YYYY or DD-MMM-YYYY \n"
            + "12Hour format with AM/PM required or 24Hour format without AM/PM \n"
            + "eg: 10-12-2012 09:00AM 11:59PM";

    enum TaskType {UNTIMED, DEADLINE, TIMERANGE}

    private static final Prefix namePrefix = new Prefix("n;");
    private static final Prefix datePrefix = new Prefix("t;");
    private static final Prefix periodPrefix = new Prefix("s;");
    private static final Prefix descriptionPrefix = new Prefix("d;");
    private static final Prefix locationPrefix = new Prefix("a;");
    private static final Prefix tagsPrefix = new Prefix("t/");
    private static final String BLANK = "";

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

        final String commandWord = matcher.group("commandWord").toLowerCase();
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return prepareAdd(arguments);

        case LocateCommand.COMMAND_WORD:
            return prepareLocate(arguments);

        case DeleteCommand.COMMAND_WORD:
            return prepareDelete(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return prepareFind(arguments);

        case ViewCommand.COMMAND_WORD:
            return prepareView(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case SelectCommand.COMMAND_WORD:
        	return prepareSelect(arguments);

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
        	return new RedoCommand();

        case EditCommand.COMMAND_WORD:
            return prepareEdit(arguments);

        case DoneCommand.COMMAND_WORD:
            return prepareMark(arguments);

        case ConfirmCommand.COMMAND_WORD:
            return new ConfirmCommand();

        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Parses arguments in the context of the add mark command.
     *
     * @param arguments
     * @return
     */
    private Command prepareMark(String args) {
        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
        }

        return new DoneCommand(index.get());
    }

    //@@author A0121261Y
    /**
     * Parses arguments in the context of the add task command.
     *
     * case 0: no date and time , i.e no args for time found
     * case 1: date only
     * case 2: date and startTime
     * case 3: date and startTime endTime
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareAdd(String args){
        String[] dateTimeArgs = null;
        TaskType taskType;
 //       final Matcher matcher = TASK_DATA_ARGS_FORMAT.matcher(args.trim());

        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(namePrefix, datePrefix, periodPrefix, descriptionPrefix,
                locationPrefix, tagsPrefix);
        argsTokenizer.tokenize(args);

        try {
            Optional<String> taskName = getTaskNameFromArgs(argsTokenizer);
            if (!taskName.isPresent()) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
            }
            Optional<String> validateDateTimeArgs = argsTokenizer.getValue(datePrefix);
            if(validateDateTimeArgs.isPresent()) {
                validateDateTimeArgs = Optional.of(validateDateTimeArgs.get().toUpperCase());
//                if(!validateDateTimeArgs.get().matches(DATE_TIME_VALIDATION_FORMAT)){
//                TODO: FIX System.out.println("line 197 parser: " + farthestPoint(Pattern.compile(DATE_TIME_VALIDATION_FORMAT), validateDateTimeArgs.get()));;
//                    throw new IllegalValueException(MESSAGE_DATE_TIME_CONSTRAINTS);
//                }
                dateTimeArgs = prepareAddTimeArgs(validateDateTimeArgs.get());
                taskType = TaskType.values()[(dateTimeArgs.length-1)];
            }else {
                taskType = TaskType.values()[0];
            }

            switch (taskType) {
                case UNTIMED:
                    return new AddCommand(
                            taskName.get(),
                            validateDateTimeArgs.isPresent() ? dateTimeArgs[0] : null,
                            getPrefixValueElseBlank(argsTokenizer,periodPrefix), // stub
                            getPrefixValueElseBlank(argsTokenizer,descriptionPrefix),
                            getPrefixValueElseBlank(argsTokenizer,locationPrefix),
                            toSet(argsTokenizer.getAllValues(tagsPrefix))
                    );
                case DEADLINE:
                    assert dateTimeArgs != null;
                    return new AddCommand(
                            taskName.get(),
                            dateTimeArgs[0], dateTimeArgs[1],
                            getPrefixValueElseBlank(argsTokenizer,periodPrefix), // stub
                            getPrefixValueElseBlank(argsTokenizer,descriptionPrefix),
                            getPrefixValueElseBlank(argsTokenizer,locationPrefix),
                            toSet(argsTokenizer.getAllValues(tagsPrefix))
                    );
                case TIMERANGE:
                    assert dateTimeArgs != null;
                    return new AddCommand(
                            taskName.get(),
                            dateTimeArgs[0], dateTimeArgs[1], dateTimeArgs[2],
                            getPrefixValueElseBlank(argsTokenizer,periodPrefix), // stub
                            getPrefixValueElseBlank(argsTokenizer,descriptionPrefix),
                            getPrefixValueElseBlank(argsTokenizer,locationPrefix),
                            toSet(argsTokenizer.getAllValues(tagsPrefix))
                    );
                default:
                    assert false: "Not suppose to happen.";
                    return null;
            }

        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    /**
     * Takes the text before first valid prefix as task name if input does not contain namePrefix.
     *
     * @param argsTokenizer
     * @return Task Name
     * @throws IllegalValueException
     */

    private Optional<String> getTaskNameFromArgs(ArgumentTokenizer argsTokenizer) throws IllegalValueException {
        if (argsTokenizer.hasDuplicated(namePrefix)) {
            throw new IllegalValueException("There can only be 1 Task name");
        }
        String taskName = argsTokenizer.getValue(namePrefix).isPresent() ?
                argsTokenizer.getValue(namePrefix).get() :
                    argsTokenizer.getPreamble().isPresent() ? argsTokenizer.getPreamble().get(): "";

        return taskName.isEmpty() ? Optional.empty() : Optional.of(taskName.trim());
    }

    private String getPrefixValueElseBlank(ArgumentTokenizer argsTokenizer, Prefix prefix) {
        if (prefix.equals(periodPrefix)) { //stub to be remove
            return argsTokenizer.getValue(prefix).isPresent() ? argsTokenizer.getValue(prefix).get() : "2359";
        }
        return argsTokenizer.getValue(prefix).isPresent() ? argsTokenizer.getValue(prefix).get() : BLANK;
    }

    private String[] prepareAddTimeArgs(String dateTimeInput) {
        String[] dateTimeSplitted = dateTimeInput.split(" ");
        return dateTimeSplitted;
    }

    private Set<String> toSet(Optional<List<String>> tagsOptional) {
        List<String> tags = tagsOptional.orElse(Collections.emptyList());
        return new HashSet<>(tags);
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
     * Parses arguments in the context of the locate task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareLocate(String args) {
        Optional<Integer> index = parseIndex(args);
        if(!index.isPresent()){
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LocateCommand.MESSAGE_USAGE));
        }

        return new LocateCommand(index.get());
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

    //@@author A0135767U
    /**
     * Parses arguments in the context of the view task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareView(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ViewCommand.MESSAGE_USAGE));
        }

        // keywords delimited by whitespace
        final String[] keywords = matcher.group("keywords").split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        return new ViewCommand(keywordSet);
    }
    //@@author

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
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, LocateCommand.MESSAGE_USAGE));
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

    int farthestPoint(Pattern pattern, String input) {
        for (int i = input.length() - 1; i > 0; i--) {
            Matcher matcher = pattern.matcher(input.substring(0, i));
            if (!matcher.matches() && matcher.hitEnd()) {
                return i;
            }
        }
        return 0;
    }

}