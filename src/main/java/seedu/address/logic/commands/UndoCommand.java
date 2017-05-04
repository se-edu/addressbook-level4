package seedu.address.logic.commands;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.CommandObject;
import seedu.address.logic.ListElementPointer;
import seedu.address.logic.commands.exceptions.CommandException;
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
        checkNotNull(history);

        int toSkip = 0;

        // append a stub value to allow easier iterating
        List<CommandObject> temp = history.getHistory();
        temp.add(new CommandObject("", null));
        ListElementPointer pointer = new ListElementPointer(temp);

        while (pointer.hasPrevious()) {
            Command command = pointer.previous().command;
            if (command instanceof ReversibleCommand) {
                if (toSkip == 0) {
                    ReversibleCommand reversibleCommand = (ReversibleCommand) command;
                    reversibleCommand.rollback();
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
