package seedu.address.ui;

import org.junit.Rule;

import guitests.GuiRobot;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.testutil.UiPartRule;

/**
 * A GUI unit test class for AddressBook.
 */
public abstract class GuiUnitTest {
    @Rule
    public final UiPartRule uiPartRule = new UiPartRule();

    protected final GuiRobot guiRobot = new GuiRobot();

    /**
     * Raises an {@code event}.
     */
    protected void raise(BaseEvent event) {
        guiRobot.interact(() -> EventsCenter.getInstance().post(event));
    }
}
