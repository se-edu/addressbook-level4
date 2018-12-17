package seedu.address.model.person.exceptions;

import seedu.address.model.person.Person;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class DuplicatePersonException extends RuntimeException {
    public DuplicatePersonException() {
        super("Operation would result in duplicate persons");
    }
    public DuplicatePersonException(Person person) {
        super("Operation would result in duplicate person: " + person.toString());
    }
}
