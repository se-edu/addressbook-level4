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
        StringBuilder sb = new StringBuilder();
        sb.append("add ");
        sb.append(getPersonDetails(person));
        return sb.toString();
    }

    /**
     * Returns an edit command string for editing person at 0-based {@code addressBookIndex} to {@code newPerson}.
     */
    public static String getEditCommand(int addressBookIndex, Person newPerson) {
        StringBuilder sb = new StringBuilder();
        sb.append("edit " + (addressBookIndex + 1) + " ");
        sb.append(getPersonDetails(newPerson));
        return sb.toString();
    }

    /**
     * Returns the part of command string for the {@code person}'s information.
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
