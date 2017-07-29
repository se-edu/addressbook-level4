package seedu.address.testutil;

import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_REMARK = "";
    public static final String DEFAULT_TAGS = "friends";

    private Person person;

    public PersonBuilder() throws IllegalValueException {
        Name defaultName = new Name(DEFAULT_NAME);
        Phone defaultPhone = new Phone(DEFAULT_PHONE);
        Email defaultEmail = new Email(DEFAULT_EMAIL);
        Address defaultAddress = new Address(DEFAULT_ADDRESS);
        Remark defaultRemark = new Remark(DEFAULT_REMARK);
        Set<Tag> defaultTags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
        this.person = new Person(defaultName, defaultPhone, defaultEmail, defaultAddress, defaultRemark, defaultTags);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(ReadOnlyPerson personToCopy) {
        this.person = new Person(personToCopy);
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     * @throws IllegalValueException if the {@code name} is invalid.
     */
    public PersonBuilder withName(String name) throws IllegalValueException {
        this.person.setName(new Name(name));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     * @throws IllegalValueException if any of the {@code tags} is invalid.
     */
    public PersonBuilder withTags(String ... tags) throws IllegalValueException {
        this.person.setTags(SampleDataUtil.getTagSet(tags));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     * @throws IllegalValueException if the {@code address} is invalid.
     */
    public PersonBuilder withAddress(String address) throws IllegalValueException {
        this.person.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     * @throws IllegalValueException if the {@code phone} is invalid.
     */
    public PersonBuilder withPhone(String phone) throws IllegalValueException {
        this.person.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     * @throws IllegalValueException if the {@code email} is invalid.
     */
    public PersonBuilder withEmail(String email) throws IllegalValueException {
        this.person.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Person} that we are building.
     */
    public PersonBuilder withRemark(String remark) {
        this.person.setRemark(new Remark(remark));
        return this;
    }

    public Person build() {
        return this.person;
    }

}
