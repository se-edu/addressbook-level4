package address.parser.qualifier;

import address.model.datatypes.person.ReadOnlyPerson;
import commons.StringUtil;

public class FirstNameQualifier implements Qualifier {
    private String firstName;

    public FirstNameQualifier(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public boolean run(ReadOnlyPerson person) {
        return StringUtil.containsIgnoreCase(person.getFirstName(), firstName);
    }

    @Override
    public String toString() {
        return "firstName=" + firstName;
    }
}
