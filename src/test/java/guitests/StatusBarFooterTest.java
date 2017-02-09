package guitests;

import static org.junit.Assert.assertEquals;

import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_INITIAL;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_UPDATED;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.ListCommand;
import seedu.address.ui.StatusBarFooter;

public class StatusBarFooterTest extends AddressBookGuiTest {

    private Clock originalClock;
    private Clock injectedClock;

    @Before
    public void injectFixedClock() {
        originalClock = StatusBarFooter.getClock();
        injectedClock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
        StatusBarFooter.setClock(injectedClock);
    }

    @After
    public void restoreOriginalClock() {
        StatusBarFooter.setClock(originalClock);
    }

    @Test
    public void syncStatus_mutatingCommandSucceeds_syncStatusUpdated() {
        assertEquals(SYNC_STATUS_INITIAL, statusBarFooter.getSyncStatus()); // verify initial value
        commandBox.runCommand(td.hoon.getAddCommand());
        String timestamp = new Date(injectedClock.millis()).toString();
        String expected = String.format(SYNC_STATUS_UPDATED, timestamp);
        assertEquals(expected, statusBarFooter.getSyncStatus());
    }

    @Test
    public void syncStatus_nonMutatingCommandSucceeds_syncStatusRemainsUnchanged() {
        assertEquals(SYNC_STATUS_INITIAL, statusBarFooter.getSyncStatus()); // verify initial value
        commandBox.runCommand(ListCommand.COMMAND_WORD);
        assertResultMessage(ListCommand.MESSAGE_SUCCESS); // verify the list command succeeds
        assertEquals(SYNC_STATUS_INITIAL, statusBarFooter.getSyncStatus());
    }

    @Test
    public void syncStatus_commandFails_syncStatusRemainsUnchanged() {
        assertEquals(SYNC_STATUS_INITIAL, statusBarFooter.getSyncStatus()); // verify initial value
        commandBox.runCommand("invalid command");
        assertEquals(SYNC_STATUS_INITIAL, statusBarFooter.getSyncStatus());
    }

}
