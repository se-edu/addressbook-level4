package seedu.address.parser.qualifier;

import seedu.address.model.datatypes.person.ReadOnlyPerson;
import seedu.address.commons.StringUtil;

public class StreetQualifier implements Qualifier {
    private final String street;

    public StreetQualifier(String street) {
        this.street = street;
    }

    @Override
    public boolean run(ReadOnlyPerson person) {
        return StringUtil.containsIgnoreCase(person.getStreet(), street);
    }

    @Override
    public String toString() {
        return "street=" + street;
    }
}
