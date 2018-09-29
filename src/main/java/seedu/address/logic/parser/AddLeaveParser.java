package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPROVAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddLeaveCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.leave.Approval;
import seedu.address.model.leave.Date;
import seedu.address.model.leave.EmployeeId;
import seedu.address.model.leave.Leave;

/**
 * Parses input arguments and creates a new AddLeaveCommand object
 */
public class AddLeaveParser implements Parser<AddLeaveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddLeaveCommand
     * and returns an AddLeaveCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddLeaveCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NRIC, PREFIX_DATE, PREFIX_APPROVAL);

        if (!arePrefixesPresent(argMultimap, PREFIX_NRIC, PREFIX_DATE, PREFIX_APPROVAL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLeaveCommand.MESSAGE_USAGE));
        }

        EmployeeId employeeId = ParserUtil.parseEmployeeId(argMultimap.getValue(PREFIX_NRIC).get());
        Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        Approval status = ParserUtil.parseApproval(argMultimap.getValue(PREFIX_APPROVAL).get());

        Leave leave = new Leave (employeeId, date, status);

        return new AddLeaveCommand(leave);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
