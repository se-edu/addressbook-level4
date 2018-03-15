package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddTuitionTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import seedu.address.model.Schedule;
import seedu.address.model.person.TuitionTask;
import seedu.address.model.person.exceptions.DurationParseException;
import seedu.address.model.person.exceptions.TimingClashException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
//import java.time.format.ResolverStyle; temporarily not used
import java.util.regex.PatternSyntaxException;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new AddTuitionTaskCommand object
 */
public class AddTuitionTaskCommandParser {

    private static final String MESSAGE_TASK_TIMING_CLASHES = "This task clashes with another task";
    private static final String MESSAGE_INVALID_DATE_TIME = "The input date and time is invalid";
    private static final String MESSAGE_INVALID_DURATION = "The duration format is invalid";
    private static final String MESSAGE_INVALID_INPUT_FORMAT = "The input format is invalid";
    private static final int MINIMUM_INPUTTED_PARAMETER = 3;
    private static final int INDEX_OF_PERSON_INDEX = 0;
    private static final int INDEX_OF_TASK = 1;
    private static final int LENGTH_OF_DATE_TIME = 16;
    private static final int INDEX_OF_START_OF_DURATION = LENGTH_OF_DATE_TIME + 1;
    private static final int INVALID_INDEX = -1;
    private static final String NO_DESCRIPTION = "";
    private static final String DURATION_VALIDATION_REGEX = "([0-9]|1[0-9]|2[0-3])\\b(h)\\b([0-5][0-9])\\b(h)\\b";

    private static String duration;
    private static String description;
    private static DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("dd/MM/yyyy HH:mm");
    //        .withResolverStyle(ResolverStyle.STRICT);
    private static String stringDateTime;
    private static LocalDateTime taskDateTime;
    private static Index index;

    /**
     * Parses the given {@code String} of arguments in the context of the AddTuitionTaskCommand
     * and returns an AddTuitionTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTuitionTaskCommand parse(String args) throws ParseException {
        String[] userInput = args.trim().split("\\s", 2);
        try {
            index = ParserUtil.parseIndex(userInput[INDEX_OF_PERSON_INDEX]);
            this.parseTask(userInput[INDEX_OF_TASK].trim());
        } catch (DateTimeParseException dtpe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuitionTaskCommand.MESSAGE_USAGE));
        } catch (DurationParseException dpe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuitionTaskCommand.MESSAGE_USAGE));
        } catch (TimingClashException tce) {
            System.out.println(MESSAGE_TASK_TIMING_CLASHES);
        } catch (PatternSyntaxException pse) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuitionTaskCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuitionTaskCommand.MESSAGE_USAGE));
        }
        return new AddTuitionTaskCommand(
                new TuitionTask("dummy", taskDateTime, duration, description), index);
    }

    /**
     * Parses the task into date, time, duration and description
     *
     * @param task to be parsed
     * @throws DateTimeParseException if date and time given is not valid
     * @throws DurationParseException if duration format is invalid
     * @throws TimingClashException   if there is a timing clash
     */
    public static void parseTask(String task) throws DateTimeParseException,
            DurationParseException, TimingClashException, IllegalValueException {

        if (!canPassInitialCheck(task)) {
            throw new IllegalValueException(MESSAGE_INVALID_INPUT_FORMAT);
        }
        try {
            description = parseDescription(task);
            stringDateTime = parseDateTime(task);
            taskDateTime = LocalDateTime.parse(stringDateTime, formatter);
            duration = parseDuration(task);
            Schedule.checkClashes(taskDateTime, duration);
        } catch (DateTimeParseException dtpe) {
            throw new DateTimeParseException(MESSAGE_INVALID_DATE_TIME, dtpe.getParsedString(), dtpe.getErrorIndex());
        } catch (DurationParseException dpe) {
            throw new DurationParseException(dpe.getMessage());
        } catch (TimingClashException tce) {
            throw new TimingClashException(tce.getMessage());
        }
    }

    private static boolean canPassInitialCheck(String task) {
        String[] arguments = task.split("\\s", 4);
        return arguments.length >= MINIMUM_INPUTTED_PARAMETER;
    }

    /**
     * Parses task into its date and time.
     *
     * @param task to be parsed
     * @return date and time of the task
     */
    private static String parseDateTime(String task) throws IllegalValueException {
        try {
            return task.substring(0, LENGTH_OF_DATE_TIME);
        } catch (IndexOutOfBoundsException iobe) {
            throw new IllegalValueException(iobe.getMessage());
        }
    }

    /**
     * Parses task into its duration
     *
     * @param task to be parsed
     * @return duration of the task
     * @throws DurationParseException if duration format is invalid
     */
    private static String parseDuration(String task) throws DurationParseException {
        int indexOfEndOfDuration = task.indexOf(" ", INDEX_OF_START_OF_DURATION);
        String parsedDuration;
        if (indexOfEndOfDuration == INVALID_INDEX) {
            parsedDuration = task.substring(INDEX_OF_START_OF_DURATION);
        } else {
            parsedDuration = task.substring(INDEX_OF_START_OF_DURATION, //runtime error given a valid input
                    indexOfEndOfDuration - INDEX_OF_START_OF_DURATION);
        }
        if (!parsedDuration.trim().matches(DURATION_VALIDATION_REGEX)) {
            throw new DurationParseException(MESSAGE_INVALID_DURATION);
        }
        return parsedDuration;
    }

    /**
     * Parses task into description
     *
     * @param task to be parsed
     * @return description of the task
     */
    private static String parseDescription(String task) {
        int indexOfEndOfDuration = task.indexOf(" ", INDEX_OF_START_OF_DURATION);
        if (indexOfEndOfDuration == INVALID_INDEX) {
            return NO_DESCRIPTION;
        } else {
            return task.substring(indexOfEndOfDuration).trim();
        }
    }


}
