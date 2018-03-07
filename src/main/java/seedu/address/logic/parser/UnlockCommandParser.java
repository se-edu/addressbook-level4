package seedu.address.logic.parser;

import seedu.address.logic.commands.UnlockCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses arguments for the UnlockCommand'
 */
public class UnlockCommandParser implements Parser<UnlockCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnlockCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        return trimmedArgs.isEmpty() ? new UnlockCommand("nopassword") : new UnlockCommand(trimmedArgs);
    }
}
