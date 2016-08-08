package seedu.address.parser.qualifier;

import seedu.address.model.datatypes.person.ReadOnlyPerson;

public interface Qualifier {
    boolean run(ReadOnlyPerson person);
    String toString();
}
