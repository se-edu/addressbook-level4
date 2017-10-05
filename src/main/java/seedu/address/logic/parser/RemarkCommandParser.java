package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RemarkCommand object
 */
public class RemarkCommandParser implements Parser <RemarkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemarkCommand
     * and returns an RemarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);

        String[] arguments = args.split("\\s");

        String index = arguments[0];
        String str = arguments[1];

        if(index == null || str == null)
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        else
            return new RemarkCommand(Integer.parseInt(index), str);
    }
}
