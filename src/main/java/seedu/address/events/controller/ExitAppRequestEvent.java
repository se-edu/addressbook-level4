package seedu.address.events.controller;

import seedu.address.events.BaseEvent;

/**
 *
 */
public class ExitAppRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
