package seedu.address.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.address.model.task.Task;

/**
 * Unmodifiable View of Task List
 */
public interface ReadOnlyTaskList extends Observable {
    ObservableList<Task> getTaskList();
}
