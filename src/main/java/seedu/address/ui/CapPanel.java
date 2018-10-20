package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.TranscriptChangedEvent;
import seedu.address.model.Transcript;
import seedu.address.model.capgoal.CapGoal;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class CapPanel extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(CapPanel.class);
    private static final String FXML = "CapPanel.fxml";

    private final StringProperty currentCapString = new SimpleStringProperty("Current CAP: 0");
    private final StringProperty capGoalString = new SimpleStringProperty("CAP Goal: NIL");

    @FXML
    private Text currentCapText;
    @FXML
    private Text capGoalText;

    public CapPanel(Transcript transcript) {
        super(FXML);
        currentCapText.textProperty().bind(currentCapString);
        capGoalText.textProperty().bind(capGoalString);
        
        Platform.runLater(() -> currentCapString.setValue("Current cap: " + transcript.getCap()));
        CapGoal goal = transcript.getCapGoal();
        Platform.runLater(() -> capGoalString.setValue("CAP Goal: " + (goal.isSet ? goal.capGoal : "NIL")));
        registerAsAnEventHandler(this);
    }

    @Subscribe
    public void handleTranscriptChangedEvent(TranscriptChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local transcript data changed, calculating new cap"));
        try {
            Transcript transcript = (Transcript) event.data;
            Platform.runLater(() -> currentCapString.setValue("Current cap: " + transcript.getCap()));
            CapGoal goal = transcript.getCapGoal();
            Platform.runLater(() -> capGoalString.setValue("CAP Goal: " + (goal.isSet ? goal.capGoal : "NIL")));
        } catch (Exception e) {
            logger.info("Error trying to calculate new cap:" + e);
        }
    }

//    @Subscribe
//    private void handleNewResultAvailableEvent(NewResultAvailableEvent event) {
//        logger.info(LogsCenter.getEventHandlingLogMessage(event));
//        Platform.runLater(() -> displayed.setValue(event.message));
//    }

}
