package guitests.guihandles;

import org.controlsfx.control.StatusBar;

import javafx.scene.Node;

/**
 * A handle for the {@code StatusBarFooter} at the footer of the application.
 */
public class StatusBarFooterHandle extends NodeHandle {
    public static final String STATUS_BAR_PLACEHOLDER = "#statusbarPlaceholder";

    private static final String SYNC_STATUS_ID = "#syncStatus";
    private static final String SAVE_LOCATION_STATUS_ID = "#saveLocationStatus";

    public StatusBarFooterHandle(Node statusBarFooterNode) {
        super(statusBarFooterNode);
    }

    /**
     * Gets the text of the sync status portion of the status bar.
     */
    public String getSyncStatus() {
        return ((StatusBar) getChildNode(SYNC_STATUS_ID)).getText();
    }

    /**
     * Gets the text of the 'save location' portion of the status bar.
     */
    public String getSaveLocation() {
        return ((StatusBar) getChildNode(SAVE_LOCATION_STATUS_ID)).getText();
    }
}
