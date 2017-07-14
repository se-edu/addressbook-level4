package guitests.guihandles;

import static org.junit.Assert.assertEquals;

import org.controlsfx.control.StatusBar;

import javafx.scene.Node;

/**
 * A handle for the {@code StatusBarFooter} at the footer of the application.
 */
public class StatusBarFooterHandle extends NodeHandle<Node> {
    public static final String STATUS_BAR_PLACEHOLDER = "#statusbarPlaceholder";

    private static final String SYNC_STATUS_ID = "#syncStatus";
    private static final String SAVE_LOCATION_STATUS_ID = "#saveLocationStatus";

    private final StatusBar syncStatusNode;
    private final StatusBar saveLocationNode;

    private String lastSeenSyncStatus;
    private String lastSeenSaveLocation;

    public StatusBarFooterHandle(Node statusBarFooterNode) {
        super(statusBarFooterNode);

        this.syncStatusNode = getChildNode(SYNC_STATUS_ID);
        this.saveLocationNode = getChildNode(SAVE_LOCATION_STATUS_ID);
    }

    /**
     * Returns the text of the sync status portion of the status bar.
     */
    public String getSyncStatus() {
        return syncStatusNode.getText();
    }

    /**
     * Returns the text of the 'save location' portion of the status bar.
     */
    public String getSaveLocation() {
        return saveLocationNode.getText();
    }

    public void rememberSyncStatus() {
        lastSeenSyncStatus = getSyncStatus();
    }

    public void assertSyncStatusNotChanged() {
        assertEquals(lastSeenSyncStatus, getSyncStatus());
    }

    public void rememberSaveLocation() {
        lastSeenSaveLocation = getSaveLocation();
    }

    public void assertSaveLocationNotChanged() {
        assertEquals(lastSeenSaveLocation, getSaveLocation());
    }
}
