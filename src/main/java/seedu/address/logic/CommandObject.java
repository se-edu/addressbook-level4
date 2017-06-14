package seedu.address.logic;

import seedu.address.logic.commands.Command;

/**
 * Represents a user input and the corresponding Command (if the user input is successfully parsed and executed).
 */
public class CommandObject {
    public final String userInput;
    public final Command command;

    public CommandObject(String userInput, Command command) {
        this.userInput = userInput;
        this.command = command;
    }
}
