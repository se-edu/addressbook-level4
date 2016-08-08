package address.events.model;

import address.events.BaseEvent;
import address.model.datatypes.ReadOnlyAddressBook;

/** Indicates data in the model has changed*/
public class LocalModelChangedEvent extends BaseEvent {

    public final ReadOnlyAddressBook data;

    public LocalModelChangedEvent(ReadOnlyAddressBook data){
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getPersonList().size() + ", number of tags " + data.getTagList().size();
    }
}
