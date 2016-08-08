package address.parser.qualifier;

import address.model.datatypes.person.ReadOnlyPerson;

public interface Qualifier {
    boolean run(ReadOnlyPerson person);
    String toString();
}
