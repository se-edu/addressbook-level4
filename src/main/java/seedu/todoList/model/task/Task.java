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
    private Priority priority;
    private StartTime startTime;
    private EndTime endTime;

    /**
     * Every field must be present and not null.
     */
    public Task(Name name, Priority priority, StartTime startTime, EndTime endTime) {
        assert !CollectionUtil.isAnyNull(name, startTime, endTime);
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Copy constructor.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getPriority(), source.getStartTime(), source.getEndTime());
    }

    @Override
    public Name getName() {
        return name;
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
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public String toString() {
        return getAsText();
    }

	public void setTags(UniqueTagList uniqueTagList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Todo getTodo() {
		// TODO Auto-generated method stub
		return null;
	}

}
