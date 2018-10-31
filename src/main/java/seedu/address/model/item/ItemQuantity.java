package seedu.address.model.item;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.commons.util.StringUtil;

/**
 *  Represents a Item's quantity in the inventory.
 *  Guarantees: immutable; is valid as declared in {@link #isValidItemQuantity(String)}
 */
public class ItemQuantity {

    public static final String MESSAGE_ITEM_QUANTITY_CONSTRAINTS =
            "Item Quantities should only be non-zero unsigned integers, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final Integer itemQuantity;

    /**
     * Constructs a {@code itemQuantity}.
     *
     * @param inputItemQuantity A valid item quantity.
     */
    public ItemQuantity(String inputItemQuantity) {
        requireNonNull(inputItemQuantity);
        checkArgument(isValidItemQuantity(inputItemQuantity), MESSAGE_ITEM_QUANTITY_CONSTRAINTS);
        itemQuantity = Integer.parseInt(inputItemQuantity);
    }

    /**
     * Returns true if a given string is a valid item quantity.
     */
    public static Boolean isValidItemQuantity(String test) {
        return StringUtil.isNonZeroUnsignedInteger(test);
    }


    @Override
    public String toString() {
        return itemQuantity.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ItemQuantity // instanceof handles nulls
                && itemQuantity.equals(((ItemQuantity) other).itemQuantity)); // state check
    }

    @Override
    public int hashCode() {
        return itemQuantity.hashCode();
    }

}
