package seedu.address.logic.commands.ledger;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ledger.Account;
import seedu.address.model.ledger.DateLedger;
import seedu.address.model.ledger.Ledger;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.ledger.CreditCommand.MESSAGE_CREDIT_ACCOUNT_SUCCESS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_LEDGERS;

/**
 * Decreases the value of balance in the ledger
 */

public class DebitCommand extends Command {

    public static final String COMMAND_WORD = "debit";

    public static final String MESSAGE_DEBIT_ACCOUNT_SUCCESS = "New amount for date %2$s is $%1$s";

    private DateLedger dateLedger;

    private Double toSub;

    public DebitCommand (DateLedger date, Double amount) {
        requireNonNull(date);
        requireNonNull(amount);
        toSub = amount;
        dateLedger = date;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        requireNonNull(model);

        List<Ledger> lastShownList = model.getFilteredLedgerList();

        Ledger initialLedger = null;
        Ledger ledgerToEdit = new Ledger(dateLedger, new Account(toSub));
        Ledger editedLedger;

        boolean k = false;

        for (Ledger i : lastShownList) {

            k = false;

            if (i.getDateLedger().getDate().equals(dateLedger.getDate())){

                ledgerToEdit = i;
                k = true;
                break;

            }

        }

        if (!k) {
            throw new CommandException(Messages.MESSAGE_INVALID_LEDGER_DISPLAYED_DATE);
        }

        initialLedger = ledgerToEdit;

        //editedLedger = new Ledger(dateLedger, ledgerToEdit.getAccount());

        editedLedger = new Ledger(dateLedger,
                new Account(Double.parseDouble(initialLedger.getAccount().getBalance()) - toSub));

        model.updateLedger(initialLedger, editedLedger);
        model.updateFilteredLedgerList(PREDICATE_SHOW_ALL_LEDGERS);
        model.commitAddressBook();

        return new CommandResult(String.format(MESSAGE_DEBIT_ACCOUNT_SUCCESS, editedLedger.getAccount(), editedLedger.getDateLedger().getDate()));
    }
}
