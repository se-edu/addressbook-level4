package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyToDo;

/** Indicates the ToDo in the model has changed*/
public class ToDoChangedEvent extends BaseEvent {

    public final ReadOnlyToDo data;

    public ToDoChangedEvent(ReadOnlyToDo data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getTaskList().size() + ", number of tags " + data.getTagList().size();
    }
}
