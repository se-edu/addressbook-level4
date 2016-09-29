package seedu.address.commons.core;

import com.google.common.eventbus.EventBus;
import seedu.address.commons.events.BaseEvent;

import java.util.logging.Logger;

/**
 * Manages the event dispatching of the app.
 */
public class EventsCenter {
    private static final Logger logger = LogsCenter.getLogger(EventsCenter.class);
    private final EventBus eventBus;
    private static EventsCenter instance;

    public static EventsCenter getInstance() {
        if (instance == null) {
            instance = new EventsCenter();
        }
        return instance;
    }

    public static void clearSubscribers() {
        instance = null;
    }

    private EventsCenter() {
        eventBus = new EventBus();
    }

    public EventsCenter registerHandler(Object handler) {
        eventBus.register(handler);
        return this;
    }

    /**
     * Posts an event to the event bus.
     * @param event
     * @param <E>
     * @return
     */
    public <E extends BaseEvent> EventsCenter post(E event) {
        logger.info(event.getClass().getName() + ": " + event.toString());
        return postEvent(event);
    }

    private <E extends BaseEvent> EventsCenter postEvent(E event) {
        eventBus.post(event);
        return this;
    }

}
