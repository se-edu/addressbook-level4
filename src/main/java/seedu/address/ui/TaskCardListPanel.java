package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.address.model.Task;


/**
 * Panel containing the list of tasks.
 */
//@@author a-shakra
public class TaskCardListPanel extends UiPart<Region> {
    private static final String FXML = "TaskCardListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TaskCardListPanel.class);

    @FXML
    private ListView<TaskCard> taskListView;

    public TaskCardListPanel(ObservableList<Task> taskList) {
        super(FXML);
        setConnections(taskList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Task> taskList) {
        ObservableList<TaskCard> mappedList = EasyBind.map(
                taskList, (task) -> new TaskCard(task, taskList.indexOf(task) + 1));
        taskListView.setItems(mappedList);
        taskListView.setCellFactory(listView -> new TaskListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }


    private void setEventHandlerForSelectionChangeEvent() {
        taskListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in Task list panel changed to : '" + newValue + "'");
                        raise(new TaskPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code PersonCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            taskListView.scrollTo(index);
            taskListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PersonCard}.
     */
    class TaskListViewCell extends ListCell<TaskCard> {

        @Override
        protected void updateItem(TaskCard task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(task.getRoot());
            }
        }
    }

}
