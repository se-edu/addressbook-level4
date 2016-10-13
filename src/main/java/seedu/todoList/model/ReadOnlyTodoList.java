package seedu.todoList.model;


import java.util.List;

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
<<<<<<< HEAD
=======

>>>>>>> e60184ee291f8238357c383073cb787221a2d62e
}
