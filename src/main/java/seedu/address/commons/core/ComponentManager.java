package seedu.address.commons.core;

import seedu.address.commons.events.BaseEvent;

/**
 * Base class for *Manager classes
 *
 * Registers the class' event handlers in eventManager
 */
public abstract class ComponentManager {
    protected EventManager eventManager;

    /**
     * Uses default {@link EventManager}
     */
    public ComponentManager(){
        this(EventManager.getInstance());
    }

    public ComponentManager(EventManager eventManager) {
        this.eventManager = eventManager;
        eventManager.registerHandler(this);
    }

    protected void raise(BaseEvent event){
        eventManager.post(event);
    }
}
