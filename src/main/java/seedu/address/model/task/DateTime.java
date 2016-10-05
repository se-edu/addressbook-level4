/**
package seedu.address.model.task;
import java.time.LocalDateTime;

import seedu.address.commons.exceptions.IllegalValueException;
**/
/**
 * Represents a Date and Time in the task list
 * Guarantees: immutable; is valid as declared in {@link #isValidDateTime(String)}
 */
/**
public class DateTime {

    public static final String MESSAGE_DATETIME_CONSTRAINTS = "Person date numbers should only contain numbers";
    //TODO: Set Regex for DateTime
    public static final String DATETIME_VALIDATION_REGEX = "\\d+";

    public final LocalDateTime value;

    /**
     * Validates given Date and Time entered by the user.
     *
     * @throws IllegalValueException if given phone string is invalid.
     */
    /**
    public DateTime(String dateTime) throws IllegalValueException {
        assert dateTime != null;
        dateTime = dateTime.trim();
        if (!isValidDateTime(dateTime)) {
            throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINTS);
        }
        //TODO: Parse DateTime?
        //this.value = dateTime;
    }

    /**
     * Returns true if a given string is a valid datetime in required format
     */
    /**
    public static boolean isValidDateTime(String test) {
        return test.matches(DATETIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateTime// instanceof handles nulls
                && this.value.equals(((DateTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
**/
