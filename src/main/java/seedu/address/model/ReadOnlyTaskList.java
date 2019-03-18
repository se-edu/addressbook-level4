package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.task.Task;

public interface ReadOnlyTaskList {
    ObservableList<Task> getTaskList();
}
