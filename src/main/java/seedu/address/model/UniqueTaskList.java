package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.task.exceptions.TaskNotFoundException;

/**
 * A list of tasks that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 */
public class UniqueTaskList implements Iterable<Task> {

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Task toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a task to the list.
     *
     * Need to add an exception that functions well in the commented out code below to prevent duplicate tasks
     */
    public void add(Task toAdd) /*throws DuplicatePersonException */ {
        requireNonNull(toAdd);
        /*
        if (contains(toAdd)) {
           throw new TimingClashException();
        }
        */
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     *
     *
     */

    public void setTask(Task target, Task editedTask) {
        requireNonNull(editedTask);

        int index = internalList.indexOf(target);
        if (index == -1) {
            System.out.println("Place Holder");
        }

        if (!target.equals(editedTask) && internalList.contains(editedTask)) {
            System.out.println("Place Holder");
        }

        internalList.set(index, editedTask);
    }

    /**
     * Removes the equivalent task from the list.
     *
     */
    public boolean remove(Task toRemove) throws TaskNotFoundException {
        requireNonNull(toRemove);
        final boolean taskFoundAndDeleted = internalList.remove(toRemove);
        if (!taskFoundAndDeleted) {
            throw new TaskNotFoundException();
        }
        return taskFoundAndDeleted;
    }

    public void setTasks(UniqueTaskList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setTasks(List<Task> tasks) /* throws DuplicatePersonException */ {
        requireAllNonNull(tasks);
        final UniqueTaskList replacement = new UniqueTaskList();
        for (final Task task : tasks) {
            replacement.add(task);
        }
        setTasks(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Task> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Task> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTaskList // instanceof handles nulls
                && this.internalList.equals(((UniqueTaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
