package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Module's code in the transcript.
 * <p>
 * Guarantees: immutable; is valid as declared in {@link #isValidCode(String)}
 */
public class Code {

    /**
     * Describes the requirements for code value.
     */
    public static final String MESSAGE_CODE_CONSTRAINTS =
            "Code can take any values except whitespaces";

    /**
     * No whitespace allowed.
     */
    public static final String CODE_VALIDATION_REGEX = "^[^\\s]+$";

    /**
     * Immutable code value.
     */
    public final String value;

    /**
     * Constructs an {@code Code}.
     *
     * @param code A valid code.
     */
    public Code(String code) {
        requireNonNull(code);
        checkArgument(isValidCode(code), MESSAGE_CODE_CONSTRAINTS);
        value = code;
    }

    /**
     * Returns true if a given string is a valid code.
     *
     * @param code string to be tested for validity
     * @return true if given string is a valid code
     */
    public static boolean isValidCode(String code) {
        return code.matches(CODE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Code // instanceof handles nulls
                && value.equals(((Code) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
