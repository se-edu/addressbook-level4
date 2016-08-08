package seedu.address.parser.qualifier;

import seedu.address.model.datatypes.person.ReadOnlyPerson;
import seedu.address.commons.StringUtil;

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
