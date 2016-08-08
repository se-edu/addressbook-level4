package address.events.controller;

import address.events.BaseEvent;

/**
 * Represents a user request to resize the app window (toggles between default size and max size)
 */
public class ResizeAppRequestEvent extends BaseEvent {
    @Override
    public String toString() {
        return "Request to resize app window";
    }
}
