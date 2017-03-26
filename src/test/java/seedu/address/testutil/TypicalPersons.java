package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 *
 */
public class TypicalPersons {

    public final Person alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalPersons() {
        alice = buildPerson("Alice Pauline", "123, Jurong West Ave 6, #08-111", "alice@example.com",
                "85355255", "friends");
        benson = buildPerson("Benson Meier", "311, Clementi Ave 2, #02-25", "johnd@example.com",
                "98765432", "owesMoney", "friends");
        carl = buildPerson("Carl Kurz", "wall street", "heinz@example.com", "95352563");
        daniel = buildPerson("Daniel Meier", "10th street", "cornelia@example.com", "87652533");
        elle = buildPerson("Elle Meyer", "michegan ave", "werner@example.com", "9482224");
        fiona = buildPerson("Fiona Kunz", "little tokyo", "lydia@example.com", "9482427");
        george = buildPerson("George Best", "4th street", "anna@example.com", "9482442");

        // Manually added
        hoon = buildPerson("Hoon Meier", "little india", "stefan@example.com", "8482424");
        ida = buildPerson("Ida Mueller", "chicago ave", "hans@example.com", "8482131");
    }

    public static void loadAddressBookWithSampleData(AddressBook ab) {
        for (Person person : new TypicalPersons().getTypicalPersons()) {
            try {
                ab.addPerson(new Person(person));
            } catch (UniquePersonList.DuplicatePersonException e) {
                assert false : "not possible";
            }
        }
    }

    public Person[] getTypicalPersons() {
        return new Person[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        loadAddressBookWithSampleData(ab);
        return ab;
    }

    /**
     * Internal person builder to handle IllegalValueException
     */
    private Person buildPerson(String name, String address, String email, String phone, String... tags) {
        try {
            return new PersonBuilder().withName(name).withAddress(address).withEmail(email).withPhone(phone)
                    .withTags(tags).build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
        return null;
    }
}
