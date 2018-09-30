package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Module's year in the transcript.
 * <p>
 * Guarantees: immutable; is valid as declared in {@link #isValidYear(int)}
 */
public class Year {

    public static final String MESSAGE_YEAR_CONSTRAINTS =
            "Year must be [1-5]. Example: 1 represents Year 1";

    /**
     * No whitespace allowed.
     */
    public static final String YEAR_VALIDATION_REGEX = "[1-5]";

    /**
     * Immutable year value.
     */
    public final int value;

    /**
     * Constructs an {@code Year}.
     *
     * @param year A valid year.
     */
    public Year(int year) {
        checkArgument(isValidYear(year), MESSAGE_YEAR_CONSTRAINTS);
        value = year;
    }

    /**
     * Constructs an {@code Year}.
     *
     * @param year A valid year.
     */
    public Year(String year) {
        requireNonNull(year);
        checkArgument(isValidYear(year), MESSAGE_YEAR_CONSTRAINTS);
        value = Integer.valueOf(year);
    }

    /**
     * Returns true if a given string is a valid year.
     *
     * @param year string to be tested for validity
     * @return true if given string is a valid year
     */
    public static boolean isValidYear(int year) {
        return isValidYear(Integer.toString(year));
    }

    /**
     * Returns true if a given string is a valid year.
     *
     * @param year string to be tested for validity
     * @return true if given string is a valid year
     */
    public static boolean isValidYear(String year) {
        return year.matches(YEAR_VALIDATION_REGEX);
    }

    /**
     * Returns the year the module was taken.
     *
     * @return year
     */
    @Override
    public String toString() {
        return Integer.toString(value);
    }

    /**
     * Compares the year value of both Year object.
     * <p>
     * This defines a notion of equality between two Year objects.
     *
     * @param other Year object compared against this object
     * @return true if both are the same object or contains the same value
     */
    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Year
                && value == ((Year) other).value);
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
