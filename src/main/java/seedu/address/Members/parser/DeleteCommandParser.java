package seedu.address.Members.parser;

import seedu.address.Members.commands.DeleteMemberCommand;
import seedu.address.Members.parser.exceptions.ParseException;
import seedu.address.commons.core.index.Index;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new DeleteMemberCommand object
 */
public class DeleteCommandParser implements Parser<DeleteMemberCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteMemberCommand
     * and returns an DeleteMemberCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteMemberCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteMemberCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMemberCommand.MESSAGE_USAGE), pe);
        }
    }

}
