package seedu.address.commons.events.storage;

import seedu.address.commons.events.BaseEvent;

import java.io.File;

/**
 * Indicates a request to load data from the file
 */
public class LoadDataRequestEvent extends BaseEvent {

    /** The file from which the data to be loaded */
    public File file;

    public LoadDataRequestEvent(File file) {
        this.file = file;
    }

    @Override
    public String toString(){
        return "from " + file;
    }
}
