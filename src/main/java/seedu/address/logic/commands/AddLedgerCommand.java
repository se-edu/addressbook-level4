package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class AddLedgerCommand extends Command {

    public static final String COMMAND_WORD = "addledger";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a finance entry into the ledger. "
            + "parameters: ";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        return null;
    }
}
