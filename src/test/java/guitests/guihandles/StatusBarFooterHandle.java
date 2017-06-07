package guitests.guihandles;

import org.controlsfx.control.StatusBar;

/**
 * A handle for the {@code StatusBarFooter} at the footer of the application.
 */
public class StatusBarFooterHandle extends NodeHandle {

    private static final String STATUS_BAR_PLACEHOLDER = "#statusbarPlaceholder";
    private static final String SYNC_STATUS_ID = "#syncStatus";

    public StatusBarFooterHandle(MainWindowHandle mainWindowHandle) {
        super(mainWindowHandle.getChildNode(STATUS_BAR_PLACEHOLDER));
    }

    public String getSyncStatus() {
        return ((StatusBar) getChildNode(SYNC_STATUS_ID)).getText();
    }
}
