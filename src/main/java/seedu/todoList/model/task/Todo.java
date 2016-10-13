package seedu.todoList.model.task;

import seedu.todoList.commons.util.CollectionUtil;
import seedu.todoList.model.task.attributes.Name;
import seedu.todoList.model.task.attributes.Priority;

/**
 * Represents a task in the TodoList.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Todo extends Task implements ReadOnlyTask {

<<<<<<< HEAD
    private Priority priority;
=======
    public final String todo;
>>>>>>> 85acd64d31eda9c80ef00ec71a27526ce74a4a34

    /**
     * Every field must be present and not null.
     */
<<<<<<< HEAD
    public Todo(Name name, Priority priority) {
        assert !CollectionUtil.isAnyNull(name, priority);
        super.name = name;
        this.priority = priority;
=======
    public Todo(String Todo) throws IllegalValueException {
        assert Todo != null;
        if (!isValidTodo(Todo)) {
            throw new IllegalValueException(MESSAGE_Todo_CONSTRAINTS);
        }
        this.todo = Todo;
>>>>>>> 85acd64d31eda9c80ef00ec71a27526ce74a4a34
    }

    /**
     * Copy constructor.
     */
    public Todo(Todo source) {
        this(source.getName(), source.getPriority());
    }

<<<<<<< HEAD
    public Priority getPriority() {
        return priority;
=======
    @Override
    public String toString() {
        return todo;
>>>>>>> 85acd64d31eda9c80ef00ec71a27526ce74a4a34
    }
    
    public Todo(ReadOnlyTask source) {
    	this((Todo) source);
    };

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Todo // instanceof handles nulls
<<<<<<< HEAD
                && super.name.equals(((Todo) other).getName()))
                && this.priority.equals(((Todo) other).getPriority());
    }

    @Override
    public String toString() {
    	final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Priority: ")
                .append(getPriority());
        return builder.toString();
=======
                && this.todo.equals(((Todo) other).todo)); // state check
    }

    @Override
    public int hashCode() {
        return todo.hashCode();
>>>>>>> 85acd64d31eda9c80ef00ec71a27526ce74a4a34
    }

	public Todo getTodo() {
		// TODO Auto-generated method stub
		return null;
	}

}
