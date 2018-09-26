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

    /**
     * Returns the module code.
     *
     * @return module code
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Compares the module code value of both Code object.
     * <p>
     * This defines a notion of equality between two code objects.
     *
     * @param other Code object compared against this object
     * @return true if both are the same object or contains the same value
     */
    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Code
                && value.equals(((Code) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
