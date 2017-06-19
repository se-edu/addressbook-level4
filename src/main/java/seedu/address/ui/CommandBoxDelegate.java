package seedu.address.ui;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Executes the user input entered into the {@code CommandBox} on its behalf.
 */
public interface CommandBoxDelegate {
    /**
     * Executes {@code userInput} and returns the result.
     */
    CommandResult execute(String userInput) throws CommandException, ParseException;
}
