package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmail(String)}
 */
public class Email {

    public static final String MESSAGE_EMAIL_CONSTRAINTS = "Person emails should follow the following constraints:\n"
            + "The local part should only contain alphanumeric characters and the following special characters "
            + "excluding the parentheses, (!#$%&'*+/=?`{|}~^.-) . This is followed by a '@' and then a domain name.\n "
            + "The domain name must be at least 2 characters long and must start and end with alphanumeric characters. "
            + "The characters in between can be alphanumeric characters, a period or a hyphen.";
    public static final String EMAIL_VALIDATION_REGEX = "^[\\w!#$%&'*+/=?`{|}~^.-]+@[^\\W_][a-zA-Z0-9.-]*[^\\W_]$";

    public final String value;

    /**
     * Constructs an {@code Email}.
     *
     * @param email A valid email address.
     */
    public Email(String email) {
        requireNonNull(email);
        checkArgument(isValidEmail(email), MESSAGE_EMAIL_CONSTRAINTS);
        this.value = email;
    }

    /**
     * Returns if a given string is a valid person email.
     */
    public static boolean isValidEmail(String test) {
        return test.matches(EMAIL_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Email // instanceof handles nulls
                && this.value.equals(((Email) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
