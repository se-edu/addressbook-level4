package seedu.todoList.model.task.attributes;

import seedu.todoList.commons.exceptions.IllegalValueException;
/**
 * 
 * Represents a task's end time in Event and Deadline.
 *
 */

public class EndTime {

	public static final String MESSAGE_ENDTIME_CONSTRAINTS = "Time should be in format";
	public static final String ENDTIME_VALIDATION_REGEX = "";
	
	public final String value;
	
	/**
	 * Validate given date
	 * 
	 * @throws IllegalValueException if given priority index if given date string is invalid
	 */
	public EndTime(String endTime) throws IllegalValueException {
		assert endTime != null;
		endTime = endTime.trim();
		if (!isValidEndTime(endTime)) {
			throw new IllegalValueException(MESSAGE_ENDTIME_CONSTRAINTS);
		}
		this.value = endTime;
	}
	
	/**
	 * 
	 * Returns true if a given string is a valid priority number.
	 * 
	 */
	public static boolean isValidEndTime(String test){
		return test.matches(ENDTIME_VALIDATION_REGEX);
	}
}