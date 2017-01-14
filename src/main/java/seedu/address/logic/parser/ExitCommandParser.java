package seedu.address.logic.parser;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;

/**
 * Parses input arguments in the context of the Exit command
 */
public class ExitCommandParser extends CommandParser {

    @Override
    public Command prepareCommand(String args) {
        return new ExitCommand();
    }
}
