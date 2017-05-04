package seedu.address.logic.commands;

import static com.google.common.base.Preconditions.checkNotNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.OutOfReversibleCommandException;
import seedu.address.model.Model;

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
        checkNotNull(undoRedoStack);

        try {
            undoRedoStack.previous().rollback();
            model.updateFilteredListToShowAll();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (OutOfReversibleCommandException oorce) {
            throw new CommandException(MESSAGE_FAILURE);
        }
    }

    @Override
    public void setData(Model model, CommandHistory commandHistory, UndoRedoStack undoRedoStack) {
        this.model = model;
        this.undoRedoStack = undoRedoStack;
    }
}
