package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Clears the Browser Panel
 */
public class ClearBrowserPanelEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
