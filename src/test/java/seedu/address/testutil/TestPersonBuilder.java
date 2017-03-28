package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.UniqueTagList;

/**
 * A utility class to help with building TestPerson objects.
 */
public class TestPersonBuilder {

    public static final String DEFUALT_NAME = "Alice Pauline";
    public static final String DEFUALT_PHONE = "85355255";
    public static final String DEFUALT_EMAIL = "alice@gmail.com";
    public static final String DEFUALT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFUALT_TAGS = "friends";

    private TestPerson person;

    public TestPersonBuilder() throws IllegalValueException {
        Name defaultName = new Name(DEFUALT_NAME);
        Phone defaultPhone = new Phone(DEFUALT_PHONE);
        Email defaultEmail = new Email(DEFUALT_EMAIL);
        Address defaultAddress = new Address(DEFUALT_ADDRESS);
        UniqueTagList defaultTags = new UniqueTagList(DEFUALT_TAGS);
        this.person = new TestPerson(defaultName, defaultPhone, defaultEmail, defaultAddress, defaultTags);
    }

    /**
     * Initializes the TestPersonBuilder with the data of {@code personToCopy}.
     */
    public TestPersonBuilder(Person personToCopy) {
        this.person = new TestPerson(personToCopy);
    }

    public TestPersonBuilder withName(String name) throws IllegalValueException {
        this.person.setName(new Name(name));
        return this;
    }

    public TestPersonBuilder withTags(String ... tags) throws IllegalValueException {
        this.person.setTags(new UniqueTagList(tags));
        return this;
    }

    public TestPersonBuilder withAddress(String address) throws IllegalValueException {
        this.person.setAddress(new Address(address));
        return this;
    }

    public TestPersonBuilder withPhone(String phone) throws IllegalValueException {
        this.person.setPhone(new Phone(phone));
        return this;
    }

    public TestPersonBuilder withEmail(String email) throws IllegalValueException {
        this.person.setEmail(new Email(email));
        return this;
    }

    public TestPerson build() {
        return this.person;
    }

}
