package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.CalendarPanelHandle;

//@@author ChoChihTun
public class CalendarPanelTest extends GuiUnitTest {

    private CalendarPanel calendarPanel;
    private CalendarPanelHandle calendarPanelHandle;

    @Before
    public void setUp() {
        calendarPanel = new CalendarPanel();
        guiRobot.interact(() -> calendarPanel = new CalendarPanel());
        uiPartRule.setUiPart(calendarPanel);

        calendarPanelHandle = new CalendarPanelHandle(calendarPanel.getRoot());
    }

    @Test
    public void display() {
        // calendar view page is not null
        assertNotNull(calendarPanel.getRoot());

        // default view page of calendar
        assertEquals(calendarPanel.getRoot().getSelectedPage(), calendarPanelHandle.getDefaultCalendarViewPage());
    }
}
