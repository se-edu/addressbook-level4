package seedu.address.ui;

import java.time.Clock;
import java.util.Date;
import java.util.logging.Logger;

import org.controlsfx.control.StatusBar;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.util.FxViewUtil;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {

    public static final String SYNC_STATUS_INITIAL = "Not updated yet in this session";
    public static final String SYNC_STATUS_UPDATED = "Last Updated: %s";

    /**
     * Used to generate time stamps.
     *
     * TODO: change clock to an instance variable.
     * We leave it as a static variable because manual dependency injection
     * will require passing down the clock reference all the way from MainApp,
     * but it should be easier once we have factories/DI frameworks.
     */
    private static Clock clock = Clock.systemDefaultZone();

    private static final Logger logger = LogsCenter.getLogger(StatusBarFooter.class);

    @FXML
    private StatusBar syncStatus;
    @FXML
    private StatusBar saveLocationStatus;

    private static final String FXML = "StatusBarFooter.fxml";

    /**
     * Sets the clock used to determine the current time.
     */
    public static void setClock(Clock clock) {
        StatusBarFooter.clock = clock;
    }

    /**
     * Returns the clock currently in use.
     */
    public static Clock getClock() {
        return clock;
    }

    public StatusBarFooter(AnchorPane placeHolder, String saveLocation) {
        super(FXML);
        addToPlaceholder(placeHolder);
        setSyncStatus(SYNC_STATUS_INITIAL);
        setSaveLocation("./" + saveLocation);
        registerAsAnEventHandler(this);
    }

    private void addToPlaceholder(AnchorPane placeHolder) {
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeHolder.getChildren().add(getRoot());
    }

    private void setSaveLocation(String location) {
        this.saveLocationStatus.setText(location);
    }

    private void setSyncStatus(String status) {
        this.syncStatus.setText(status);
    }

    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent abce) {
        long now = clock.millis();
        String lastUpdated = new Date(now).toString();
        logger.info(LogsCenter.getEventHandlingLogMessage(abce, "Setting last updated status to " + lastUpdated));
        setSyncStatus(String.format(SYNC_STATUS_UPDATED, lastUpdated));
    }
}
