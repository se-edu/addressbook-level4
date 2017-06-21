package seedu.address.ui;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Is able to execute the user input and returns the result.
 */
public interface ExecuteDelegate {
    /**
     * Executes {@code userInput} and returns the result.
     */
    CommandResult execute(String userInput) throws CommandException, ParseException;
}
