package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's rating in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRating(String)}
 */
public class Rating {
    public static final String MESSAGE_RATING_CONSTRAINTS = "Rating must be an integer";
    public static final String RATING_VALIDATION_REGEX = "-?\\d*";
    private static final String INVALID_RATING_DISPLAY = "-";
    private static final String RATING_DISPLAY = "*";

    public final Integer value;

    /**
     * Constructs a {@code Rating} for a new person who hasn't been assigned a rating.
     */
    public Rating() {
        value = -1;
    }

    /**
     * Constructs a {@code Rating}.
     *
     * @param rating A valid rating.
     */
    public Rating(String rating) {
        checkArgument(isValidRating(rating), MESSAGE_RATING_CONSTRAINTS);
        value = Integer.parseInt(rating);
    }

    /**
     * Returns true if a given string is a valid person rating.
     */
    public static boolean isValidRating(String test) {
        return test.matches(RATING_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Rating) // instanceof handles nulls
                && this.value == ((Rating) other).value; // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public String getRatingDisplay() {
        if(value == -1) {
            return INVALID_RATING_DISPLAY;
        } else {
            return convertRatingToStars(value);
        }
    }

    private String convertRatingToStars(int rating) {
        StringBuilder sb = new StringBuilder();
        while (rating-- > 0) {
            sb.append(RATING_DISPLAY);
        }
        return sb.toString();
    }
}
