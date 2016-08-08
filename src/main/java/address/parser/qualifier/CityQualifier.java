package address.parser.qualifier;

import address.model.datatypes.person.ReadOnlyPerson;
import commons.StringUtil;

public class CityQualifier implements Qualifier {
    private String city;

    public CityQualifier(String city) {
        this.city = city;
    }

    @Override
    public boolean run(ReadOnlyPerson person) {
        return StringUtil.containsIgnoreCase(person.getCity(), city);
    }

    @Override
    public String toString() {
        return "city=" + city;
    }
}
