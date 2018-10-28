package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM_QUANTITY;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.item.Item;

/**
 * Adds an item to the inventory.
 */
public class AddItemCommand extends Command {

    public static final String COMMAND_WORD = "addItem";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an item into the inventory."
            + "parameters: "
            + PREFIX_ITEM_NAME + "ITEM NAME "
            + PREFIX_ITEM_QUANTITY + "ITEM QUANTITY ";

    public static final String MESSAGE_SUCCESS = "New item added: %1$s";
    public static final String MESSAGE_DUPLICATE_ITEM = "This item already exists in the item list";

    private final Logger logger = LogsCenter.getLogger(AddItemCommand.class);

    private final Item addItem;

    public AddItemCommand(Item item) {
        requireNonNull(item);
        addItem = item;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasItem(addItem)) {
            throw new CommandException(MESSAGE_DUPLICATE_ITEM);
        }

        logger.info("Adding item...");
        model.addItem(addItem);
        logger.info("Added Item");
        model.commitAddressBook();
        logger.info("Committing Club Book");

        return new CommandResult(String.format(MESSAGE_SUCCESS, addItem));
    }

    /**
     * Returns true if the added item is the same.
     */
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddItemCommand // instanceof handles nulls
                && addItem.equals(((AddItemCommand) other).addItem));
    }
}
