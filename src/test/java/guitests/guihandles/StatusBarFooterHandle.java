package guitests.guihandles;

import org.controlsfx.control.StatusBar;

import seedu.address.TestApp;

/**
 * A handle for the status bar at the footer of the application.
 */
public class StatusBarFooterHandle extends GuiHandle {

    public static final String SYNC_STATUS_ID = "#syncStatus";

    public StatusBarFooterHandle() {
        super(TestApp.APP_TITLE);
    }

    public String getSyncStatus() {
        return ((StatusBar) getNode(SYNC_STATUS_ID)).getText();
    }
}
