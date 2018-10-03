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

    public Group(Name name, String description, UniqueList<Person> groupMembers) {
        requireAllNonNull(name, description, groupMembers);
        this.name = name;
        this.description = description;
        this.groupMembers = groupMembers;
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
     * Returns all member of a group as a String.
     */
    public String printMembers() {
        Iterator<Person> itr = groupMembers.iterator();
        StringBuilder builder = new StringBuilder();
        while (itr.hasNext()) {
            builder.append(itr.next().getName().fullName).append("\n");
        }
        return builder.toString();
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
     * Same as isSame because Groups are uniquely identified by their names.
     * TO NOTE: Used when deleting groups.
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
        return otherGroup.getName().equals(getName());
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
                .append("\nDescription: ")
                .append(getDescription())
                .append("\nNumber of Members: ")
                .append(groupMembers.asUnmodifiableObservableList().size());
        return builder.toString();
    }
}
