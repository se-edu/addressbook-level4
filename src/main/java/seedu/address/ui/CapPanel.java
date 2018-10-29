package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.TranscriptChangedEvent;
import seedu.address.model.Transcript;
import seedu.address.model.capgoal.CapGoal;
//@@author jeremyyew
/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class CapPanel extends UiPart<Region> {
    
    private static final Logger logger = LogsCenter.getLogger(CapPanel.class);
    private static final String FXML = "CapPanel.fxml";

    private final DoubleProperty currentCapDouble = new SimpleDoubleProperty(0);
    private final StringProperty capGoalString = new SimpleStringProperty("NIL");

    @FXML
    private Text currentCapText;
    @FXML
    private Text capGoalText;
    @FXML
    private Text currentCapValue;
    @FXML
    private Text capGoalValue;

    public CapPanel(Transcript transcript) {
        super(FXML);
        currentCapValue.textProperty().bind(Bindings.convert(currentCapDouble));
        capGoalValue.textProperty().bind(capGoalString);

        Platform.runLater(() -> currentCapDouble.setValue(transcript.getCap()));
        CapGoal goal = transcript.getCapGoal();
        Platform.runLater(() -> capGoalString.setValue(goal.isSet ? String.valueOf(goal.capGoal) : "NIL"));
        registerAsAnEventHandler(this);
    }

    @Subscribe
    public void handleTranscriptChangedEvent(TranscriptChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event,
                "Local transcript data changed, obtaining new cap and cap goal"));
        try {
            Transcript transcript = (Transcript) event.data;
            Platform.runLater(() -> currentCapDouble.setValue(transcript.getCap()));
            CapGoal goal = transcript.getCapGoal();
            Platform.runLater(() -> capGoalString.setValue(goal.isSet ? String.valueOf(goal.capGoal) : "NIL"));
        } catch (Exception e) {
            logger.info("Error trying to obtain new cap and cap goal:" + e);
        }
    }

}
