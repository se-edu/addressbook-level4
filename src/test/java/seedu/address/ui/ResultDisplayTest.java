package seedu.address.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.ResultDisplayHandle;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;

public class ResultDisplayTest extends GuiUnitTest {

    private static final NewResultAvailableEvent NEW_RESULT_EVENT_STUB = new NewResultAvailableEvent("Stub");

    private ResultDisplay resultDisplay;
    private ResultDisplayHandle resultDisplayHandle;

    @Before
    public void setUp() throws Exception {
        guiRobot.interact(() -> resultDisplay = new ResultDisplay());
        uiPartRule.setUiPart(resultDisplay);

        resultDisplayHandle = new ResultDisplayHandle();
    }

    @Test
    public void display() throws Exception {
        EventsCenter.getInstance().post(NEW_RESULT_EVENT_STUB);
        assertEquals(NEW_RESULT_EVENT_STUB.message, resultDisplayHandle.getText());
        guiRobot.pauseForHuman();
    }
}
