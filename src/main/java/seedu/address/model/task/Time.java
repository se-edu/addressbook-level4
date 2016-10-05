package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's phone number in the SmartyDo.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Time {

    public static final String MESSAGE_PHONE_CONSTRAINTS = "Task phone numbers should only contain numbers";
    public static final String PHONE_VALIDATION_REGEX = "\\d{4}";

    public final String value;

    /**
     * Validates given phone number.
     *
     * @throws IllegalValueException if given phone string is invalid.
     */
    public Time(String phone) throws IllegalValueException {
        assert phone != null;
        phone = phone.trim();
        if (!phone.isEmpty()&&!isValidPhone(phone)) {
            throw new IllegalValueException(MESSAGE_PHONE_CONSTRAINTS);
        }
        this.value = phone;
    }

    /**
     * Returns true if a given string is a valid task phone number.
     */
    public static boolean isValidPhone(String test) {
        return test.matches(PHONE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time // instanceof handles nulls
                && this.value.equals(((Time) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
