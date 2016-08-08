package address.exceptions;

import address.model.datatypes.person.ReadOnlyPerson;

public class DuplicatePersonException extends DuplicateDataException {

    private final ReadOnlyPerson offender;

    public DuplicatePersonException(ReadOnlyPerson dup) {
        offender = dup;
    }

    @Override
    public String toString() {
        return "Duplicate person not allowed: " + offender + " already exists!";
    }
}
