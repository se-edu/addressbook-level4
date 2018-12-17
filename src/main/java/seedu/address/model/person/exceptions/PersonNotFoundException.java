package seedu.address.model.person.exceptions;

import seedu.address.model.person.Person;

/**
 * Signals that the operation is unable to find the specified person.
 */
public class PersonNotFoundException extends RuntimeException {
    public PersonNotFoundException(Person person) {
        super("Could not find person: " + person.toString());
    }
}
