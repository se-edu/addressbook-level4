package address.parser.qualifier;

import address.model.datatypes.person.ReadOnlyPerson;
import commons.StringUtil;

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
