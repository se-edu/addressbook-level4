package seedu.address.model;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

import java.util.Iterator;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class TaskList implements seedu.address.model.ReadOnlyTaskList {

    private final UniqueTaskList tasks;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();


    protected void indicateModified() {
      //  invalidationListenerManager.callListeners(this);
    }

    public TaskList(){}

    @Override
    public void addListener(InvalidationListener listener) {
        invalidationListenerManager.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        invalidationListenerManager.removeListener(listener);
    }

    public TaskList(ReadOnlyTaskList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    public void resetData(ReadOnlyTaskList newData){
        requireNonNull(newData);
        setTasks(newData.getTaskList());
    }


    public void setTasks(List<Task> tasks) {
        this.tasks.setTasks(tasks);
        indicateModified();
    }

    public void setTask(Task target, Task editedTask){
        requireNonNull(editedTask);
        tasks.setTask(target, editedTask);
        indicateModified();
    }


    public void addTask(Task task){
        tasks.add(task);
        indicateModified();
    }

    public boolean hasTask(Task task) {
        requireNonNull(task);
        return tasks.contains(task);
    }
    public void removeTask(Task task){
        tasks.remove(task);
        indicateModified();
    }

    @Override
    public boolean equals(Object other){
        return other == this || (other instanceof TaskList)
                && tasks.equals((((TaskList) other).tasks));
    }

    @Override
    public String toString(){
        return tasks.asUnmodifiableObservableList().size() + " persons";

    }
    @Override
    public ObservableList<Task> getTaskList() {
        return tasks.asUnmodifiableObservableList();
    }

    {
        tasks  = new UniqueTaskList();
    }



    @Override
    public int hashCode() { return tasks.hashCode(); }
}
