package seedu.address.ui;

import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Time;

public class TitleCard extends UiPart{

    private static final String FXML = "TitleListCard.fxml";
    public static final String BLANK = " ";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private CheckBox completeStatus;

    private ReadOnlyTask task;
    private int displayedIndex;

    public TitleCard(){

    }

    public static TitleCard load(ReadOnlyTask task, int displayedIndex){
        TitleCard card = new TitleCard();
        card.task = task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        name.setText(task.getName().taskName);
        id.setText(displayedIndex + ". ");
        setDateTimeText();
        completeStatus.setSelected(task.getCompleted());
        setDesign();
    }

    public void setDateTimeText(){
        
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
