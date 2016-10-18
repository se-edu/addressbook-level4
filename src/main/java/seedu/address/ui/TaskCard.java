package seedu.address.ui;

import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Time;

public class TaskCard extends UiPart{

    private static final String FXML = "TaskListCard.fxml";
    public static final String BLANK = " ";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label time;
    @FXML
    private Label startTime;
    @FXML
    private Label endTime;
    @FXML
    private Label address;
    @FXML
    private Label description;
    @FXML
    private Label tags;
    @FXML
    private CheckBox completeStatus;

    private ReadOnlyTask task;
    private int displayedIndex;

    public TaskCard(){

    }

    public static TaskCard load(ReadOnlyTask task, int displayedIndex){
        TaskCard card = new TaskCard();
        card.task = task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        name.setText(task.getName().taskName);
        id.setText(displayedIndex + ". ");
        setDateTimeText();
        address.setText(task.getLocation().value);
        description.setText(task.getDescription().value);
        tags.setText(task.tagsString());
        completeStatus.setSelected(task.getCompleted());
        setDesign();
    }

    public void setDateTimeText(){
        if (task.getTime().isPresent()) {
            time.setText(task.getTime().get().getStartDateString());
            if (task.getTime().get().getUntimedStatus()) {
                startTime.setText(BLANK);
                endTime.setText(BLANK);
            } else if (task.getTime().get().getEndDate().isPresent()) {
                startTime.setText("Starts at: " + task.getTime().get().getStartDate().toLocalTime()
                        .format(DateTimeFormatter.ofPattern(Time.TIME_PRINT_FORMAT)));
                endTime.setText("Ends at: " + task.getTime().get().getEndDate().get().toLocalTime()
                        .format(DateTimeFormatter.ofPattern(Time.TIME_PRINT_FORMAT)));
            } else {
                startTime.setText("Starts at: " + task.getTime().get().getStartDate().toLocalTime()
                        .format(DateTimeFormatter.ofPattern(Time.TIME_PRINT_FORMAT)));
                endTime.setText(BLANK);
            }
        } else {
            time.setText(BLANK);
            startTime.setText(BLANK);
            endTime.setText(BLANK);
        }

    }

    @FXML
    private void setDesign() {
        boolean isCompleted = task.getCompleted();

        if (isCompleted) {
            completeStatus.setSelected(true);
        } else {
            completeStatus.setSelected(false);
        }
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
