package seedu.todoList.model.task;

/**
 * A read-only immutable interface for a task in the TodoList .
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Todo getTodo();
    Priority getPriority();
    StartTime getStartTime();
    EndTime getEndTime();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getTodo().equals(this.getTodo())  // state checks here onwards
                && other.getPriority().equals(this.getPriority())
                && other.getStartTime().equals(this.getStartTime())
                && other.getEndTime().equals(this.getEndTime()));
    }

    /**
     * Formats the task as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTodo())
                .append("\nPriority: ")  
                .append(getPriority())
                .append("\nStart Time: ")
                .append(getStartTime())
                .append("\nEnd Time: ")
                .append(getEndTime());
        return builder.toString();
    }
	
}
