package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

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
            + "parameters: ";

    private static final String MESSAGE_SUCCESS = "New item added: %1$s";

    private final Item addItem;

    public AddItemCommand(Item item) {
        requireNonNull(item);
        addItem = item;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.addItem(addItem);

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
