package seedu.todoList.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.todoList.model.task.ReadOnlyTask;
import seedu.todoList.model.task.ReadOnlyEvent;

public class EventCard extends UiPart{

<<<<<<< HEAD:src/main/java/seedu/todoList/ui/EventCard.java
    private static final String FXML = "EventCard.fxml";
=======
    private static final String FXML = "TaskListCard.fxml";
>>>>>>> e60184ee291f8238357c383073cb787221a2d62e:src/main/java/seedu/todoList/ui/TaskCard.java

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
<<<<<<< HEAD:src/main/java/seedu/todoList/ui/EventCard.java
    private Label date;
    @FXML
    private Label startTime;
    @FXML
    private Label endTime;
=======
    private Label Todo;
    @FXML
    private Label Priority;
    @FXML
    private Label StartTime;
    @FXML
    private Label EndTime;
    @FXML
    private Label Date;
>>>>>>> e60184ee291f8238357c383073cb787221a2d62e:src/main/java/seedu/todoList/ui/TaskCard.java

    private ReadOnlyTask task;
    private int displayedIndex;

    public EventCard(){

    }

    public static EventCard load(ReadOnlyTask task, int displayedIndex){
        EventCard card = new EventCard();
        card.task = task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
<<<<<<< HEAD:src/main/java/seedu/todoList/ui/EventCard.java
        name.setText(task.getName().fullName);
        id.setText(displayedIndex + ". ");
        date.setText(task.getDate().value);
        startTime.setText(task.getStartTime().value);
        endTime.setText(task.getEndTime().value);
=======
        Todo.setText(task.getTodo().todo);
        Priority.setText(task.getPriority().priority);
        StartTime.setText(task.getStartTime().startTime);
        EndTime.setText(task.getEndTime().endTime);
>>>>>>> e60184ee291f8238357c383073cb787221a2d62e:src/main/java/seedu/todoList/ui/TaskCard.java
    }

    public HBox getLayout() {
        return cardPane;
    }

    @Override
    public void setNode(Node node) {
        cardPane = (HBox)node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}
