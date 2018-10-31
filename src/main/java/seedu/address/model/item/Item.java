package seedu.address.model.item;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;

/**
 * Represents an Item in the inventory.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Item {

    // Identity fields
    private final ItemName itemName;
    private final ItemQuantity itemQuantity;

    private final Logger logger = LogsCenter.getLogger(Item.class);

    /**
     * Every field must be present and not null.
     */
    public Item(ItemName itemName, ItemQuantity itemQuantity) {
        requireAllNonNull(itemName);
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
    }

    public ItemName getItemName() {
        return itemName;
    }

    public ItemQuantity getItemQuantity() {
        return itemQuantity;
    }

    /**
     * Returns true if both items have the same ItemName
     */
    public boolean isSameItem(Item otherItem) {
        if (otherItem.getItemName() == itemName) {
            logger.info("Same item");
            return true;
        }

        return otherItem.getItemName() != null
                && otherItem.getItemName().equals(getItemName());
    }

    /**
     * Returns true if both items have the same identity and data fields.
     * This defines a stronger notion of equality between two items.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Item)) {
            return false;
        }

        Item otherItem = (Item) other;
        return otherItem.getItemName().equals(getItemName());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getItemName())
                .append(" ItemQuantity: ")
                .append(getItemQuantity());
        return builder.toString();
    }
}
