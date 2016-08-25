package seedu.address.parser.qualifier;


import seedu.address.model.person.ReadOnlyPerson;

public interface Qualifier {
    boolean run(ReadOnlyPerson person);
    String toString();
}
