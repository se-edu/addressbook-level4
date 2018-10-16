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
import seedu.address.commons.events.ui.NewResultAvailableEvent;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class CapPanel extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(CapPanel.class);
    private static final String FXML = "CapPanel.fxml";

    private final StringProperty displayed = new SimpleStringProperty("Current Cap: 0");

    @FXML
    private Text capText;

    public CapPanel() {
        super(FXML);
        capText.textProperty().bind(displayed);
        registerAsAnEventHandler(this);
    }

//    @Subscribe
//    private void handleNewResultAvailableEvent(NewResultAvailableEvent event) {
//        logger.info(LogsCenter.getEventHandlingLogMessage(event));
//        Platform.runLater(() -> displayed.setValue(event.message));
//    }

}
