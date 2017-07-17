package seedu.address.testutil;

import guitests.GuiRobot;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.BaseEvent;

/**
 * Helper methods related to events.
 */
public class EventsUtil {
    /**
     * Raises an {@code event}.
     */
    public static void raise(BaseEvent event) {
        new GuiRobot().interact(() -> EventsCenter.getInstance().post(event));
    }
}
