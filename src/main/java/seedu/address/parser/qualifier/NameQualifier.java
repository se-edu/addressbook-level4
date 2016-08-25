package seedu.address.parser.qualifier;

import seedu.address.commons.StringUtil;
import seedu.address.model.person.ReadOnlyPerson;

/**
 *
 */
public class NameQualifier implements Qualifier {
    private String name;

    public NameQualifier(String name) {
        this.name = name;
    }

    @Override
    public boolean run(ReadOnlyPerson person) {
        return StringUtil.containsIgnoreCase(person.getName().fullName, name);
    }

    @Override
    public String toString() {
        return "name=" + name;
    }
}