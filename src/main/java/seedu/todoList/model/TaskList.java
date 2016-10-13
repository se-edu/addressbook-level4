package seedu.todoList.model;

import javafx.collections.ObservableList;
import seedu.todoList.model.task.ReadOnlyTask;
import seedu.todoList.model.task.*;
import seedu.todoList.model.task.UniqueTaskList;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the TodoList  level
 * Duplicates are not allowed (by .equals comparison)
 */
public class TaskList implements ReadOnlyTaskList {

    private UniqueTaskList tasks;

    {
        tasks = new UniqueTaskList();
    }

    public TaskList() {}

    /**
     * tasks and Tags are copied into this TodoList 
     */
    public TaskList(ReadOnlyTaskList toBeCopied) {
        this(toBeCopied.getUniqueTaskList());
    }

    /**
     * tasks and Tags are copied into this TodoList 
     */
    public TaskList(UniqueTaskList tasks) {
        resetData(tasks.getInternalList());
    }

    public static ReadOnlyTaskList getEmptyTaskList() {
        return new TaskList();
    }

//// list overwrite operations

    public ObservableList<Task> getTasks() {
        return tasks.getInternalList();
    }

    public void setTasks(List<Task> tasks) {
    	this.tasks.getInternalList().setAll(tasks);
    }


//    public void resetData(Collection<? extends ReadOnlyTask> newTasks) {
//        setTasks(newTasks.stream().map(Task::new).collect(Collectors.toList()));
//    }
    
    public void resetData(Collection<? extends ReadOnlyTask> newTasks) {
    	Object[] typeCheck = newTasks.toArray();
    	if(typeCheck.length == 0) {
    		tasks = new UniqueTaskList();
    	}
    	else if(typeCheck[0] instanceof Todo) {
    		setTasks(newTasks.stream().map(Todo::new).collect(Collectors.toList()));
    	}
    	else if(typeCheck[0] instanceof Event) {
    		setTasks(newTasks.stream().map(Event::new).collect(Collectors.toList()));
    	}
    	else if(typeCheck[0] instanceof Deadline) {
    		setTasks(newTasks.stream().map(Deadline::new).collect(Collectors.toList()));
    	}
    }
    
    public void resetData(ReadOnlyTaskList newData) {
        resetData(newData.getTaskList());
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

    public boolean removeTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }

//// util methods

    @Override
    public String toString() {
        return tasks.getInternalList().size() + " tasks";
        // TODO: refine later
    }

    @Override
    public List<ReadOnlyTask> getTaskList() {
        return Collections.unmodifiableList(tasks.getInternalList());
    }

    @Override
    public UniqueTaskList getUniqueTaskList() {
        return this.tasks;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskList // instanceof handles nulls
                && this.tasks.equals(((TaskList) other).tasks));
    }
}