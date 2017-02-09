package guitests;

import static org.junit.Assert.assertEquals;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import seedu.address.ui.StatusBarFooter;

public class StatusBarFooterTest extends AddressBookGuiTest {

    private Clock oldClock;
    private Clock newClock;

    @Before
    public void injectFixedClock() {
        oldClock = StatusBarFooter.clock;
        newClock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
        StatusBarFooter.clock = newClock;
    }

    @After
    public void removeInjectedClock() {
        StatusBarFooter.clock = oldClock;
    }

    @Test
    public void syncStatus_commandSucceeds_statusUpdated() {
        // verify syncStatus is updated correctly after a successful command
        String expected = "Last Updated: " + new Date(newClock.millis()).toString();
        commandBox.runCommand(td.hoon.getAddCommand());
        assertEquals(expected, statusBarFooter.getSyncStatus());
    }

    @Test
    public void syncStatus_commandFails_statusRemainsUnchanged() {
        String lastSyncStatus = statusBarFooter.getSyncStatus();

        // verify syncStatus remains unchanged after an invalid command
        commandBox.runCommand("invalid command");
        assertEquals(lastSyncStatus, statusBarFooter.getSyncStatus());
    }

}
