package seedu.address.commons.events.storage;

import seedu.address.commons.events.BaseEvent;

import java.io.File;

/**
 * Indicates an exception during a file opening
 */
public class FileOpeningExceptionEvent extends BaseEvent {

    public Exception exception;
    public File file;

    public FileOpeningExceptionEvent(Exception exception, File file){
        this.exception = exception;
        this.file = file;
    }

    @Override
    public String toString(){
        return exception + " while opening " + file.getName();
    }
}
