package seedu.address.logic.commands;

import static com.google.common.base.Preconditions.checkNotNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.ListElementPointer;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Redo the previously undone command.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redo success!";
    public static final String MESSAGE_FAILURE = "There's no more commands left to be redone!";

    @Override
    public CommandResult execute() throws CommandException {
        checkNotNull(model);
        checkNotNull(history);

        int toSkip = -1;

        ListElementPointer pointer = new ListElementPointer(history.getHistory());

        if (!pointer.hasCurrent()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        String commandType = pointer.current().command.getClass().getSimpleName();
        switch (commandType) {
        case "UndoCommand":
            toSkip++;
            break;
        case "RedoCommand":
            toSkip--;
            break;
        default:
            throw new CommandException(MESSAGE_FAILURE);
        }

        while (pointer.hasPrevious()) {
            Command command = pointer.previous().command;
            if (command instanceof ReversibleCommand) {
                if (toSkip == 0) {
                    command.execute();
                    model.updateFilteredListToShowAll();
                    return new CommandResult(MESSAGE_SUCCESS);
                } else {
                    toSkip--;
                }
            } else if (command instanceof UndoCommand) {
                toSkip++;
            } else if (command instanceof RedoCommand) {
                toSkip--;
            }
        }

        throw new CommandException(MESSAGE_FAILURE);
    }

    @Override
    public void setData(Model model, CommandHistory history) {
        this.model = model;
        this.history = history;
    }
}
