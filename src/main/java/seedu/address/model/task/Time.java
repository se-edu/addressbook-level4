package seedu.address.model.task;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's time number in the SmartyDo.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Time {

    public static final String MESSAGE_TIME_CONSTRAINTS = "Task time should only contain numbers";
    public static final String TIME_VALIDATION_REGEX = "\\d{4}";

    public final String value;

    /**
     * Validates given time number.
     *
     * @throws IllegalValueException if given time string is invalid.
     */
    public Time(String time) throws IllegalValueException {
        assert time != null;
        time = time.trim();
        if (!time.isEmpty()&&!isValidPhone(time)) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }
        this.value = time;
    }

    /**
     * Returns true if a given string is a valid task time.
     */
    public static boolean isValidPhone(String test) {
        return test.matches(TIME_VALIDATION_REGEX);
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
