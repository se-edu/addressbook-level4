package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import seedu.address.logic.commands.SelectCommand;

/**
 * Parses input arguments and creates a new SelectCommand object
 */
public class SelectCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectCommand
     * and returns an SelectCommand object for execution.
     */
    public SelectCommand parse(String args) throws ParseException {
        Optional<Integer> index = ParserUtil.parseIndex(args);
        if (!index.isPresent()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }

        return new SelectCommand(index.get());
    }

}
