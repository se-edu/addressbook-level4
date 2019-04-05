package seedu.address.model.purchase;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Purchase's price in the expenditure list.
 * Guarantees: immutable; is valid as declared in {@link #isValidPrice(String)}
 */

public class Price {
    public static final String MESSAGE_CONSTRAINTS =
            "Price should contain numbers for dollars, followed by one decimal point " +
                    "then followed by two numbers for cents.";
    public static final String VALIDATION_REGEX = "\\d+\\.\\d{2}";
    public final String value;

    /**
     * Constructs a {@code Price}.
     *
     * @param price A valid price.
     */
    public Price(String price) {
        requireNonNull(price);
        checkArgument(isValidPrice(price), MESSAGE_CONSTRAINTS);
        value = price;
    }

    /**
     * Returns true if a given string is a valid price.
     */
    public static boolean isValidPrice(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Price // instanceof handles nulls
                && value.equals(((Price) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
