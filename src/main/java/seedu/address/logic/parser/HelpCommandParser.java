package seedu.address.logic.parser;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.HelpCommand;

/**
 * Creates a new HelpCommand object
 */
public class HelpCommandParser extends CommandParser {

    @Override
    public Command prepareCommand(String args) {
        return new HelpCommand();
    }
}
