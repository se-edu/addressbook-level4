package seedu.address.model.member;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/** Represents a member's postalcode in the Clubhub
*/

public class Postalcode {


    public static final String MESSAGE_POSTALCODE_CONSTRAINTS =
            "Postal code should only contain numbers, and it should be at least 6 digits long";
    public static final String POSTALCODE_VALIDATION_REGEX = "\\d{6,}";
    public final String value;

    /**
     * Constructs a {@code Postalcode}.
     *
     * @param postalcode A valid postal code .
     */
    public Postalcode(String postalcode) {
        requireNonNull(postalcode);
        checkArgument(isValidPostalcode(postalcode), MESSAGE_POSTALCODE_CONSTRAINTS);
        value = postalcode;
    }

    /**
     * Returns true if a given string is a valid postalcode.
     */
    public static boolean isValidPostalcode(String test) {
        return test.matches(POSTALCODE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Postalcode // instanceof handles nulls
                && value.equals(((Postalcode) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }



}

