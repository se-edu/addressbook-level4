package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Module's credits in the transcript.
 * <p>
 * Guarantees: immutable; is valid as declared in {@link #isValidCredit(int)}
 */
public class Credit {

    /**
     * Describes the requirements for credit value.
     */
    public static final String MESSAGE_CREDIT_CONSTRAINTS = "Credits must be a integer";

    /**
     * Immutable credit value.
     */
    public final int value;

    /**
     * Constructs an {@code Credit}.
     *
     * @param credits A valid credit.
     */
    public Credit(int credits) {
        requireNonNull(credits);
        checkArgument(isValidCredit(credits), MESSAGE_CREDIT_CONSTRAINTS);
        value = credits;
    }

    /**
     * Returns true if a given string is a valid credit.
     * <p>
     * Credit must be between 1 and 20
     *
     * @param credits string to be tested for validity
     * @return true if given string is a valid credit
     */
    public static boolean isValidCredit(int credits) {
        if (credits < 1) {
            return false;
        } else if (credits > 20) {
            return false;
        }

        return true;
    }

    /**
     * Returns the module credits value.
     *
     * @return module credits
     */
    @Override
    public String toString() {
        return Integer.toString(value);
    }

    /**
     * Compares the module credit value of both Credit object.
     * <p>
     * This defines a notion of equality between two credit objects.
     *
     * @param other Credit object compared against this object
     * @return true if both are the same object or contains the same value
     */
    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Credit
                && value == ((Credit) other).value);
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
