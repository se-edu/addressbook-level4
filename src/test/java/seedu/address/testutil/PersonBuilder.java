package seedu.address.testutil;

import seedu.address.exceptions.IllegalValueException;
import seedu.address.model.Tag;
import seedu.address.model.person.*;

/**
 *
 */
public class PersonBuilder {

    private TestPerson person;

    public PersonBuilder() {
        this.person = new TestPerson();
    }

    public PersonBuilder withName(String name) throws IllegalValueException {
        this.person.setName(new Name(name));
        return this;
    }

    public PersonBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            person.getTags().add(new Tag(tag));
        }
        return this;
    }

    public PersonBuilder withAddress(String address, boolean visible) throws IllegalValueException {
        this.person.setAddress(new Address(address, visible));
        return this;
    }

    public PersonBuilder withPhone(String phone, boolean visible) throws IllegalValueException {
        this.person.setPhone(new Phone(phone, visible));
        return this;
    }

    public PersonBuilder withEmail(String email, boolean visible) throws IllegalValueException {
        this.person.setEmail(new Email(email, visible));
        return this;
    }

    public TestPerson build() {
        return this.person;
    }

}
