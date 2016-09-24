package seedu.address.commons.events.storage;

import seedu.address.commons.events.BaseEvent;

import java.io.File;

/**
 * Indicates an exception during a file saving
 */
public class DataSavingExceptionEvent extends BaseEvent {

    public Exception exception;

    public DataSavingExceptionEvent(Exception exception) {
        this.exception = exception;
    }

    @Override
    public String toString(){
        return exception.toString();
    }
}
