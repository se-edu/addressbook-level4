package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.task.ReadOnlyTask;
//@@author A0135812L
public class TaskCardMarkChangedEvent extends BaseEvent{

    private final int displayedIndex;
    
    public TaskCardMarkChangedEvent(int displayedIndex){
        this.displayedIndex = displayedIndex;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public int getDisplayedIndex() {
        return displayedIndex;
    }
      

}
