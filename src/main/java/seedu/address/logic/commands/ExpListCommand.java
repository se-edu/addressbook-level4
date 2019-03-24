package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PURCHASES;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all purchases in the expenditure list to the user.
 */

public class ExpListCommand extends Command {
    public static final String COMMAND_WORD = "explist";

    public static final String MESSAGE_SUCCESS = "Listed all purchases";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredPurchaseList(PREDICATE_SHOW_ALL_PURCHASES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
