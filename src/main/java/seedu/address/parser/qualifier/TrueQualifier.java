package seedu.address.parser.qualifier;

import seedu.address.model.datatypes.person.ReadOnlyPerson;

public class TrueQualifier implements Qualifier {

    public TrueQualifier() {
    }

    @Override
    public boolean run(ReadOnlyPerson person) {
        return true;
    }

    @Override
    public String toString() {
        return "TRUE";
    }
}
