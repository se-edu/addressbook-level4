package seedu.todoList.model;

import java.util.Set;

import seedu.todoList.commons.core.UnmodifiableObservableList;
import seedu.todoList.model.task.ReadOnlyTask;
import seedu.todoList.model.task.Task;
import seedu.todoList.model.task.Event;
import seedu.todoList.model.task.UniqueTaskList;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTodoList newData);

    /** Returns the TodoList */
    ReadOnlyTodoList getTodoList();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.taskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws UniqueTaskList.DuplicatetaskException;
    
    /** Adds the given event */
    void addEvent(Event event) throws UniqueTaskList.DuplicatetaskException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);

}
