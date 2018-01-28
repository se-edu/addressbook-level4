package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates that a new result is available.
 */
public class NewResultAvailableEvent extends BaseEvent {

    public final String message;
    public final boolean isSuccessful;

    public NewResultAvailableEvent(String message, boolean isSuccessful) {
        this.message = message;
        this.isSuccessful = isSuccessful;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
