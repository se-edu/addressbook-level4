package seedu.address.parser.qualifier;

import seedu.address.model.datatypes.person.ReadOnlyPerson;
import seedu.address.commons.StringUtil;

public class LastNameQualifier implements Qualifier {
    private String lastName;

    public LastNameQualifier(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean run(ReadOnlyPerson person) {
        return StringUtil.containsIgnoreCase(person.getLastName(), lastName);
    }

    @Override
    public String toString() {
        return "lastName=" + lastName;
    }
}
