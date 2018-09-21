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
    public static final String MESSAGE_CODE_CONSTRAINTS = "Credits must be a integer";

    /**
     * No whitespace allowed.
     */
    public static final String CREDIT_VALIDATION_REGEX = "([1-9][0-9])|(0?[1-9])";

    /**
     * Immutable credit value.
     */
    public final int value;

    /**
     * Constructs an {@code Code}.
     *
     * @param credits A valid credit.
     */
    public Credit(int credits) {
        requireNonNull(credits);
        checkArgument(isValidCredit(credits), MESSAGE_CODE_CONSTRAINTS);
        value = Integer.valueOf(credits);
    }

    /**
     * Returns true if a given string is a valid credit.
     *
     * @param credits string to be tested for validity
     * @return true if given string is a valid credit
     */
    public static boolean isValidCredit(int credits) {
        return Integer.toString(credits).matches(CREDIT_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Credit // instanceof handles nulls
                && value == ((Credit) other).value); // state check
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}