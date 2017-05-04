package seedu.address.logic.commands;

import static com.google.common.base.Preconditions.checkNotNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.ReversibleCommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.OutOfReversibleCommandException;
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
        checkNotNull(reversibleCommandHistory);

        try {
            reversibleCommandHistory.next().execute();
            model.updateFilteredListToShowAll();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (OutOfReversibleCommandException oorce) {
            throw new CommandException(MESSAGE_FAILURE);
        } catch (CommandException ce) {
            throw new AssertionError("The command has been successfully executed previously; "
                    + "it should not fail now");
        }
    }

    @Override
    public void setData(Model model, CommandHistory commandHistory, ReversibleCommandHistory reversibleCommandHistory) {
        this.model = model;
        this.reversibleCommandHistory = reversibleCommandHistory;
    }
}
