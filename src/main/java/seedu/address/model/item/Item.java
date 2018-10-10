package seedu.address.model.item;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents an Item in the inventory.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Item {

    // Identity fields
    private final String itemName;

    /**
     * Every field must be present and not null.
     */
    public Item(String itemName) {
        requireAllNonNull(itemName);
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameItem(seedu.address.model.item.Item otherItem) {
        if (otherItem == this) {
            return true;
        }

        return otherItem != null
                && otherItem.getItemName().equals(getItemName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof seedu.address.model.item.Item)) {
            return false;
        }

        seedu.address.model.item.Item otherItem = (seedu.address.model.item.Item) other;
        return otherItem.getItemName().equals(getItemName());
    }

}
