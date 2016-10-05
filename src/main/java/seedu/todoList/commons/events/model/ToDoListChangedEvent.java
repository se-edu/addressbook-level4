package seedu.todoList.commons.events.model;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.todoList.commons.events.BaseEvent;

/** Indicates the to-do-list in the model has changed*/
public class ToDoListChangedEvent extends BaseEvent {

    public final ReadOnlyAddressBook data;

    public ToDoListChangedEvent(ReadOnlyAddressBook data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getPersonList().size() + ", number of tags " + data.getTagList().size();
    }
}
