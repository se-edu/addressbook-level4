package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Module's year in the transcript.
 * <p>
 * Guarantees: immutable; is valid as declared in {@link #isValidYear(int)}
 */
public class Year {

    public static final String MESSAGE_CODE_CONSTRAINTS =
            "Year must be [1-2][0-9][1-2][0-9]. Example: 1819 represents YA 2018/2019";

    /**
     * No whitespace allowed.
     */
    public static final String YEAR_VALIDATION_REGEX = "[1-2][0-9][1-2][0-9]";

    /**
     * Immutable year value.
     */
    public final int value;

    /**
     * Constructs an {@code Code}.
     *
     * @param year A valid year.
     */
    public Year(int year) {
        requireNonNull(year);
        checkArgument(isValidYear(year), MESSAGE_CODE_CONSTRAINTS);
        value = Integer.valueOf(year);
    }

    /**
     * Returns true if a given string is a valid year.
     *
     * @param year string to be tested for validity
     * @return true if given string is a valid year
     */
    public static boolean isValidYear(int year) {
        return Integer.toString(year).matches(YEAR_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Year // instanceof handles nulls
                && value == ((Year) other).value); // state check
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}