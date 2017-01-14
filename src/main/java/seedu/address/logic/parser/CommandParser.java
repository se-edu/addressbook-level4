package seedu.address.logic.parser;

import seedu.address.logic.commands.Command;

/**
 * Parses user input arguments in the context of the respective command classes
 */
public abstract class CommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of a {@code Command}
     * and returns the respective {@code Command} class for execution.
     */
    public abstract Command prepareCommand(String args);
}
