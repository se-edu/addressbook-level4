package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TickTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;



/**
 * Parses the given String of arguments when the ticktask command is typed
 */
public class TickTaskCommandParser implements Parser<TickTaskCommand> {
    /**
     * Parses the given {@code String} of arguements in the context of TickTaskCommand
     * @param args
     * @return a TickTaskCommand Object for execution
     * @throws ParseException if the user does not conform the expected format
     */
    @Override
    public TickTaskCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new TickTaskCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    TickTaskCommand.MESSAGE_USAGE, pe));
        }

    }
}
