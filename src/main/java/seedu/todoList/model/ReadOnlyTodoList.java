package seedu.todoList.model;


import java.util.List;

import seedu.todoList.model.tag.Tag;
import seedu.todoList.model.tag.UniqueTagList;
import seedu.todoList.model.task.ReadOnlyTask;
import seedu.todoList.model.task.UniqueTaskList;

/**
 * Unmodifiable view of an TodoList
 */
public interface ReadOnlyTodoList {

    UniqueTaskList getUniqueTaskList();

    /**
     * Returns an unmodifiable view of tasks list
     */
    List<ReadOnlyTask> gettaskList();
}
