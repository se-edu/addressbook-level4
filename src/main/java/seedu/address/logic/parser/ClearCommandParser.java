package seedu.address.logic.parser;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;

/**
 * Parses input arguments in the context of the Clear command
 */
public class ClearCommandParser extends CommandParser {

    @Override
    public Command prepareCommand(String args) {
        return new ClearCommand();
    }
}
