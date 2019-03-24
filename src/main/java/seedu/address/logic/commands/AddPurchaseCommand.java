package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PURCHASENAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.purchase.Purchase;

/**
 * Adds a purchase to the expenditure list.
 */
public class AddPurchaseCommand extends Command {
    public static final String COMMAND_WORD = "addpurchase";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a purchase to the expenditure list. "
            + "Parameters: "
            + PREFIX_PURCHASENAME + "NAME "
            + PREFIX_PRICE + "PRICE "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PURCHASENAME + "Ice cream "
            + PREFIX_PRICE + "1.50 "
            + PREFIX_TAG + "from 7-11";

    public static final String MESSAGE_SUCCESS = "New purchase added: %1$s";

    private final Purchase toAddPurchase;

    /**
     * Creates an AddPurchaseCommand to add the specified {@code Purchase}
     */
    public AddPurchaseCommand(Purchase purchase) {
        requireNonNull(purchase);
        toAddPurchase = purchase;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);


        model.addPurchase(toAddPurchase);
        model.commitExpenditureList();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAddPurchase));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddPurchaseCommand // instanceof handles nulls
                && toAddPurchase.equals(((AddPurchaseCommand) other).toAddPurchase));
    }
}
