package guitests.guihandles;

import org.controlsfx.control.StatusBar;

import guitests.GuiRobot;
import javafx.stage.Stage;
import seedu.address.TestApp;

/**
 * A handle for the status bar at the footer of the application.
 */
public class StatusBarFooterHandle extends GuiHandle {

    public static final String SYNC_STATUS_ID = "#syncStatus";
    public static final String SAVE_LOCATION_STATUS_ID = "#saveLocationStatus";

    public StatusBarFooterHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public String getSyncStatus() {
        return ((StatusBar) getNode(SYNC_STATUS_ID)).getText();
    }

    public String getSaveLocation() {
        return ((StatusBar) getNode(SAVE_LOCATION_STATUS_ID)).getText();
    }
}
