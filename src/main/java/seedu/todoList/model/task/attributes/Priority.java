package seedu.todoList.model.task.attributes;

import seedu.todoList.commons.exceptions.IllegalValueException;

/**
 * 
 * Represent's a task's priority in the Todo.
 *
 */
public class Priority {

	public static final String MESSAGE_PRIORITY_CONSTRAINTS = "Todo priority index should be a positive integer from index 0";
	public static final String PRIORITY_VALIDATION_REGEX = "[\\p{Digit}]+";
	
	public final int priorityIndex;
	
	/**
	 * Validate given priority index
	 * 
	 * @throws IllegalValueException if given priority index if given phone string is invalid
	 */
	public Priority(String priority) throws IllegalValueException{
		assert priority != null;
		priority = priority.trim();
		if (!isValidPriority(priority)){
			throw new IllegalValueException(MESSAGE_PRIORITY_CONSTRAINTS);
		}
		this.priorityIndex = Integer.parseInt(priority);
	}
	
	/**
	 * 
	 * Returns true if a given string is a valid priority number.
	 * 
	 */
	public static boolean isValidPriority(String test){
		return test.matches(PRIORITY_VALIDATION_REGEX);
	}
	
	@Override
	public String toString() {
		return Integer.toString(priorityIndex);
	}
}