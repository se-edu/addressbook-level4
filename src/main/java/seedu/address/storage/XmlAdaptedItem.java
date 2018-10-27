package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.item.Item;
import seedu.address.model.item.ItemName;
import seedu.address.model.item.ItemQuantity;

/**
 * JAXB-friendly version of the Item.
 */
public class XmlAdaptedItem {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Item's %s field is missing!";

    @XmlElement(required = true)
    private String itemName;
    @XmlElement(required = true)
    private String itemQuantity;



    /**
     * Constructs an XmlAdaptedItem.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedItem() {}

    /**
     * Constructs an {@code XmlAdaptedItem} with the given item details.
     */
    public XmlAdaptedItem(String itemName, String itemQuantity) {
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
    }

    /**
     * Converts a given Item into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedItem
     */
    public XmlAdaptedItem(Item source) {
        itemName = source.getItemName().fullItemName;
        itemQuantity = source.getItemQuantity().itemQuantity.toString();
    }

    /**
     * Converts this jaxb-friendly adapted item object into the model's Item object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted item
     */
    public Item toModelType() throws IllegalValueException {
        if (itemName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ItemName.class.getSimpleName()));
        }
        if (!ItemName.isValidItemName(itemName)) {
            throw new IllegalValueException(ItemName.MESSAGE_ITEM_NAME_CONSTRAINTS);
        }
        final ItemName modelItemName = new ItemName(itemName);

        if (itemQuantity == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ItemQuantity.class.getSimpleName()));
        }
        if (!ItemQuantity.isValidItemQuantity(itemQuantity)) {
            throw new IllegalValueException(ItemQuantity.MESSAGE_ITEM_QUANTITY_CONSTRAINTS);
        }
        final ItemQuantity modelItemQuantity = new ItemQuantity(itemQuantity);

        return new Item(modelItemName, modelItemQuantity);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedItem)) {
            return false;
        }

        XmlAdaptedItem otherItem = (XmlAdaptedItem) other;
        return Objects.equals(itemName, otherItem.itemName)
                && Objects.equals(itemQuantity, otherItem.itemQuantity);
    }
}
