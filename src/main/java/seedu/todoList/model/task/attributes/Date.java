package seedu.todoList.model.task.attributes;

import seedu.todoList.commons.exceptions.IllegalValueException;

/**
 * 
 * Represents a task's Event and Deadline.
 *
 */
public class Date {

	public static final String MESSAGE_DATE_CONSTRAINTS = "Date should be in format";
	public static final String DATE_VALIDATION_REGEX = "";
	
	public final String value;
	
	/**
	 * Validate given date
	 * 
	 * @throws IllegalValueException if given priority index if given date string is invalid
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
	 * 
	 * Returns true if a given string is a valid priority number.
	 * 
	 */
	public static boolean isValidDate(String test){
		return test.matches(DATE_VALIDATION_REGEX);
	}
}