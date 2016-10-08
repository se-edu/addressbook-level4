package seedu.todoList.model.task;

import seedu.todoList.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a task in the TodoList .
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyEvent {

    Todo getTodo();
    Date getDate();
    StartTime getStartTime();
    EndTime getEndTime();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyEvent other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getTodo().equals(this.getTodo())); // state checks here onwards
    }

    /**
     * Formats the task as text, showing all contact details.
     */
    default String getAsText_Event() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Todo: ")
                .append(getTodo());
        return builder.toString();
    }
    

}
