package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class StatusBarFooterTest extends AddressBookGuiTest {

    @Test
    public void syncStatus_commandSucceeds_statusUpdated() {
        String lastSyncStatus = statusBarFooter.getSyncStatus();

        // verify syncStatus is updated after a successful command
        commandBox.runCommand(td.hoon.getAddCommand());
        assertNotEquals(lastSyncStatus, statusBarFooter.getSyncStatus());

        // TODO: verify the updated time stamp is accurate
    }

    @Test
    public void syncStatus_commandFails_statusRemainsUnchanged() {
        String lastSyncStatus = statusBarFooter.getSyncStatus();

        // verify syncStatus remains unchanged after an invalid command
        commandBox.runCommand("invalid command");
        assertEquals(lastSyncStatus, statusBarFooter.getSyncStatus());
    }

}
