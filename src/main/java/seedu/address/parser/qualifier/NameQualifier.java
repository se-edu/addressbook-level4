package seedu.address.parser.qualifier;

import seedu.address.commons.StringUtil;
import seedu.address.model.person.ReadOnlyPerson;

import java.util.Set;

/**
 *
 */
public class NameQualifier implements Qualifier {
    private Set<String> nameKeyWords;

    public NameQualifier(Set<String> nameKeyWords) {
        this.nameKeyWords = nameKeyWords;
    }

    @Override
    public boolean run(ReadOnlyPerson person) {
        return nameKeyWords.stream()
                           .filter(keyword -> StringUtil.containsIgnoreCase(person.getName().fullName, keyword))
                           .findAny()
                           .isPresent();
    }

    @Override
    public String toString() {
        return "name=" + String.join(", ", nameKeyWords);
    }
}