package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.task.ReadOnlyTask;

//@@author A0135767U
public class ViewItemRequestEvent extends BaseEvent{
	
    public final ReadOnlyTask newSelection;
    public final int targetIndex;

    public ViewItemRequestEvent(ReadOnlyTask newSelection, int targetIndex) {
        this.newSelection = newSelection;
        this.targetIndex = targetIndex;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
    
    public ReadOnlyTask getNewSelection() {
        return newSelection;
    }

}
