package seedu.address.events.controller;

import seedu.address.events.BaseEvent;

/**
 * Indicates a request to jump to the list of persons
 */
public class JumpToListRequestEvent extends BaseEvent {

    public int targetIndex;

    public JumpToListRequestEvent(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    /**
     * Returns true if both events intended the selection to jump to the same item in the list.
     */
    @Override
    public boolean hasSameIntentionAs(BaseEvent other){
        return (other != null)
            && (other instanceof JumpToListRequestEvent)
            && ((JumpToListRequestEvent) other).targetIndex == this.targetIndex;
    }
}
