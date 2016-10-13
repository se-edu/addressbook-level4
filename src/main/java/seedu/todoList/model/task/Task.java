package seedu.todoList.model.task;

import seedu.todoList.model.task.attributes.Name;


/**
 * Represents a task in the TaskList.
 * Guarantees: details are present and not null, field values are validated.
 */
<<<<<<< HEAD
public abstract class Task implements ReadOnlyTask {

    protected Name name;
    
    public Name getName() {
    	return this.name;
    }
=======
public class Task implements ReadOnlyTask {

    private Todo todo;
    private Priority priority;
    private StartTime startTime;
    private EndTime endTime;

    /**
     * Every field must be present and not null.
     */
    public Task(Todo todo, Priority priority, StartTime startTime, EndTime endTime) {
        assert !CollectionUtil.isAnyNull(todo, startTime, endTime);
        this.todo = todo;
        this.priority = priority;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getTodo(), source.getPriority(), source.getStartTime(), source.getEndTime());
    }

    @Override
    public Todo getTodo() {
        return todo;
    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public StartTime getStartTime() {
        return startTime;
    }

    @Override
    public EndTime getEndTime() {
        return endTime;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || ((other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other)));
    }
    
    @Override
    public String toString() {
        return getAsText();
    }
    
>>>>>>> 85acd64d31eda9c80ef00ec71a27526ce74a4a34
}
