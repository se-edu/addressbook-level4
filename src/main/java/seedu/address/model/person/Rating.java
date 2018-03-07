package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's rating in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRating(String)}
 */
public class Rating {
    public static final String MESSAGE_RATING_CONSTRAINTS = "Rating must be an integer";
    public static final String RATING_VALIDATION_REGEX = "[0-9]+";
    private Integer ratingValue;

    /**
     * Constructs a {@code Rating} for a new person who hasn't been assigned a rating.
     */
    public Rating() {
        ratingValue = -1;
    }

    /**
     * Constructs a {@code Rating}.
     *
     * @param rating A valid rating.
     */
    public Rating(String rating) {
        checkArgument(isValidRating(rating), MESSAGE_RATING_CONSTRAINTS);
        ratingValue = Integer.parseInt(rating);
    }

    /**
     * Returns true if a given string is a valid person rating.
     */
    public boolean isValidRating(String test) {
        return test.matches(RATING_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return ratingValue.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Rating) // instanceof handles nulls
                && this.ratingValue == ((Rating) other).ratingValue; // state check
    }

    @Override
    public int hashCode() {
        return ratingValue.hashCode();
    }
}
