package address.parser.qualifier;

import address.model.datatypes.person.ReadOnlyPerson;

public class IdQualifier implements Qualifier {
    private final int id;

    public IdQualifier(int id) {
        this.id = id;
    }

    @Override
    public boolean run(ReadOnlyPerson person) {
        return person.getId() == id;
    }

    @Override
    public String toString() {
        return "id=" + id;
    }
}
