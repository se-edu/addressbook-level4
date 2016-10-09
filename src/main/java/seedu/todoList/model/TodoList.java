package seedu.todoList.model;

import javafx.collections.ObservableList;
import seedu.todoList.model.task.Event;
import seedu.todoList.model.task.ReadOnlyTask;
import seedu.todoList.model.task.Task;
import seedu.todoList.model.task.UniqueTaskList;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the TodoList  level
 * Duplicates are not allowed (by .equals comparison)
 */
public class TodoList implements ReadOnlyTodoList {

    private final UniqueTaskList tasks;

    {
        tasks = new UniqueTaskList();
    }

    public TodoList() {}

    /**
     * tasks and Tags are copied into this TodoList 
     */
    public TodoList(ReadOnlyTodoList toBeCopied) {
        this(toBeCopied.getUniqueTaskList());
    }

    /**
     * tasks and Tags are copied into this TodoList 
     */
    public TodoList(UniqueTaskList tasks) {
        resetData(tasks.getInternalList());
    }

    public static ReadOnlyTodoList getEmptyTodoList() {
        return new TodoList();
    }

//// list overwrite operations

    public ObservableList<Task> gettasks() {
        return tasks.getInternalList();
    }

    public void setTasks(List<Task> tasks) {
        this.tasks.getInternalList().setAll(tasks);
    }

    public void resetData(Collection<? extends ReadOnlyTask> newtasks) {
        setTasks(newtasks.stream().map(Task::new).collect(Collectors.toList()));
    }

    public void resetData(ReadOnlyTodoList newData) {
        resetData(newData.gettaskList());
    }

//// task-level operations

    /**
     * Adds a task to the TodoList.
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * @throws UniqueTaskList.DuplicatetaskException if an equivalent task already exists.
     */
    public void addTask(Task p) throws UniqueTaskList.DuplicatetaskException {
        tasks.add(p);
    }
    
    public void addEvent(Event event) {
        // TODO Auto-generated method stub
        
    }

    public boolean removeTask(ReadOnlyTask key) throws UniqueTaskList.taskNotFoundException {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new UniqueTaskList.taskNotFoundException();
        }
    }

//// util methods

    @Override
    public String toString() {
        return tasks.getInternalList().size() + " tasks";
        // TODO: refine later
    }

    @Override
    public List<ReadOnlyTask> gettaskList() {
        return Collections.unmodifiableList(tasks.getInternalList());
    }

    @Override
    public UniqueTaskList getUniqueTaskList() {
        return this.tasks;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TodoList // instanceof handles nulls
                && this.tasks.equals(((TodoList) other).tasks));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks);
    }

}
