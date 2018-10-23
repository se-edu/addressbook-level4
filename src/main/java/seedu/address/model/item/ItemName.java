package seedu.address.model.item;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 *  Represents a Item's name in the inventory.
 *  Guarantees: immutable; is valid as declared in {@link #isValidItemName(String)}
 */
public class ItemName {

    public static final String MESSAGE_ITEM_NAME_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullItemName;

    /**
     * Constructs a {@code ItemName}.
     *
     * @param itemName A valid item name.
     */
    public ItemName(String itemName) {
        requireNonNull(itemName);
        checkArgument(isValidItemName(itemName), MESSAGE_ITEM_NAME_CONSTRAINTS);
        fullItemName = itemName;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static Boolean isValidItemName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullItemName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ItemName // instanceof handles nulls
                && fullItemName.equals(((ItemName) other).fullItemName)); // state check
    }

    @Override
    public int hashCode() {
        return fullItemName.hashCode();
    }

}
