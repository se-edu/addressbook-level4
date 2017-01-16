package guitests;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StatusBarFooterTest extends AddressBookGuiTest {

    private Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
    private String lastSyncStatus;

    @Override
    protected Clock getClock() {
        return clock;
    }

    @Before
    public void setUp() {
        lastSyncStatus = statusBarFooter.getSyncStatus();
    }

    @Test
    public void statusBar_commandSucceeds_textUpdated() {
        Date expectedTime = new Date(clock.millis());
        commandBox.runCommand(td.hoon.getAddCommand());
        assertEquals("Last Updated: " + expectedTime.toString(), statusBarFooter.getSyncStatus());
    }

    @Test
    public void commandBox_commandFails_textStays() {
        commandBox.runCommand("invalid command");
        assertEquals(lastSyncStatus, statusBarFooter.getSyncStatus());
    }

}
