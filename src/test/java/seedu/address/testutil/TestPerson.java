package seedu.address.testutil;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.UniqueTagList;

/**
 * Provides extra utility for Person, used in testing only.
 */
public class TestPerson extends Person {

    /**
     * Creates a TestPerson from provided fields. Every field must be present and not null.
     */
    public TestPerson(Name name, Phone phone, Email email, Address address, UniqueTagList tags) {
        super(name, phone, email, address, tags);
    }

    /**
     * Creates a copy of {@code personToCopy}.
     */
    public TestPerson(Person personToCopy) {
        super(personToCopy);
    }

    /**
     * Returns an add command string for adding this person.
     */
    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getName().fullName + " ");
        sb.append("a/" + this.getAddress().value + " ");
        sb.append("p/" + this.getPhone().value + " ");
        sb.append("e/" + this.getEmail().value + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("t/" + s.tagName + " "));
        return sb.toString();
    }

}
