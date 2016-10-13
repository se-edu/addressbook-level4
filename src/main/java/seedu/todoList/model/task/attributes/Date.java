package seedu.todoList.model.task.attributes;

import seedu.todoList.commons.exceptions.IllegalValueException;

/**
 * Represents a Event's date in the TodoList.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {
    
    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Event date should be written in this format '1-1-2016'";
    public static final String DATE_VALIDATION_REGEX = "\\D-\\D-\\D";
    
    public final String value;
    
    /**
     * Validates given date.
     *
     * @throws IllegalValueException if given date is invalid.
     */
    public Date(String date) throws IllegalValueException {
        assert date != null;
        date = date.trim();
        if (!isValidDate(date)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        this.value = date;
    }
    
    /**
     * Returns if a given string is a valid event date.
     */
    public static boolean isValidDate(String date) {
        return date.matches(DATE_VALIDATION_REGEX);
    }
    
    @Override
    public String toString() {
        return value;
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && this.value.equals(((Date) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
