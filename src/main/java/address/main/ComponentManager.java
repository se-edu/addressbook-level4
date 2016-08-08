package address.main;

import address.events.BaseEvent;
import address.events.EventManager;

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

    /**
     * Injects the {@link EventManager} dependency
     * @param eventManager
     */
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    protected void raise(BaseEvent event){
        eventManager.post(event);
    }
}
