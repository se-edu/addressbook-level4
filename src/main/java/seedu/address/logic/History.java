package seedu.address.logic;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Stores the history of commands executed.
 */
public interface History {
    /**
     * Appends {@code command} to the list of executed commands.
     */
    public void addNewExecutedCommand(Command command);

    /**
     * Executes the previous {@code ReversibleCommand} executed by the user.
     *
     */
    public void undoPreviousReversibleCommand() throws CommandException;

    /**
     * Removes the next {@code ReversibleCommand} executed and returns it. This
     * {@code ReversibleCommand} returned should be returned when
     * {@code getPreviousReversibleCommand} is called. Returns Returns
     * {@code Optional.empty()} if there are no more executed
     * {@code ReversibleCommand}s.
     */
    public void executeNextReversibleCommand() throws CommandException;
}
