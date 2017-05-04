package seedu.address.logic;

import java.util.Stack;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.ReversibleCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Stores the history of commands executed.
 */
public class HistoryManager implements History {
    private Stack<Command> undoStack;
    private Stack<Command> redoStack;

    public HistoryManager() {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }

    /**
     * All commands in {@code redoStack} will be removed upon method call.
     */
    @Override
    public void addNewExecutedCommand(Command command) {
        if (command instanceof UndoCommand || command instanceof RedoCommand) {
            return;
        }

        if (!redoStack.empty()) {
            redoStack.clear();
        }

        undoStack.push(command);
    }

    @Override
    public void undoPreviousReversibleCommand() throws CommandException {
        Command toUndo;
        do {
            if (undoStack.empty()) {
                throw new CommandException(UndoCommand.MESSAGE_FAILURE);
            }
            toUndo = undoStack.pop();
            redoStack.push(toUndo);
        } while (!(toUndo instanceof ReversibleCommand));

        ((ReversibleCommand) toUndo).rollback();
    }

    @Override
    public void executeNextReversibleCommand() throws CommandException {
        Command toRedo;
        do {
            if (redoStack.empty()) {
                throw new CommandException(RedoCommand.MESSAGE_FAILURE);
            }
            toRedo = redoStack.pop();
            undoStack.push(toRedo);
        } while (!(toRedo instanceof ReversibleCommand));

        try {
            toRedo.execute();
        } catch (CommandException ce) {
            throw new AssertionError("The command has been successfully executed previously; " +
                    "it should not fail now");
        }
    }
}
