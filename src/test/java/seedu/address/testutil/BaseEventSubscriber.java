package seedu.address.testutil;

import seedu.address.events.BaseEvent;

/**
 *
 */
public interface BaseEventSubscriber {

    public void receive(BaseEvent e);

}
