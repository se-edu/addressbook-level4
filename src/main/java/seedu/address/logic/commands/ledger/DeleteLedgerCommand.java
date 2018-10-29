package seedu.address.logic.commands.ledger;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.item.Item;
import seedu.address.model.ledger.Account;
import seedu.address.model.ledger.DateLedger;
import seedu.address.model.ledger.Ledger;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Deletes a ledger identified using it's displayed index from the address book.
 */
public class DeleteLedgerCommand extends Command {

    public static final String COMMAND_WORD = "deleteLedger";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the ledger identified by the ledger date used in the displayed ledger list.\n"
            + "Parameters: date (must be in DD/MM format)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_ITEM_SUCCESS = "Deleted Ledger: %1$s";

    private final DateLedger targetDate;

    public DeleteLedgerCommand(DateLedger targetDate) {
        this.targetDate = targetDate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Ledger> lastShownList = model.getFilteredLedgerList();

        Ledger ledgerToDelete = new Ledger(targetDate, new Account(0.0));

        boolean k = false;

        for (Ledger i : lastShownList) {

            k = false;

            if (i.getDateLedger().getDate().equals(targetDate.getDate())){

                ledgerToDelete = i;
                model.deleteLedger(ledgerToDelete);
                k = true;
                break;

            }

        }

        if (!k) {
            throw new CommandException(Messages.MESSAGE_INVALID_LEDGER_DISPLAYED_DATE);
        }

        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_DELETE_ITEM_SUCCESS, ledgerToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteLedgerCommand // instanceof handles nulls
                && targetDate.equals(((DeleteLedgerCommand) other).targetDate)); // state check
    }
}
