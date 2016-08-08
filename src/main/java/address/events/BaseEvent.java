package address.events;

import address.events.controller.JumpToListRequestEvent;
import address.events.controller.ResizeAppRequestEvent;

public abstract class BaseEvent {

    /**
     * All Events should have a clear unambiguous custom toString message so that feedback message creation
     * stays consistent and reusable.
     *
     * For example the event manager post method will call any posted event's toString and print it in the console.
     */
    public abstract String toString();

    /**
     * Returns true if the {@code other} event has the same intended result as this event,
     *     even if they are two different event objects. This is a weaker form of equality.
     *     e.g. if both events intended the selection to jump to the 1st element in the list. <br>
     *     The default implementation returns true of both objects are of the same class.
     *     For example, comparing two distinct {@link ResizeAppRequestEvent} will return
     *     true (because both intend to maximize the app). <br>
     *     If the events are parameterized, e.g. {@link JumpToListRequestEvent} this method
     *     should be overriden to compare the parameters.
     * @param other
     */
    public boolean hasSameIntentionAs(BaseEvent other){
        return other != null
            && this.getClass().equals(other.getClass());
    }
}
