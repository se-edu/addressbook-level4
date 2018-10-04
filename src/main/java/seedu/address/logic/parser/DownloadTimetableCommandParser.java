package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_LOCATION;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DownloadTimetableCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DownloadTimetableCommand object
 */
public class DownloadTimetableCommandParser implements Parser<DownloadTimetableCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DownloadTimetableCommand
     * and returns an DownloadTimetableCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DownloadTimetableCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer
            .tokenize(args, PREFIX_FILE_LOCATION);
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                String
                    .format(MESSAGE_INVALID_COMMAND_FORMAT, DownloadTimetableCommand.MESSAGE_USAGE),
                pe);
        }
        if (!arePrefixesPresent(argMultimap, PREFIX_FILE_LOCATION)) {
            throw new ParseException(String
                .format(MESSAGE_INVALID_COMMAND_FORMAT, DownloadTimetableCommand.MESSAGE_USAGE));
        }
        String locationTo = ParserUtil
            .parseLocation(argMultimap.getValue(PREFIX_FILE_LOCATION).get());
        return new DownloadTimetableCommand(index, locationTo);
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
