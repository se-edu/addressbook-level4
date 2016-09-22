package seedu.address.testutil;

import seedu.address.commons.events.BaseEvent;

/**
 *
 */
public interface BaseEventSubscriber {

    public void receive(BaseEvent e);

}
