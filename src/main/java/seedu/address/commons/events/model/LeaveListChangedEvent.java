package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyLeaveList;

public class LeaveListChangedEvent extends BaseEvent {
    public final ReadOnlyLeaveList data;

    public LeaveListChangedEvent(ReadOnlyLeaveList data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getRequestList().size();
    }
}
