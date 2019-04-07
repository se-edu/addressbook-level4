package seedu.address.ui;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.task.Task;

/**
 *  Panel containing a list of tasks
 */
public class TickedTaskListPanel extends UiPart<Region> {

    private static final String FXML = "TaskListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TickedTaskListPanel.class);

    @javafx.fxml.FXML
    private ListView<Task> tickedTaskListView;

    public TickedTaskListPanel(ObservableList<Task> taskList, ObservableValue<Task> selectedTask,
                         Consumer<Task> onSelectedTaskChange) {
        super(FXML);
        tickedTaskListView.setItems(taskList);
        tickedTaskListView.setCellFactory(listView -> new TaskListViewCell());
        tickedTaskListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in person list panel changed to : '" + newValue + "'");
            onSelectedTaskChange.accept(newValue);
        });
        selectedTask.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected person changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected person,
            // otherwise we would have an infinite loop.
            if (Objects.equals(tickedTaskListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                tickedTaskListView.getSelectionModel().clearSelection();
            } else {
                int index = tickedTaskListView.getItems().indexOf(newValue);
                tickedTaskListView.scrollTo(index);
                tickedTaskListView.getSelectionModel().clearAndSelect(index);
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class TaskListViewCell extends ListCell<Task> {
        @Override
        protected void updateItem(Task task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TaskCard(task, getIndex() + 1).getRoot());
            }
        }
    }

}

