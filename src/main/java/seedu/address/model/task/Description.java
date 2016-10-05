package seedu.address.model.task;


import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's phone number in the SmartyDo.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmail(String)}
 */
public class Description {

    public static final String MESSAGE_EMAIL_CONSTRAINTS =
            "Task description doesn't have any constraints";
    public static final String EMAIL_VALIDATION_REGEX = ".+";

    public final String value;

    /**
     * Validates given email.
     *
     * @throws IllegalValueException if given email address string is invalid.
     */
    public Description(String email) throws IllegalValueException {
        assert email != null;
        email = email.trim();
        if (!email.isEmpty()&&!isValidEmail(email)) {
            throw new IllegalValueException(MESSAGE_EMAIL_CONSTRAINTS);
        }
        this.value = email;
    }

    /**
     * Returns if a given string is a valid task email.
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
                || (other instanceof Description // instanceof handles nulls
                && this.value.equals(((Description) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
