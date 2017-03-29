package seedu.address.testutil;

import seedu.address.model.person.Person;

public class PersonUtil {

    /**
     * Returns an add command string for adding this person.
     */
    public static String getAddCommand(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + person.getName().fullName + " ");
        sb.append("a/" + person.getAddress().value + " ");
        sb.append("p/" + person.getPhone().value + " ");
        sb.append("e/" + person.getEmail().value + " ");
        person.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
