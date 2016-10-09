package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.task.ReadOnlyTask;

public class ViewItemRequestEvent extends BaseEvent{
	
    public final ReadOnlyTask newSelection;

    public ViewItemRequestEvent(ReadOnlyTask newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
    
    public ReadOnlyTask getNewSelection() {
        return newSelection;
    }

}
