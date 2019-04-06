package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;




/**
 * Class tasklist containing all the methods of the tasklist
 */
public class TaskList implements seedu.address.model.ReadOnlyTaskList {

    private final UniqueTaskList tasks;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    public TaskList(ReadOnlyTaskList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    {
        tasks = new UniqueTaskList();
    }

    public TaskList() {

    }

    protected void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }





    @Override
    public void addListener(InvalidationListener listener) {
        invalidationListenerManager.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        invalidationListenerManager.removeListener(listener);
    }

    /**
     *  resets the task list with the dummy data
     * @param newData
     */
    public void resetData(ReadOnlyTaskList newData) {
        requireNonNull(newData);
        setTasks(newData.getTaskList());
    }

    public void setTasks(List<Task> tasks) {
        this.tasks.setTasks(tasks);
        indicateModified();
    }

    /**
     * replaces a selected task with the edited task
     * @param target
     * @param editedTask
     */
    public void setTask(Task target, Task editedTask) {
        requireNonNull(editedTask);
        tasks.setTask(target, editedTask);
        indicateModified();
    }

    /**
     * adds a task into {@code tasks}
     * @param task
     */
    public void addTask(Task task) {
        tasks.add(task);
        indicateModified();
    }

    /**
     * checks for existence of tasks in {@code tasks}
     * @param task
     */
    public boolean hasTask(Task task) {
        requireNonNull(task);
        return tasks.contains(task);
    }

    /**
     * removes a task from the {@code tasks}
     * @param task
     */
    public void removeTask(Task task) {
        tasks.remove(task);
        indicateModified();
    }

    /**
     * sorts the task list
     */
    public void sortTask(){
        tasks.sortTask();
        indicateModified();
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof TaskList)
                && tasks.equals((((TaskList) other).tasks));
    }

    @Override
    public String toString() {
        return tasks.asUnmodifiableObservableList().size() + " tasks";
    }

    @Override
    public ObservableList<Task> getTaskList() {
        return tasks.asUnmodifiableObservableList();
    }

    @Override
    public int hashCode() {
        return tasks.hashCode();
    }
}
