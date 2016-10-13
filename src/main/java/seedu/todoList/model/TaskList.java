package seedu.todoList.model;

import javafx.collections.ObservableList;
<<<<<<< HEAD
=======
import seedu.todoList.model.task.Event;
>>>>>>> 85acd64d31eda9c80ef00ec71a27526ce74a4a34
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

<<<<<<< HEAD
    private UniqueTaskList tasks;
=======
    private final UniqueTaskList tasks;
>>>>>>> 85acd64d31eda9c80ef00ec71a27526ce74a4a34

    {
        tasks = new UniqueTaskList();
    }

    public TaskList() {}

    /**
     * tasks and Tags are copied into this TodoList 
     */
<<<<<<< HEAD
    public TaskList(ReadOnlyTaskList toBeCopied) {
        this(toBeCopied.getUniqueTaskList());
=======
<<<<<<< HEAD:src/main/java/seedu/todoList/model/TaskList.java
    public TaskList(ReadOnlyTodoList toBeCopied) {
        this(toBeCopied.getUniqueTaskList(), toBeCopied.getUniqueTagList());
=======
    public TodoList(ReadOnlyTodoList toBeCopied) {
        this(toBeCopied.getUniqueTaskList());
>>>>>>> e60184ee291f8238357c383073cb787221a2d62e:src/main/java/seedu/todoList/model/TodoList.java
>>>>>>> 85acd64d31eda9c80ef00ec71a27526ce74a4a34
    }

    /**
     * tasks and Tags are copied into this TodoList 
     */
<<<<<<< HEAD
    public TaskList(UniqueTaskList tasks) {
        resetData(tasks.getInternalList());
=======
<<<<<<< HEAD:src/main/java/seedu/todoList/model/TaskList.java
    public TaskList(UniqueTaskList tasks, UniqueTagList tags) {
        resetData(tasks.getInternalList(), tags.getInternalList());
=======
    public TodoList(UniqueTaskList tasks) {
        resetData(tasks.getInternalList());
>>>>>>> e60184ee291f8238357c383073cb787221a2d62e:src/main/java/seedu/todoList/model/TodoList.java
>>>>>>> 85acd64d31eda9c80ef00ec71a27526ce74a4a34
    }

    public static ReadOnlyTaskList getEmptyTaskList() {
        return new TaskList();
    }

//// list overwrite operations

    public ObservableList<Task> getTasks() {
        return tasks.getInternalList();
    }

    public void setTasks(List<Task> tasks) {
<<<<<<< HEAD
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
    
=======
        this.tasks.getInternalList().setAll(tasks);
    }

    public void resetData(Collection<? extends ReadOnlyTask> newtasks) {
        setTasks(newtasks.stream().map(Task::new).collect(Collectors.toList()));
    }

    public void resetData(ReadOnlyTodoList newData) {
        resetData(newData.gettaskList());
    }
>>>>>>> 85acd64d31eda9c80ef00ec71a27526ce74a4a34

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

<<<<<<< HEAD
=======

>>>>>>> 85acd64d31eda9c80ef00ec71a27526ce74a4a34
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
<<<<<<< HEAD:src/main/java/seedu/todoList/model/TaskList.java
                || (other instanceof TaskList // instanceof handles nulls
<<<<<<< HEAD
                && this.tasks.equals(((TaskList) other).tasks));
=======
                && this.tasks.equals(((TaskList) other).tasks)
                && this.tags.equals(((TaskList) other).tags));
=======
                || (other instanceof TodoList // instanceof handles nulls
                && this.tasks.equals(((TodoList) other).tasks));
>>>>>>> e60184ee291f8238357c383073cb787221a2d62e:src/main/java/seedu/todoList/model/TodoList.java
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks);
>>>>>>> 85acd64d31eda9c80ef00ec71a27526ce74a4a34
    }

}
