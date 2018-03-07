package seedu.address.logic.parser;

import seedu.address.logic.commands.LockCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parse arguments for LockCommand
 */
public class LockCommandParser implements Parser<LockCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LockCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();


        return trimmedArgs.isEmpty() ? new LockCommand() : new LockCommand(trimmedArgs);
    }
}
