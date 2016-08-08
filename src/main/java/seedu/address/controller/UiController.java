package seedu.address.controller;

import seedu.address.events.BaseEvent;
import seedu.address.events.EventManager;

/**
 * Parent class for all controllers.
 */
public class UiController {
    public UiController(){
        EventManager.getInstance().registerHandler(this);
    }

    protected void raise(BaseEvent event){
        EventManager.getInstance().post(event);
    }

    protected void raisePotentialEvent(BaseEvent event) {
        EventManager.getInstance().postPotentialEvent(event);
    }
}
