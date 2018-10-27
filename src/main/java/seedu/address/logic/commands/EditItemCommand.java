package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ITEM_QUANTITY;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ITEMS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.item.Item;
import seedu.address.model.item.ItemName;
import seedu.address.model.item.ItemQuantity;

/**
 * Edits the details of an existing member in the address book.
 */
public class EditItemCommand extends Command {

    public static final String COMMAND_WORD = "editItem";
    public static final String COMMAND_ALIAS = "eI";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the item identified "
            + "by the index number used in the displayed item list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_ITEM_NAME + "ITEM_NAME] "
            + "[" + PREFIX_ITEM_QUANTITY + "ITEM_QUANTITY]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ITEM_NAME + "Ball "
            + PREFIX_ITEM_QUANTITY + "4";

    public static final String MESSAGE_EDIT_ITEM_SUCCESS = "Edited Item: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ITEM = "This item already exists in the item list.";

    private final Index index;
    private final EditItemDescriptor editItemDescriptor;

    /**
     * @param index of the item in the filtered item list to edit
     * @param editItemDescriptor details to edit the item with
     */
    public EditItemCommand(Index index, EditItemDescriptor editItemDescriptor) {
        requireNonNull(index);
        requireNonNull(editItemDescriptor);

        this.index = index;
        this.editItemDescriptor = new EditItemDescriptor(editItemDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Item> lastShownList = model.getFilteredItemList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Item itemToEdit = lastShownList.get(index.getZeroBased());
        Item editedItem = createEditedItem(itemToEdit, editItemDescriptor);

        if (!itemToEdit.isSameItem(editedItem) && model.hasItem(editedItem)) {
            throw new CommandException(MESSAGE_DUPLICATE_ITEM);
        }

        model.updateItem(itemToEdit, editedItem);
        model.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_EDIT_ITEM_SUCCESS, editedItem));
    }

    /**
     * Creates and returns a {@code Item} with the details of {@code itemToEdit}
     * edited with {@code editItemDescriptor}.
     */
    private static Item createEditedItem(Item itemToEdit, EditItemDescriptor editItemDescriptor) {
        assert itemToEdit != null;

        ItemName updatedItemName = editItemDescriptor.getItemName().orElse(itemToEdit.getItemName());
        ItemQuantity updatedItemQuantity = editItemDescriptor.getItemQuantity().orElse(itemToEdit.getItemQuantity());

        return new Item(updatedItemName, updatedItemQuantity);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditItemCommand)) {
            return false;
        }

        // state check
        EditItemCommand e = (EditItemCommand) other;
        return index.equals(e.index)
                && editItemDescriptor.equals(e.editItemDescriptor);
    }

    /**
     * Stores the details to edit the member with. Each non-empty field value will replace the
     * corresponding field value of the member.
     */
    public static class EditItemDescriptor {
        private ItemName itemName;
        private ItemQuantity itemQuantity;

        public EditItemDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditItemDescriptor(EditItemDescriptor toCopy) {
            setItemName(toCopy.itemName);
            setItemQuantity(toCopy.itemQuantity);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(itemName, itemQuantity);
        }

        public void setItemName(ItemName itemName) {
            this.itemName = itemName;
        }

        public Optional<ItemName> getItemName() {
            return Optional.ofNullable(itemName);
        }

        public void setItemQuantity(ItemQuantity itemQuantity) {
            this.itemQuantity = itemQuantity;
        }

        public Optional<ItemQuantity> getItemQuantity() {
            return Optional.ofNullable(itemQuantity);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditItemDescriptor)) {
                return false;
            }

            // state check
            EditItemDescriptor e = (EditItemDescriptor) other;

            return getItemName().equals(e.getItemName())
                    && getItemQuantity().equals(e.getItemQuantity());
        }
    }
}
