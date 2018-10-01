package seedu.address.model.person.password;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the password tagged to a NRIC that is used to login.
 */
public class Password {
    // Password must be alphanumeric
    public static final String PASSWORD_VALIDATION_REGEX = "^[a-zA-Z0-9]{5,}$";

    public static final String MESSAGE_PASSWORD_CONSTRAINTS =
            "Password must be alphanumeric and must contain at least five alphanumeric characters.";

    public final String password;

    public Password(String password) {
        requireNonNull(password);

        //Checks for valid password regex. if false, throws IllegalArgumentException
        checkArgument(isValidPassword(password), MESSAGE_PASSWORD_CONSTRAINTS);
        this.password = password;
    }

    /**
     * Returns true if a given string is a valid password.
     */
    public static boolean isValidPassword(String test) {
        return test.matches(PASSWORD_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return password;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Password // instanceof handles nulls
                && password.equals(((Password) other).password)); // state check
    }

    @Override
    public int hashCode() {
        return password.hashCode();
    }
}
