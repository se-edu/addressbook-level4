package seedu.address.logic.commands;

import static com.google.common.base.Preconditions.checkNotNull;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Undo the previous command.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "There's no more commands left to be undone!";

    @Override
    public CommandResult execute() throws CommandException {
        checkNotNull(model);
        checkNotNull(history);

        history.undoPreviousReversibleCommand();

        model.updateFilteredListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
