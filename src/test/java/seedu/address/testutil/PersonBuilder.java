package seedu.address.testutil;

import seedu.address.model.datatypes.person.Person;
import seedu.address.model.datatypes.tag.Tag;
import seedu.address.commons.DateTimeUtil;

import java.util.Arrays;

/**
 * A utility class to build Person objects using a fluent interface.
 */
public class PersonBuilder {
    public static final int STUB_ID = -99;
    private final Person person;

    /**
     * Creates a person builder with the given firstName and lastName.
     */
    public PersonBuilder(String firstName, String lastName) {
        this.person = new Person(firstName, lastName, STUB_ID);
    }

    /**
     * Creates a person builder with the given firstName, lastName and ID.
     */
    public PersonBuilder(String firstName, String lastName, int id) {
        this.person = new Person(firstName, lastName, id);
    }

    /**
     * Creates a person builder with the given Person object.
     * The person object will be mutated during the building process.
     * @param initial
     */
    public PersonBuilder(Person initial) {
        this.person = initial;
    }

    public PersonBuilder withFirstName(String firstName) {
        person.setFirstName(firstName);
        return this;
    }

    public PersonBuilder withLastName(String lastName) {
        person.setLastName(lastName);
        return this;
    }

    public PersonBuilder withStreet(String street) {
        person.setStreet(street);
        return this;
    }

    public PersonBuilder withCity(String city) {
        person.setCity(city);
        return this;
    }

    public PersonBuilder withPostalCode(String postalCode){
        person.setPostalCode(postalCode);
        return this;
    }

    public PersonBuilder withBirthday(String birthday){
        person.setBirthday(DateTimeUtil.parse(birthday));
        return this;
    }

    public PersonBuilder withGithubUsername(String githubUsername){
        person.setGithubUsername(githubUsername);
        return this;
    }

    public PersonBuilder withTags(Tag... tags){
        person.setTags(Arrays.asList(tags));
        return this;
    }

    public Person build() {
        return person;
    }
}
