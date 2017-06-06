package seedu.address.model.person.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate Person objects.
 */
public class DuplicatePersonException extends DuplicateDataException {
    public DuplicatePersonException() {
        super("Operation would result in duplicate persons");
    }
}
