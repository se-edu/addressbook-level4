package seedu.todoList.model.task.attributes;

import seedu.todoList.commons.exceptions.IllegalValueException;

/**
 * 
 * Represents a task's start time in the Event and Deadline.
 *
 */
public class StartTime {
	
	public static final String MESSAGE_STARTTIME_CONSTRAINTS = "Time should be in format";
	public static final String STARTTIME_VALIDATION_REGEX = "";
	
	public final String value;
	
	/**
	 * Validate given date
	 * 
	 * @throws IllegalValueException if given priority index if given date string is invalid
	 */
	public StartTime(String startTime) throws IllegalValueException {
		assert startTime != null;
		startTime = startTime.trim();
		if (!isValidStartTime(startTime)) {
			throw new IllegalValueException(MESSAGE_STARTTIME_CONSTRAINTS);
		}
		this.value = startTime;
	}
	
	/**
	 * 
	 * Returns true if a given string is a valid priority number.
	 * 
	 */
	public static boolean isValidStartTime(String test){
		return test.matches(STARTTIME_VALIDATION_REGEX);
	}
}