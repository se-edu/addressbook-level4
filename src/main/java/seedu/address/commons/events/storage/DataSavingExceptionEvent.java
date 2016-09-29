package seedu.address.commons.events.storage;

import seedu.address.commons.events.BaseEvent;

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
