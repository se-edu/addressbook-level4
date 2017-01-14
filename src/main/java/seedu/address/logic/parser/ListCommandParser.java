package seedu.address.logic.parser;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ListCommand;

/**
 * Creates a new ListCommand object
 */
public class ListCommandParser extends CommandParser {

    @Override
    public Command prepareCommand(String args) {
        return new ListCommand();
    }
}
