package seedu.address.testutil;

import seedu.address.model.person.Person;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Person person) {
        return "add " + getPersonDetails(person);
    }

    /**
     * Returns an edit command string for editing the person at {@code zeroBasedIndex} to match {@code editedPerson}.
     */
    public static String getEditCommand(int zeroBasedIndex, Person editedPerson) {
        return "edit " + (zeroBasedIndex + 1) + " " + getPersonDetails(editedPerson);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    private static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(person.getName().fullName + " ");
        sb.append("a/" + person.getAddress().value + " ");
        sb.append("p/" + person.getPhone().value + " ");
        sb.append("e/" + person.getEmail().value + " ");
        person.getTags().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }
}
