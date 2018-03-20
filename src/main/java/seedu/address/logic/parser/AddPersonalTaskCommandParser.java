package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DATE_TIME;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DURATION;
import static seedu.address.commons.core.Messages.MESSAGE_TASK_TIMING_CLASHES;
import static seedu.address.model.Schedule.isTaskClash;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import seedu.address.logic.commands.AddPersonalTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import seedu.address.model.person.exceptions.DurationParseException;
import seedu.address.model.person.exceptions.TimingClashException;
import seedu.address.model.personal.PersonalTask;


/**
 * Parses input arguments and creates a new AddPersonalTaskCommand object.
 */
public class AddPersonalTaskCommandParser implements Parser<AddPersonalTaskCommand> {

    private static final String DURATION_VALIDATION_REGEX = "([0-9]|1[0-9]|2[0-3])h([0-5][0-9]|[0-9])m";
    private static final String INPUT_FORMAT_VALIDATION_REGEX = "(\\d{2}/\\d{2}/\\d{4})\\s\\d{2}:\\d{2}\\s"
            + "\\d{1,2}h\\d{1,2}m.*";
    private static final int MAXIMUM_AMOUNT_OF_TASK_PARAMETER = 4;
    private static final int INDEX_OF_DESCRIPTION = 3;
    private static final int INDEX_OF_DATE = 0;
    private static final int INDEX_OF_TIME = 1;
    private static final int INDEX_OF_DURATION = 2;

    private static String duration;
    private static String description = "";
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm")
            .withResolverStyle(ResolverStyle.STRICT);
    private static LocalDateTime taskDateTime;

    /**
     * Parses the given {@code String} of arguments in the context of the AddPersonalTaskCommand
     * and returns an AddPersonalTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddPersonalTaskCommand parse(String task) throws ParseException {
        if (!task.trim().matches(INPUT_FORMAT_VALIDATION_REGEX)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPersonalTaskCommand.MESSAGE_USAGE));
        }
        try {
            parseTask(task.trim());
        } catch (DateTimeParseException dtpe) {
            throw new ParseException(dtpe.getMessage() + "\n"
                    + AddPersonalTaskCommand.MESSAGE_USAGE);
        } catch (DurationParseException dpe) {
            throw new ParseException(dpe.getMessage() + "\n"
                    + AddPersonalTaskCommand.MESSAGE_USAGE);
        } catch (TimingClashException tce) {
            //not a ParseException. Should be handled in future milestone.
            throw new ParseException(MESSAGE_TASK_TIMING_CLASHES);
        }
        return new AddPersonalTaskCommand(new PersonalTask(taskDateTime, duration, description));
    }

    /**
     * Parses the task into date, time, duration and description
     *
     * @param task to be parsed
     * @throws DateTimeParseException if date and time given is not valid
     * @throws DurationParseException if duration format is invalid
     * @throws TimingClashException   if there is a timing clash
     */
    private static void parseTask(String task) throws DateTimeParseException,
            DurationParseException, TimingClashException {

        String[] arguments = task.split("\\s", MAXIMUM_AMOUNT_OF_TASK_PARAMETER);
        parseDescription(arguments);
        try {
            parseDateTime(arguments);
            parseDuration(arguments);
        } catch (DateTimeParseException dtpe) {
            throw new DateTimeParseException(MESSAGE_INVALID_DATE_TIME, dtpe.getParsedString(), dtpe.getErrorIndex());
        } catch (DurationParseException dpe) {
            throw new DurationParseException(dpe.getMessage());
        }
        if (isTaskClash(taskDateTime, duration)) {
            throw new TimingClashException(MESSAGE_TASK_TIMING_CLASHES);
        }
    }

    /**
     * Parses task into its description.
     *
     * @param arguments consist the components of task.
     */
    private static void parseDescription(String[] arguments) {
        if (hasDescription(arguments)) {
            description = arguments[INDEX_OF_DESCRIPTION];
        } else {
            description = "";
        }
    }

    /**
     * Returns true if description is provided in the task.
     */
    private static boolean hasDescription(String[] arguments) {
        return arguments.length == MAXIMUM_AMOUNT_OF_TASK_PARAMETER;
    }

    /**
     * Parses task into its date and time.
     *
     * @param arguments consist the components of task.
     * @throws DateTimeParseException if the input is invalid
     */
    private static void parseDateTime(String[] arguments) {
        String stringDateTime = arguments[INDEX_OF_DATE] + " " + arguments[INDEX_OF_TIME];
        taskDateTime = LocalDateTime.parse(stringDateTime, formatter);
    }

    /**
     * Parses task into its duration
     *
     * @param arguments consist the components of task.
     * @throws DurationParseException if duration format is invalid
     */
    private static void parseDuration(String[] arguments) throws DurationParseException {
        duration = arguments[INDEX_OF_DURATION];
        if (!duration.matches(DURATION_VALIDATION_REGEX)) {
            throw new DurationParseException(MESSAGE_INVALID_DURATION);
        }
    }
}
