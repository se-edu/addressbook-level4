package seedu.todoList.model.task;

import seedu.todoList.commons.util.CollectionUtil;
import seedu.todoList.model.task.attributes.Date;
import seedu.todoList.model.task.attributes.EndTime;
import seedu.todoList.model.task.attributes.Name;

/**
 * Represents a task in the TodoList.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Deadline extends Task implements ReadOnlyTask {

	private Date date;
    private EndTime endTime;

    /**
     * Every field must be present and not null.
     */
    public Deadline(Name name, Date date, EndTime endTime) {
        assert !CollectionUtil.isAnyNull(name, date, endTime);
        super.name = name;
        this.date = date;
        this.endTime = endTime;
    }

    /**
     * Copy constructor.
     */
    public Deadline(Deadline source) {
        this(source.getName(), source.getDate(), source.getEndTime());
    }
    
    public Deadline(ReadOnlyTask source) {
    	this((Deadline) source);
    };

    public Date getDate() {
        return date;
    }

    public EndTime getEndTime() {
        return endTime;
    }

    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Event // instanceof handles nulls
                && super.name.equals(((Event) other).getName())
                && this.date.equals(((Event) other).getDate())
				&& this.endTime.equals(((Event) other).getEndTime()));

    }

    @Override
    public String toString() {
    	final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Date: ")
                .append(getDate())
                .append(" EndTime: ")
                .append(getEndTime());
        return builder.toString();
    }

	public Deadline getDeadline() {
		// DEADLINE Auto-generated method stub
		return null;
	}
}
