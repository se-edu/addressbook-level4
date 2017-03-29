package seedu.address.testutil;

import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFUALT_NAME = "Alice Pauline";
    public static final String DEFUALT_PHONE = "85355255";
    public static final String DEFUALT_EMAIL = "alice@gmail.com";
    public static final String DEFUALT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFUALT_TAGS = "friends";

    private Person person;

    public PersonBuilder() throws IllegalValueException {
        Name defaultName = new Name(DEFUALT_NAME);
        Phone defaultPhone = new Phone(DEFUALT_PHONE);
        Email defaultEmail = new Email(DEFUALT_EMAIL);
        Address defaultAddress = new Address(DEFUALT_ADDRESS);
        Set<Tag> defaultTags = SampleDataUtil.getTagSet(DEFUALT_TAGS);
        this.person = new Person(defaultName, defaultPhone, defaultEmail, defaultAddress, defaultTags);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        this.person = new Person(personToCopy);
    }

    public PersonBuilder withName(String name) throws IllegalValueException {
        this.person.setName(new Name(name));
        return this;
    }

    public PersonBuilder withTags(String ... tags) throws IllegalValueException {
        this.person.setTags(SampleDataUtil.getTagSet(tags));
        return this;
    }

    public PersonBuilder withAddress(String address) throws IllegalValueException {
        this.person.setAddress(new Address(address));
        return this;
    }

    public PersonBuilder withPhone(String phone) throws IllegalValueException {
        this.person.setPhone(new Phone(phone));
        return this;
    }

    public PersonBuilder withEmail(String email) throws IllegalValueException {
        this.person.setEmail(new Email(email));
        return this;
    }

    public Person build() {
        return this.person;
    }

}
