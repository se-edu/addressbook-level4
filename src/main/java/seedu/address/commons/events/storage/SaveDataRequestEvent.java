package seedu.address.commons.events.storage;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyAddressBook;

import java.io.File;

/**
 * Indicates a request for saving data has been raised
 */
public class SaveDataRequestEvent extends BaseEvent {

    /** The file to which the data should be saved */
    public final File file;

    public final ReadOnlyAddressBook data;

    public SaveDataRequestEvent(File file, ReadOnlyAddressBook data) {
        this.file = file;
        this.data = data;
    }

    @Override
    public String toString(){
        return file.toString();
    }
}
