package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODE;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddTimetableCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

import seedu.address.model.person.timetable.Timetable;


/**
 * Parses input arguments and creates a new AddTimetableCommand object
 */
public class AddTimetableCommandParser implements Parser<AddTimetableCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddTimetableCommand and
     * returns an AddTimetableCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTimetableCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer
            .tokenize(args, PREFIX_MODE, PREFIX_FILE_NAME, PREFIX_FORMAT, PREFIX_FILE_LOCATION);
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTimetableCommand.MESSAGE_USAGE),
                pe);
        }
        if (!arePrefixesPresent(argMultimap, PREFIX_MODE, PREFIX_FILE_NAME, PREFIX_FORMAT)) {
            throw new ParseException(String
                .format(MESSAGE_INVALID_COMMAND_FORMAT, AddTimetableCommand.MESSAGE_USAGE));
        }
        String mode = ParserUtil.parseMode(argMultimap.getValue(PREFIX_MODE).get());
        String format = ParserUtil.parseFormat(argMultimap.getValue(PREFIX_FORMAT).get());
        String fileName = ParserUtil.parseFilename(argMultimap.getValue(PREFIX_FILE_NAME).get());

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        Timetable timetable = new Timetable(fileName, format);
        if (arePrefixesPresent(argMultimap, PREFIX_FILE_LOCATION)) {
            String locationFrom = ParserUtil
                .parseLocation(argMultimap.getValue(PREFIX_FILE_LOCATION).get());
            if (mode.equals("existing")) {
                timetable = new Timetable(fileName, format, locationFrom);
            }
        }
        editPersonDescriptor.setTimetable(timetable);
        return new AddTimetableCommand(index, editPersonDescriptor);
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
