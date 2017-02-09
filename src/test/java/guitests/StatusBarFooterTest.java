package guitests;

import static org.junit.Assert.assertEquals;

import static seedu.address.ui.StatusBarFooter.DEFAULT_STATUS_BEFORE_ANY_UPDATES;

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
    public void syncStatus_mutatingCommandSucceeds_statusUpdated() {
        assertEquals(DEFAULT_STATUS_BEFORE_ANY_UPDATES, statusBarFooter.getSyncStatus()); // verify initial value
        String expected = "Last Updated: " + new Date(injectedClock.millis()).toString();
        commandBox.runCommand(td.hoon.getAddCommand());
        assertEquals(expected, statusBarFooter.getSyncStatus());
    }

    @Test
    public void syncStatus_nonMutatingCommandSucceeds_statusRemainsUnchanged() {
        assertEquals(DEFAULT_STATUS_BEFORE_ANY_UPDATES, statusBarFooter.getSyncStatus()); // verify initial value
        String expected = DEFAULT_STATUS_BEFORE_ANY_UPDATES;
        commandBox.runCommand(ListCommand.COMMAND_WORD);
        assertResultMessage(ListCommand.MESSAGE_SUCCESS); // verify the list command succeeds
        assertEquals(expected, statusBarFooter.getSyncStatus());
    }

    @Test
    public void syncStatus_commandFails_statusRemainsUnchanged() {
        String expected = DEFAULT_STATUS_BEFORE_ANY_UPDATES;
        commandBox.runCommand("invalid command");
        assertEquals(expected, statusBarFooter.getSyncStatus());
    }

}
