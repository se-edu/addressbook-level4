package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DATE_TIME;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DURATION;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_INPUT_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_TASK_TIMING_CLASHES;
import static seedu.address.model.UniqueTaskList.checkTimeClash;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;

import seedu.address.logic.commands.AddTuitionTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import seedu.address.model.person.exceptions.DurationParseException;
import seedu.address.model.person.exceptions.TimingClashException;

/**
 * Parses input arguments and creates a new AddTuitionTaskCommand object
 */
public class AddTuitionTaskCommandParser implements Parser<AddTuitionTaskCommand> {

    private static final String INPUT_FORMAT_VALIDATION_REGEX = "\\d+\\s(\\d{2}/\\d{2}/\\d{4})\\s\\d{2}:\\d{2}\\s"
            + "\\d{1,2}h\\d{1,2}m.*";
    private static final int MAXIMUM_AMOUNT_OF_TASK_PARAMETER = 5;
    private static final int INDEX_OF_PERSON_INDEX = 0;
    private static final int INDEX_OF_DATE = 1;
    private static final int INDEX_OF_TIME = 2;
    private static final int INDEX_OF_DURATION = 3;

    /**
     * Parses the given {@code String} of arguments in the context of the AddTuitionTaskCommand
     * and returns an AddTuitionTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTuitionTaskCommand parse(String args) throws ParseException {
        if (!args.trim().matches(INPUT_FORMAT_VALIDATION_REGEX)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuitionTaskCommand.MESSAGE_USAGE));
        }

        String[] arguments = args.trim().split("\\s+", MAXIMUM_AMOUNT_OF_TASK_PARAMETER);
        try {
            Index personIndex = ParserUtil.parseIndex(arguments[INDEX_OF_PERSON_INDEX]);
            LocalDateTime taskDateTime =
                    ParserUtil.parseDateTime(arguments[INDEX_OF_DATE] + " " + arguments[INDEX_OF_TIME]);
            String duration = ParserUtil.parseDuration(arguments[INDEX_OF_DURATION]);
            String description = ParserUtil.parseDescription(arguments, MAXIMUM_AMOUNT_OF_TASK_PARAMETER);
            checkTimeClash(taskDateTime, duration);

            return new AddTuitionTaskCommand(personIndex, taskDateTime, duration, description);
        } catch (DateTimeParseException dtpe) {
            throw new ParseException(MESSAGE_INVALID_DATE_TIME + "\n"
                    + AddTuitionTaskCommand.MESSAGE_USAGE);
        } catch (DurationParseException dpe) {
            throw new ParseException(MESSAGE_INVALID_DURATION + "\n"
                    + AddTuitionTaskCommand.MESSAGE_USAGE);
        } catch (TimingClashException tce) {
            throw new ParseException(MESSAGE_TASK_TIMING_CLASHES + "\n"
                    + AddTuitionTaskCommand.MESSAGE_USAGE);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_INPUT_FORMAT, AddTuitionTaskCommand.MESSAGE_USAGE));
        }
    }
}
