package systemtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_UPDATED;

import java.time.Clock;
import java.util.Date;

import guitests.guihandles.StatusBarFooterHandle;

/**
 * Contains assertion methods to check the validity of the application.
 */
public class SystemTestAsserts {

    /**
     * Asserts that only the sync status in the status bar was changed
     * to the {@code injectedClock} timing, while the save location remains
     * the same.
     */
    public static void assertOnlySyncStatusChanged(StatusBarFooterHandle statusBarFooterHandle, Clock injectedClock) {
        String timestamp = new Date(injectedClock.millis()).toString();
        String expectedSyncStatus = String.format(SYNC_STATUS_UPDATED, timestamp);
        assertEquals(expectedSyncStatus, statusBarFooterHandle.getSyncStatus());
        assertFalse(statusBarFooterHandle.isSaveLocationChanged());
    }
}
