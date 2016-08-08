package seedu.address.events.controller;

import seedu.address.events.BaseEvent;

/**
 * Represents a user request to minimize the app window
 */
public class MinimizeAppRequestEvent extends BaseEvent {
    @Override
    public String toString() {
        return "Request to minimize app window";
    }
}
