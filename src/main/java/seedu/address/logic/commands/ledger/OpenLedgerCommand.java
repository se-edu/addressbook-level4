package seedu.address.logic.commands.ledger;


import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowLedgerRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Opens the Ledger window
 */

public class OpenLedgerCommand extends Command {

    public static final String COMMAND_WORD = "openLedger";

    public static final String SHOWING_OPEN_LEDGER_MESSAGE = "Opened ledger window.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        EventsCenter.getInstance().post(new ShowLedgerRequestEvent());
        return new CommandResult(SHOWING_OPEN_LEDGER_MESSAGE);
    }
}
