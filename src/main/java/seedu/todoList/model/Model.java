package seedu.todoList.model;

import java.util.Set;

import seedu.todoList.commons.core.UnmodifiableObservableList;
import seedu.todoList.model.task.ReadOnlyTask;
import seedu.todoList.model.task.Task;
import seedu.todoList.model.task.Event;
import seedu.todoList.model.task.UniqueTaskList;
import seedu.todoList.commons.exceptions.*;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetTodoListData(ReadOnlyTaskList newData);
    /** Clears existing backing model and replaces with the provided new data. */
    void resetEventListData(ReadOnlyTaskList newData);
    /** Clears existing backing model and replaces with the provided new data. */
    void resetDeadlineListData(ReadOnlyTaskList newData);

    /** Returns the TodoList */
    ReadOnlyTaskList getTodoList();
    /** Returns the EventList */
    ReadOnlyTaskList getEventList();
    /** Returns the DeadlineList */
    ReadOnlyTaskList getDeadlineList();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target, String dataType) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(Task task) throws IllegalValueException, UniqueTaskList.DuplicatetaskException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTodoList();
    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredEventList();
    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredDeadlineList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredTodoListToShowAll();
    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredEventListToShowAll();
    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredDeadlineListToShowAll();

    /** Updates the filter of the filtered todo list to filter by the given keywords*/
    void updateFilteredTodoList(Set<String> keywords);
    /** Updates the filter of the filtered event list to filter by the given keywords*/
    void updateFilteredEventList(Set<String> keywords);
    /** Updates the filter of the filtered deadline list to filter by the given keywords*/
    void updateFilteredDeadlineList(Set<String> keywords);

}
