package seedu.address.model.group;

import java.util.Iterator;
import java.util.Objects;
import static java.util.Objects.requireNonNull;

import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

public class Group {

    public static final String MESSAGE_GROUP_NO_DESCRIPTION =
            "No group description has been inputted.";

    //Identity field
    private final Name name;

    //Data fields
    private final String description;
    private UniquePersonList groupMembers;

    /**
     * @param name field must be present and not null.
     */
    public Group(Name name, String description) {
        requireNonNull(name);
        this.name = name;
        this.description = description;
        groupMembers = new UniquePersonList();
    }

    public Name getName() { return name; }

    public String getDescription() {
        if (description.equals("")) return MESSAGE_GROUP_NO_DESCRIPTION;
        return description;
    }

    public void printMembers() {
        Iterator<Person> itr = groupMembers.iterator();
        if (itr.hasNext()) {
            System.out.println(itr.next() + "\n");
        }
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Description: ")
                .append(getDescription())
                .append(" Number of Members: ")
                .append(groupMembers.numberOfPersons());
        return builder.toString();
    }
}
