package guitests.guihandles;

import seedu.address.commons.events.BaseEvent;

/**
 * Represents that the {@code WebView} has successfully loaded.
 */
public class WebViewLoadedEvent extends BaseEvent {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
