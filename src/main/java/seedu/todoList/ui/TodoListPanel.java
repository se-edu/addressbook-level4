package seedu.todoList.ui;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.todoList.commons.core.LogsCenter;
import seedu.todoList.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.todoList.model.task.ReadOnlyTask;

import java.util.logging.Logger;

/**
 * Panel containing the list of tasks.
 */
public class TodoListPanel extends UiPart {
    private final Logger logger = LogsCenter.getLogger(TodoListPanel.class);
    private static final String FXML = "TodoListPanel.fxml";
    private VBox panel;
    private AnchorPane placeHolderPane;

    @FXML
    ListView<ReadOnlyTask> todoListView;

    public TodoListPanel() {
        super();
    }

    @Override
    public void setNode(Node node) {
        panel = (VBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }

    public static TodoListPanel load(Stage primaryStage, AnchorPane todoListPlaceholder,
                                       ObservableList<ReadOnlyTask> todoList) {
        TodoListPanel todoListPanel =
                UiPartLoader.loadUiPart(primaryStage, todoListPlaceholder, new TodoListPanel());
        todoListPanel.configure(todoList);
        return todoListPanel;
    }

    private void configure(ObservableList<ReadOnlyTask> todokList) {
        setConnections(todokList);
        addToPlaceholder();
    }

    private void setConnections(ObservableList<ReadOnlyTask> todoList) {
        todoListView.setItems(todoList);
        todoListView.setCellFactory(listView -> new TodoListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder() {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(panel);
    }

    private void setEventHandlerForSelectionChangeEvent() {
        todoListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                raise(new TaskPanelSelectionChangedEvent(newValue));
            }
        });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            todoListView.scrollTo(index);
            todoListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class TodoListViewCell extends ListCell<ReadOnlyTask> {

        public TodoListViewCell() {
        }

        @Override
        protected void updateItem(ReadOnlyTask task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(TodoCard.load(task, getIndex() + 1).getLayout());
            }
        }
    }

}
