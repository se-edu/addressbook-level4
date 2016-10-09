package seedu.todoList.model.task;

import seedu.todoList.model.tag.UniqueTagList;
import seedu.todoList.commons.util.CollectionUtil;

import java.util.Objects;


/**
 * Represents a task in the TodoList.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Event implements ReadOnlyEvent {

    private Todo todo;
    private Date date;
    private StartTime startTime;
    private EndTime endTime;

    /**
     * Every field must be present and not null.
     */
    public Event(Todo todo, Date date, StartTime startTime, EndTime endTime) {
        assert !CollectionUtil.isAnyNull(todo, startTime, endTime);
        this.todo = todo;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Copy constructor.
     */
    public Event(ReadOnlyEvent source) {
        this(source.getTodo(), source.getDate(), source.getStartTime(), source.getEndTime());
    }

    @Override
    public Todo getTodo() {
        return todo;
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
                || ((other instanceof ReadOnlyEvent)  // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyEvent) other));
    }
    
    @Override
    public String toString() {
        return getAsText();
    }

}
