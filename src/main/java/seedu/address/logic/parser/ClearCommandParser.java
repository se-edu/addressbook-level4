package seedu.address.logic.parser;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;

/**
 * Creates a new ClearCommand object
 */
public class ClearCommandParser extends CommandParser {

    @Override
    public Command prepareCommand(String args) {
        return new ClearCommand();
    }
}
