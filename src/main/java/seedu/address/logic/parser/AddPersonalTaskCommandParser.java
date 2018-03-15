package seedu.address.logic.parser;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddPersonalTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import seedu.address.model.Schedule;
import seedu.address.model.person.exceptions.DurationParseException;
import seedu.address.model.person.exceptions.TimingClashException;
import seedu.address.model.personal.PersonalTask;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new AddPersonalTaskCommand object.
 */
public class AddPersonalTaskCommandParser {

    private static final String MESSAGE_TASK_TIMING_CLASHES = "This task clashes with another task";
    private static final String MESSAGE_INVALID_DATE_TIME = "The input date and time is invalid";
    private static final String MESSAGE_INVALID_DURATION = "The duration format is invalid";
    private static final String MESSAGE_INVALID_INPUT_FORMAT = "The input format is invalid";
    private static final String DURATION_VALIDATION_REGEX = "([0-9]|1[0-9]|2[0-3])h([0-5][0-9]|[0-9])m";
    private static final int MINIMUM_AMOUNT_OF_TASK_PARAMETER = 3;
    private static final int MAXIMUM_AMOUNT_OF_TASK_PARAMETER = 4;
    private static final int INDEX_OF_DESCRIPTION = 3;
    private static final int INDEX_OF_DATE = 0;
    private static final int INDEX_OF_TIME = 1;
    private static final int INDEX_OF_DURATION = 2;

    private static String duration;
    private static String description;
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static String stringDateTime;
    private static LocalDateTime taskDateTime;

    /**
     * Parses the given {@code String} of arguments in the context of the AddTuitionTaskCommand
     * and returns an AddTuitionTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddPersonalTaskCommand parse(String task) throws ParseException {
        try {
            this.parseTask(task.trim());
        } catch (DateTimeParseException dtpe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPersonalTaskCommand.MESSAGE_USAGE));
        } catch (DurationParseException dpe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPersonalTaskCommand.MESSAGE_USAGE));
        } catch (TimingClashException tce) {
            //not a ParseException. Should be handled in future milestone.
            throw new ParseException(MESSAGE_TASK_TIMING_CLASHES);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPersonalTaskCommand.MESSAGE_USAGE));
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
    public static void parseTask(String task) throws DateTimeParseException,
            DurationParseException, TimingClashException, IllegalValueException {

        String[] arguments = task.split("\\s", 4);
        if (!canPassInitialCheck(arguments)) {
            throw new IllegalValueException(MESSAGE_INVALID_INPUT_FORMAT);
        }
        parseDescription(arguments);
        try {
            parseDateTime(arguments);
            parseDuration(arguments);
            Schedule.checkClashes(taskDateTime, duration);
        } catch (DateTimeParseException dtpe) {
            throw new DateTimeParseException(MESSAGE_INVALID_DATE_TIME, dtpe.getParsedString(), dtpe.getErrorIndex());
        } catch (DurationParseException dpe) {
            throw new DurationParseException(dpe.getMessage());
        } catch (TimingClashException tce) {
            throw new TimingClashException(tce.getMessage());
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
        }
    }

    /**
     * Returns true if description is provided in the task.
     */
    private static boolean hasDescription(String[] arguments) {
        return arguments.length == MAXIMUM_AMOUNT_OF_TASK_PARAMETER;
    }

    /**
     *Returns true if there are enough task components.
     */
    private static boolean canPassInitialCheck(String[] arguments) {
        return arguments.length >= MINIMUM_AMOUNT_OF_TASK_PARAMETER;
    }

    /**
     * Parses task into its date and time.
     *
     * @param arguments consist the components of task.
     * @throws DateTimeParseException if the input is invalid
     */
    private static void parseDateTime(String[] arguments) {
        stringDateTime = arguments[INDEX_OF_DATE] + " " + arguments[INDEX_OF_TIME];
        System.out.println(stringDateTime);
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
