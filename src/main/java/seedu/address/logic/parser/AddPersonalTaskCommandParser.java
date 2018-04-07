package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DATE_TIME;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DURATION;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import seedu.address.logic.commands.AddPersonalTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import seedu.address.model.person.exceptions.DurationParseException;
import seedu.address.model.personal.PersonalTask;

//@@author yungyung04

/**
 * Parses input arguments and creates a new AddPersonalTaskCommand object.
 */
public class AddPersonalTaskCommandParser implements Parser<AddPersonalTaskCommand> {

    private static final String INPUT_FORMAT_VALIDATION_REGEX = "(\\d{2}/\\d{2}/\\d{4})\\s\\d{2}:\\d{2}\\s"
            + "\\d{1,2}h\\d{1,2}m.*";
    private static final int MAXIMUM_AMOUNT_OF_TASK_PARAMETER = 4;
    private static final int INDEX_OF_DATE = 0;
    private static final int INDEX_OF_TIME = 1;
    private static final int INDEX_OF_DURATION = 2;

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

        String[] arguments = task.trim().split("\\s+", MAXIMUM_AMOUNT_OF_TASK_PARAMETER);
        try {
            LocalDateTime taskDateTime =
                    ParserUtil.parseDateTime(arguments[INDEX_OF_DATE] + " " + arguments[INDEX_OF_TIME]);
            String duration = ParserUtil.parseDuration(arguments[INDEX_OF_DURATION]);
            String description = ParserUtil.parseDescription(arguments, MAXIMUM_AMOUNT_OF_TASK_PARAMETER);

            return new AddPersonalTaskCommand(new PersonalTask(taskDateTime, duration, description));
        } catch (DateTimeParseException dtpe) {
            throw new ParseException(MESSAGE_INVALID_DATE_TIME + "\n"
                    + AddPersonalTaskCommand.MESSAGE_USAGE);
        } catch (DurationParseException dpe) {
            throw new ParseException(MESSAGE_INVALID_DURATION + "\n"
                    + AddPersonalTaskCommand.MESSAGE_USAGE);
        }
    }
}
