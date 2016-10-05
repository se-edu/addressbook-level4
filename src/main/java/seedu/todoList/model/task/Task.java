package seedu.todoList.model.task;

import seedu.todoList.model.tag.UniqueTagList;
import seedu.todoList.commons.util.CollectionUtil;

import java.util.Objects;


/**
 * Represents a task in the TodoList.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private StartTime startTime;
    private EndTime endTime;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, StartTime startTime, EndTime endTime) {
        assert !CollectionUtil.isAnyNull(name, phone, email, Todo, tags);
        this.name = name;
        this.startTime = startTime;
        this.startTime = endTime;
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getStartTime(), source.getEndTime());
    }

    @Override
    public Name getName() {
        return name;
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
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, Todo, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
