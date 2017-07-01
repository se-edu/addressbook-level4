package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_INITIAL;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_UPDATED;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import guitests.guihandles.StatusBarFooterHandle;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.model.AddressBook;

public class StatusBarFooterTest extends GuiUnitTest {

    private static final String STUB_SAVE_LOCATION = "Stub";

    private static Clock originalClock;
    private static Clock injectedClock;

    private StatusBarFooter statusBarFooter;
    private StatusBarFooterHandle statusBarFooterHandle;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        injectFixedClock();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        restoreOriginalClock();
    }

    @Before
    public void setUp() throws Exception {
        guiRobot.interact(() -> statusBarFooter = new StatusBarFooter(STUB_SAVE_LOCATION));
        uiPartRule.setUiPart(statusBarFooter);

        statusBarFooterHandle = new StatusBarFooterHandle();
    }

    private static void injectFixedClock() {
        originalClock = StatusBarFooter.getClock();
        injectedClock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
        StatusBarFooter.setClock(injectedClock);
    }

    private static void restoreOriginalClock() {
        StatusBarFooter.setClock(originalClock);
    }

    @Test
    public void display() throws Exception {
        // initial state
        assertEquals("./" + STUB_SAVE_LOCATION, statusBarFooterHandle.getSaveLocation());
        assertEquals(SYNC_STATUS_INITIAL, statusBarFooterHandle.getSyncStatus());
        guiRobot.pauseForHuman();

        // address book state changed
        EventsCenter.getInstance().post(new AddressBookChangedEvent(new AddressBook()));
        assertEquals("./" + STUB_SAVE_LOCATION, statusBarFooterHandle.getSaveLocation());
        assertEquals(String.format(SYNC_STATUS_UPDATED, new Date(injectedClock.millis()).toString()),
                statusBarFooterHandle.getSyncStatus());
        guiRobot.pauseForHuman();
    }
}
