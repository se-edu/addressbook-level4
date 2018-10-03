package seedu.address.model.schedule;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Schedule's Date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateOfBirth(String)}
 */

/**
 * Represents a Person's Date of Birth in the address book.
 * Guarantees: immutable; is valid as declared in {@link #Date(String)}
 */
public class Date {
    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Date of Birth should only be in the format of DD/MM/YYYY and it should not be blank";
    public static final String DATE_VALIDATION_REGEX = "[0-3][0-9]/[0-1][0-9]/[0-2][0-9]{3}";

    public final String date;

    /**
     * Constructs a {@code dateOfBirth}.
     *
     * @param date A valid date of birth.
     */

    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_DATE_CONSTRAINTS);
        this.date = date;
    }

    /**
     * Returns true if a given string is a valid date of birth.
     */
    public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return date;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && date.equals(((Date) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

}
