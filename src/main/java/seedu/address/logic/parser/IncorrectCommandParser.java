package seedu.address.logic.parser;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;

/**
 * Returns an IncorrectCommand object with a message stating
 * that the command entered does not match any known command.
 * This class does not parse any input
 */
public class IncorrectCommandParser extends CommandParser {

    @Override
    public Command prepareCommand(String args) {
        return new IncorrectCommand(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

}
