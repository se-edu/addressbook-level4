package seedu.todoList.model.task;

import seedu.todoList.model.task.attributes.Name;


/**
 * Represents a task in the TaskList.
 * Guarantees: details are present and not null, field values are validated.
 */
public abstract class Task implements ReadOnlyTask {

    protected Name name;
    
    public Name getName() {
    	return this.name;
    }
}
