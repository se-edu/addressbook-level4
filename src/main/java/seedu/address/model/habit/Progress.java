package seedu.address.model.habit;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Habit's progress in the habit tracker list.
 * Guarantees: immutable; is valid as declared in {@link #isValidProgress(String)}
 */

public class Progress {
    public static final String MESSAGE_CONSTRAINTS =
            "Progress should contain numbers from 0 to 5.";
    public static final String VALIDATION_REGEX = "\\d";
    public final String value;

    /**
     * Constructs a {@code Progress}.
     *
     * @param progress A valid progress.
     */
    public Progress(String progress) {
        requireNonNull(progress);
        checkArgument(isValidProgress(progress), MESSAGE_CONSTRAINTS);
        value = progress;
    }

    /**
     * Returns true if a given string is a valid price.
     */
    public static boolean isValidProgress(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Progress // instanceof handles nulls
                && value.equals(((Progress) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
