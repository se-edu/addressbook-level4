package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyToDo;

/** Indicates the ToDo in the model has changed*/
public class AddressBookChangedEvent extends BaseEvent {

    public final ReadOnlyToDo data;

    public AddressBookChangedEvent(ReadOnlyToDo data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getPersonList().size() + ", number of tags " + data.getTagList().size();
    }
}
