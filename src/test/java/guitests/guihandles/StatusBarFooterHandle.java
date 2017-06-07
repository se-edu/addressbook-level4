package guitests.guihandles;

import org.controlsfx.control.StatusBar;

/**
 * A handle for the status bar at the footer of the application.
 */
public class StatusBarFooterHandle extends NodeHandle {

    public static final String STATUS_BAR_PLACEHOLDER = "#statusbarPlaceholder";
    public static final String SYNC_STATUS_ID = "#syncStatus";
    public static final String SAVE_LOCATION_STATUS_ID = "#saveLocationStatus";

    public StatusBarFooterHandle(MainWindowHandle mainWindowHandle) {
        super(mainWindowHandle.getNode(STATUS_BAR_PLACEHOLDER));
    }

    public String getSyncStatus() {
        return ((StatusBar) getNode(SYNC_STATUS_ID)).getText();
    }

    public String getSaveLocation() {
        return ((StatusBar) getNode(SAVE_LOCATION_STATUS_ID)).getText();
    }
}
