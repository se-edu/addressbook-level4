package seedu.address.logic.parser;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ListCommand;

/**
 * Parses input arguments in the context of the List command
 */
public class ListCommandParser extends CommandParser {

    @Override
    public Command prepareCommand(String args) {
        return new ListCommand();
    }
}
