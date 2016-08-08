package seedu.address.testutil;

import seedu.address.model.datatypes.AddressBook;
import seedu.address.model.datatypes.person.Person;
import seedu.address.model.datatypes.tag.Tag;

/**
 * Typical data set used for testing
 */
public class TypicalTestData {

    public Tag colleagues = new Tag("colleagues");
    public Tag friends = new Tag("friends");
    public Tag family = new Tag("family");
    /*
     * TODO: add more details to these persons.
     * Note that staring letter of names have some pattern.
     * First person's name start with A and her second name starts with B.
     * The Second person's name starts with B and his other names start with C and D.
     * And so on.
     */

    public Person alice = new PersonBuilder(new Person("Alice", "Brown", 1))
                                                               .withStreet("81th Wall Street").withCity("New York")
                                                               .withPostalCode("41452").withBirthday("11.09.1983")
                                                               .withGithubUsername("alicia").withTags(friends).build();
    public Person benson = new PersonBuilder(new Person("Benson", "Christopher Dean", 2))
                                                                 .withStreet("Pittsburgh Street").withCity("Pittsburgh")
                                                                 .withPostalCode("424456").withGithubUsername("ben333").build();
    public Person charlie = new PersonBuilder(new Person("Charlie", "Davidson", 3)).withCity("Texas")
                                                                   .withGithubUsername("charlotte").build();
    public Person dan = new PersonBuilder(new Person("Dan", "Edwards", 4)).withBirthday("03.01.1995").build();
    public Person elizabeth = new Person("Elizabeth", "F. Green", 5);
    public Person fiona = new PersonBuilder("Fiona", "Wong", 6).withStreet("51th street").withCity("New York")
                                                               .withPostalCode("51245").withBirthday("01.01.1980")
                                                               .withGithubUsername("fiona").withTags(friends).build();
    public Person george = new PersonBuilder("George", "David", 7).withStreet("8th Ave").withCity("Chicago")
                                                                  .withPostalCode("25614").withBirthday("01.12.1988")
                                                                  .withGithubUsername("george").withTags(friends, colleagues)
                                                                  .build();
    
    public AddressBook book;

    public TypicalTestData() {
        book = new AddressBook();
        book.addPerson(alice);
        book.addPerson(benson);
        book.addPerson(charlie);
        book.addPerson(dan);
        book.addPerson(elizabeth);
        book.addTag(colleagues);
        book.addTag(friends);
        book.addTag(family);
    }

    public Person[] getTestData() {
        return new Person[] {alice, benson, charlie, dan, elizabeth};
    }

}
