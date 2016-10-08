package seedu.todoList.model.task;

import seedu.todoList.model.tag.UniqueTagList;
import seedu.todoList.commons.util.CollectionUtil;

import java.util.Objects;


/**
 * Represents a task in the TodoList.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask,ReadOnlyEvent {

    private Todo todo;
    private Priority priority;
    private Date date;
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
    public Date getDate() {
        return date;
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
                && this.isSameStateAs((ReadOnlyTask) other)))
                || ((other instanceof ReadOnlyEvent) 
                && this.isSameStateAs((ReadOnlyEvent) other));
    }
    
    @Override
    public String toString() {
        return getAsText();
    }
    
    public String toString_Event() {
        return getAsText_Event();
    }

}
