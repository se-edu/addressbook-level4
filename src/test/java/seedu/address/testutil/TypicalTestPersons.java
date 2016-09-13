package seedu.address.testutil;

import seedu.address.exceptions.IllegalValueException;
import seedu.address.model.UniqueTagList;
import seedu.address.model.datatypes.AddressBook;
import seedu.address.model.person.*;

/**
 *
 */
public class TypicalTestPersons {

    public static TestPerson alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestPersons() {
        try {
            alice =  new PersonBuilder().withName("Alice Pauline").withAddress("123, Jurong West Ave 6, #08-111", true)
                    .withEmail("alice@gmail.com", true).withPhone("85355255", true)
                    .withTags("friends").build();
            benson = new PersonBuilder().withName("Benson Meier").withAddress("311, Clementi Ave 2, #02-25", true)
                    .withEmail("johnd@gmail.com", true).withPhone("98765432", true)
                    .withTags("owesMoney", "friends").build();
            carl = new PersonBuilder().withName("Carl Kurz").withPhone("95352563", false).withEmail("heinz@yahoo.com", false).withAddress("wall street", false).build();
            daniel = new PersonBuilder().withName("Daniel Meier").withPhone("87652533", false).withEmail("cornelia@google.com", false).withAddress("10th street", false).build();
            elle = new PersonBuilder().withName("Elle Meyer").withPhone("9482224", false).withEmail("werner@gmail.com", false).withAddress("michegan ave", false).build();
            fiona = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427", false).withEmail("lydia@gmail.com", false).withAddress("little tokyo", false).build();
            george = new PersonBuilder().withName("George Best").withPhone("9482442", false).withEmail("anna@google.com", false).withAddress("4th street", false).build();

            //Manually added
            hoon = new PersonBuilder().withName("Hoon Meier").withPhone("8482424", false).withEmail("stefan@mail.com", false).withAddress("little india", false).build();
            ida = new PersonBuilder().withName("Ida Mueller").withPhone("8482131", false).withEmail("hans@google.com", false).withAddress("chicago ave", false).build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(AddressBook ab) {

        try {
            ab.addPerson(new Person(alice));
            ab.addPerson(new Person(benson));
            ab.addPerson(new Person(carl));
            ab.addPerson(new Person(daniel));
            ab.addPerson(new Person(elle));
            ab.addPerson(new Person(fiona));
            ab.addPerson(new Person(george));
        } catch (UniquePersonList.DuplicatePersonException e) {
            assert false : "not possible";
        }
    }
}
