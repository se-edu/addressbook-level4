package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.ExpenditureList;
import seedu.address.model.Model;

/**
 * Clears the expenditure list.
 */
public class ClearExpListCommand extends Command {
    public static final String COMMAND_WORD = "clearexplist";
    public static final String MESSAGE_SUCCESS = "Expenditure List has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.setExpenditureList(new ExpenditureList());
        model.commitExpenditureList();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
