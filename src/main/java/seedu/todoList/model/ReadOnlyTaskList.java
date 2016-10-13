package seedu.todoList.model;


import java.util.List;

import seedu.todoList.model.task.ReadOnlyTask;
import seedu.todoList.model.task.UniqueTaskList;

/**
 * Unmodifiable view of an TodoList
 */
public interface ReadOnlyTaskList {

    UniqueTaskList getUniqueTaskList();

    /**
     * Returns an unmodifiable view of tasks list
     */
    List<ReadOnlyTask> getTaskList();
}
