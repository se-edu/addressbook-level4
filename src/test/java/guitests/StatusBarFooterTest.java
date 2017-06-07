package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
import seedu.address.testutil.PersonUtil;
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
    public void syncStatus_initialValue() {
        assertEquals(SYNC_STATUS_INITIAL, mainWindowHandle.getStatusBarFooter().getSyncStatus());
    }

    @Test
    public void syncStatus_mutatingCommandSucceeds_syncStatusUpdated() {
        String timestamp = new Date(injectedClock.millis()).toString();
        String expected = String.format(SYNC_STATUS_UPDATED, timestamp);
        assertTrue(mainWindowHandle.getCommandBox()
                .runCommand(PersonUtil.getAddCommand(td.hoon))); // mutating command succeeds
        assertEquals(expected, mainWindowHandle.getStatusBarFooter().getSyncStatus());
    }

    @Test
    public void syncStatus_nonMutatingCommandSucceeds_syncStatusRemainsUnchanged() {
        assertTrue(mainWindowHandle.getCommandBox()
                .runCommand(ListCommand.COMMAND_WORD)); // non-mutating command succeeds
        assertEquals(SYNC_STATUS_INITIAL, mainWindowHandle.getStatusBarFooter().getSyncStatus());
    }

    @Test
    public void syncStatus_commandFails_syncStatusRemainsUnchanged() {
        assertFalse(mainWindowHandle.getCommandBox().runCommand("invalid command")); // invalid command fails
        assertEquals(SYNC_STATUS_INITIAL, mainWindowHandle.getStatusBarFooter().getSyncStatus());
    }

}
