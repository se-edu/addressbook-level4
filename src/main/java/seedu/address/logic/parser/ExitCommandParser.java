package seedu.address.logic.parser;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;

/**
 * Creates a new ExitCommand object
 */
public class ExitCommandParser extends CommandParser {

    @Override
    public Command prepareCommand(String args) {
        return new ExitCommand();
    }
}
