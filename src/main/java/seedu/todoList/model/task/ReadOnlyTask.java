package seedu.todoList.model.task;

import seedu.todoList.model.task.attributes.Name;

/**
 * A read-only immutable interface for a task in the TodoList .
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    Name getName();

}
