package seedu.address.model.group;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.model.Entity;
import seedu.address.model.UniqueList;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Represents a Group in the address book.
 * Guarantees: Field values are validated, immutable.
 */
public class Group extends Entity {

    public static final String MESSAGE_GROUP_NO_DESCRIPTION =
            "No group description has been inputted.";

    /**Groups must have unique names.*/
    private final Name name;

    // Data fields
    private final String description;
    private UniqueList<Person> groupMembers;

    /**
     * Every field must be present and not null.
     */
    public Group(Name name, String description) {
        requireAllNonNull(name, description);
        this.name = name;
        this.description = description;
        groupMembers = new UniqueList<>();
    }

    public Name getName() {
        return name;
    }

    public String getDescription() {
        if (description.equals("")) {
            return MESSAGE_GROUP_NO_DESCRIPTION;
        }
        return description;
    }

    public ObservableList<Person> getGroupMembers() {
        return groupMembers.asUnmodifiableObservableList();
    }

    /**
     * Prints out all member of a group.
     */
    public void printMembers() {
        Iterator<Person> itr = groupMembers.iterator();
        if (itr.hasNext()) {
            System.out.println(itr.next() + "\n");
        }
    }

    /**
     * Returns true if both groups of the same name.
     * This defines a weaker notion of equality between two groups.
     */
    @Override
    public boolean isSame(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Group)) {
            return false;
        }

        Group otherGroup = (Group) other;
        return otherGroup.getName().equals(getName());
    }

    /**
     * Returns true if both groups have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Group)) {
            return false;
        }

        Group otherGroup = (Group) other;
        return otherGroup.getName().equals(getName())
                && otherGroup.getDescription().equals(getDescription())
                && otherGroup.getGroupMembers().equals(getGroupMembers());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, description, groupMembers);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Description: ")
                .append(getDescription())
                .append(" Number of Members: ")
                .append(groupMembers.asUnmodifiableObservableList().size());
        return builder.toString();
    }
}
