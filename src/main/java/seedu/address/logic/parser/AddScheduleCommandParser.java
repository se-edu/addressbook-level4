package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEEID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE_TYPE;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddScheduleCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import seedu.address.model.person.EmployeeId;
import seedu.address.model.schedule.Date;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.Type;

/**
 * Parses input arguments and creates a new {@code RemarkCommand} object
 */
public class AddScheduleCommandParser implements Parser<AddScheduleCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddScheduleCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EMPLOYEEID, PREFIX_SCHEDULE_TYPE, PREFIX_SCHEDULE_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_EMPLOYEEID, PREFIX_SCHEDULE_TYPE, PREFIX_SCHEDULE_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddScheduleCommand.MESSAGE_USAGE));
        }

        Type type = ParserUtil.parseStatus(argMultimap.getValue(PREFIX_SCHEDULE_TYPE).get());
        Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_SCHEDULE_DATE).get());
        EmployeeId id = ParserUtil.parseEmployeeId(argMultimap.getValue(PREFIX_EMPLOYEEID).get());


        Schedule schedule = new Schedule(id, type, date);

        return new AddScheduleCommand(schedule);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

