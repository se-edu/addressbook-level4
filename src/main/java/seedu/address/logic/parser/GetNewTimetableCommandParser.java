package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.GetNewTimetableCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import seedu.address.model.person.Name;
import seedu.address.model.timetable.Timetable;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class GetNewTimetableCommandParser implements Parser<GetNewTimetableCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand and returns an
     * AddTimetableCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public GetNewTimetableCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer
                .tokenize(args, PREFIX_NAME, PREFIX_FILE_NAME, PREFIX_FILE_LOCATION, PREFIX_MODE);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_FILE_NAME, PREFIX_FILE_LOCATION,
            PREFIX_MODE)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                String
                    .format(MESSAGE_INVALID_COMMAND_FORMAT, GetNewTimetableCommand.MESSAGE_USAGE));
        }
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        String fileName = ParserUtil.parseFilename(argMultimap.getValue(PREFIX_FILE_NAME).get());
        String locationTo = ParserUtil
            .parseLocation(argMultimap.getValue(PREFIX_FILE_LOCATION).get());
        String mode = ParserUtil.parseLocation(argMultimap.getValue(PREFIX_MODE).get());
        Timetable timetable = new Timetable(name, fileName, mode);
        return new GetNewTimetableCommand(timetable, locationTo);
    }


    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap,
        Prefix... prefixes) {
        return Stream.of(prefixes)
            .allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }


}
