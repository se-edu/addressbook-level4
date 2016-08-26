package seedu.address.exceptions;

/**
 * Signals an error caused by duplicate data where there should be none.
 */
public abstract class DuplicateDataException extends IllegalValueException {
    public DuplicateDataException(String message) {
        super(message);
    }
}
