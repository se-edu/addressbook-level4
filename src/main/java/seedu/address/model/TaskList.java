package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

import java.util.List;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class TaskList implements seedu.address.model.ReadOnlyTaskList {

    private final UniqueTaskList tasks;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();


    protected void indicateModified() {
      //  invalidationListenerManager.callListeners(this);
    }

    public TaskList(){}

    public TaskList(ReadOnlyTaskList toBeCopied) {
        this();
      //  resetData(toBeCopied);
    }

    public void setTasks(List<Task> tasks) {
        this.tasks.setTasks(tasks);
        indicateModified();
    }


    public void addTask(Task task){
        tasks.add(task);
        indicateModified();
    }


    @Override
    public ObservableList<Task> getTaskList() {
        return tasks.asUnmodifiableObservableList();
    }
    {
        tasks  = new UniqueTaskList();
    }
}
