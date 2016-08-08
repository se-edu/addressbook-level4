package address.controller;

import address.events.BaseEvent;
import address.events.EventManager;

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
