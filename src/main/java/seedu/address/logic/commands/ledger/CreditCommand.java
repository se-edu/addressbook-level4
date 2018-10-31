package seedu.address.logic.commands.ledger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ledger.Account;
import seedu.address.model.ledger.DateLedger;
import seedu.address.model.ledger.Ledger;
import seedu.address.ui.CommandBox;

import java.util.List;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_LEDGERS;

/**
 * Increases the value of balance in the ledger
 */

public class CreditCommand extends Command {


    private final Logger logger = LogsCenter.getLogger(CreditCommand.class);

    public static final String COMMAND_WORD = "credit";

    public static final String MESSAGE_CREDIT_ACCOUNT_SUCCESS = "New amount for date %2$s is $%1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "Credits the amount in a date ledger. Parameters: " +
            "/d[Date DD/MM] /b[$Balance]";

    private DateLedger dateLedger;

    private Double toAdd;

    public CreditCommand (DateLedger date, Double amount) {
        requireNonNull(date);
        requireNonNull(amount);
        toAdd = amount;
        dateLedger = date;
    }



    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        requireNonNull(model);

        List<Ledger> lastShownList = model.getFilteredLedgerList();

        Ledger initialLedger = null;
        Ledger ledgerToEdit = new Ledger(dateLedger, new Account(toAdd));
        Ledger editedLedger;

        boolean k = false;

        for (Ledger i : lastShownList) {

            k = false;

            if (i.getDateLedger().getDate().equals(dateLedger.getDate())) {

                ledgerToEdit = initialLedger = i;
                k = true;
                break;

            }

        }

        if (!k) {
            throw new CommandException(Messages.MESSAGE_INVALID_LEDGER_DISPLAYED_DATE);
        }


        //ledgerToEdit.getAccount().credit(toAdd);

        editedLedger = new Ledger(dateLedger, new Account(toAdd + Double.parseDouble(ledgerToEdit.getAccount()
                .getBalance())));

        model.updateLedger(initialLedger, editedLedger);
        model.updateFilteredLedgerList(PREDICATE_SHOW_ALL_LEDGERS);
        model.commitAddressBook();

        return new CommandResult(String.format(MESSAGE_CREDIT_ACCOUNT_SUCCESS, editedLedger.getAccount(), editedLedger.getDateLedger()));
    }
}
