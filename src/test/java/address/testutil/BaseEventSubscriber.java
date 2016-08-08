package address.testutil;

import address.events.BaseEvent;

/**
 *
 */
public interface BaseEventSubscriber {

    public void receive(BaseEvent e);

}
