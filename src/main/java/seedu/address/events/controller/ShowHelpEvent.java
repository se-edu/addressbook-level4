package seedu.address.events.controller;

import seedu.address.events.BaseEvent;

/**
 * An event requesting to view the help page.
 */
public class ShowHelpEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
