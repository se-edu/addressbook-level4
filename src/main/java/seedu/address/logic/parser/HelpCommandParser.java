package seedu.address.logic.parser;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.HelpCommand;

/**
 * Parses input arguments in the context of the Help command
 */
public class HelpCommandParser extends CommandParser {

    @Override
    public Command prepareCommand(String args) {
        return new HelpCommand();
    }
}
