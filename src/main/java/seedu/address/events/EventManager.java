package seedu.address.events;

import com.google.common.eventbus.EventBus;
import seedu.address.commons.core.LoggerManager;

import java.util.logging.Logger;

/**
 * Manages the event dispatching of the app.
 */
public class EventManager {
    private static final Logger logger = LoggerManager.getLogger(EventManager.class);
    private final EventBus eventBus;
    private static EventManager instance;

    public static EventManager getInstance() {
        if (instance == null) {
            instance = new EventManager();
        }
        return instance;
    }

    public static void clearSubscribers() {
        instance = null;
    }

    private EventManager() {
        eventBus = new EventBus();
    }

    public EventManager registerHandler(Object handler) {
        eventBus.register(handler);
        return this;
    }

    /**
     * Posts an event to the event bus.
     * @param event
     * @param <E>
     * @return
     */
    public <E extends BaseEvent> EventManager post(E event) {
        logger.info(event.getClass().getName() + ": " + event.toString());
        return postEvent(event);
    }

    private <E extends BaseEvent> EventManager postEvent(E event) {
        eventBus.post(event);
        return this;
    }

    /**
     * Similar to {@link #post} event, but logs at fine level.
     * To be used for less important events.
     * @param event
     * @param <E>
     * @return
     */
    public <E extends BaseEvent> EventManager postPotentialEvent(E event) {
        logger.fine(event.getClass().getName() + ": " + event.toString());
        return postEvent(event);
    }

}
