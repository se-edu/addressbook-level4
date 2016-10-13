package seedu.todoList.model.task;

<<<<<<< HEAD
import seedu.todoList.commons.util.CollectionUtil;
import seedu.todoList.model.task.attributes.Name;
import seedu.todoList.model.task.attributes.Date;
import seedu.todoList.model.task.attributes.EndTime;
import seedu.todoList.model.task.attributes.StartTime;
=======
import seedu.todoList.model.tag.UniqueTagList;
import seedu.todoList.commons.util.CollectionUtil;

import java.util.Objects;

>>>>>>> 85acd64d31eda9c80ef00ec71a27526ce74a4a34

/**
 * Represents a task in the TodoList.
 * Guarantees: details are present and not null, field values are validated.
 */
<<<<<<< HEAD
public class Event extends Task implements ReadOnlyTask {

	private Date date;
=======
public class Event implements ReadOnlyEvent {

    private Todo todo;
    private Date date;
>>>>>>> 85acd64d31eda9c80ef00ec71a27526ce74a4a34
    private StartTime startTime;
    private EndTime endTime;

    /**
     * Every field must be present and not null.
     */
<<<<<<< HEAD
    public Event(Name name, Date date, StartTime startTime, EndTime endTime) {
        assert !CollectionUtil.isAnyNull(name, date, startTime, endTime);
        this.name = name;
=======
    public Event(Todo todo, Date date, StartTime startTime, EndTime endTime) {
        assert !CollectionUtil.isAnyNull(todo, startTime, endTime);
        this.todo = todo;
>>>>>>> 85acd64d31eda9c80ef00ec71a27526ce74a4a34
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Copy constructor.
     */
<<<<<<< HEAD
    public Event(Event source) {
        this(source.getName(), source.getDate(), source.getStartTime(), source.getEndTime());
    }
    
    public Event(ReadOnlyTask source) {
    	this((Event) source);
    };

    public Date getDate() {
        return date;
    }

=======
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
>>>>>>> 85acd64d31eda9c80ef00ec71a27526ce74a4a34
    public StartTime getStartTime() {
        return startTime;
    }

<<<<<<< HEAD
=======
    @Override
>>>>>>> 85acd64d31eda9c80ef00ec71a27526ce74a4a34
    public EndTime getEndTime() {
        return endTime;
    }

<<<<<<< HEAD
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Event // instanceof handles nulls
                && super.name.equals(((Event) other).getName())
                && this.date.equals(((Event) other).getDate())
				&& this.startTime.equals(((Event) other).getStartTime())
				&& this.endTime.equals(((Event) other).getEndTime()));

    }

    @Override
    public String toString() {
    	final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Date: ")
                .append(getDate())
                .append(" StartTime: ")
                .append(getStartTime())
                .append(" EndTime: ")
                .append(getEndTime());
        return builder.toString();
    }

	public Event getEvent() {
		// EVENT Auto-generated method stub
		return null;
	}

=======
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

>>>>>>> 85acd64d31eda9c80ef00ec71a27526ce74a4a34
}
