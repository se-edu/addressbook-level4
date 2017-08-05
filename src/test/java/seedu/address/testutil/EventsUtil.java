package seedu.address.testutil;

import guitests.GuiRobot;
import javafx.application.Platform;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.BaseEvent;

/**
 * Helper methods related to events.
 */
public class EventsUtil {
    /**
     * Posts {@code event} to all registered subscribers. This method will return successfully after the {@code event}
     * has been posted to all subscribers.
     */
    public static void post(BaseEvent event) {
        new GuiRobot().interact(() -> EventsCenter.getInstance().post(event));
    }

    public static void raise(BaseEvent event) {
        Platform.runLater(() -> EventsCenter.getInstance().post(event));
    }
}
