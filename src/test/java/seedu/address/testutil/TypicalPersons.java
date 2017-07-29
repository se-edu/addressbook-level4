package seedu.address.testutil;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 *
 */
public class TypicalPersons {

    public static final Index INDEX_FIRST_PERSON = Index.fromOneBased(1);
    public static final Index INDEX_SECOND_PERSON = Index.fromOneBased(2);
    public static final Index INDEX_THIRD_PERSON = Index.fromOneBased(3);

    public static final ReadOnlyPerson ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE, HOON, IDA;

    static {
        try {
            ALICE = new PersonBuilder().withName("Alice Pauline")
                    .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
                    .withPhone("85355255")
                    .withTags("friends").build();
            BENSON = new PersonBuilder().withName("Benson Meier").withAddress("311, Clementi Ave 2, #02-25")
                    .withEmail("johnd@example.com").withPhone("98765432")
                    .withTags("owesMoney", "friends").build();
            CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
                    .withEmail("heinz@example.com").withAddress("wall street").build();
            DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
                    .withEmail("cornelia@example.com").withAddress("10th street").build();
            ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
                    .withEmail("werner@example.com").withAddress("michegan ave").build();
            FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
                    .withEmail("lydia@example.com").withAddress("little tokyo").build();
            GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
                    .withEmail("anna@example.com").withAddress("4th street").withRemark("Likes to swim").build();

            // Manually added
            HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
                    .withEmail("stefan@example.com").withAddress("little india").build();
            IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
                    .withEmail("hans@example.com").withAddress("chicago ave").build();
        } catch (IllegalValueException e) {
            throw new AssertionError("Sample data cannot be invalid", e);
        }
    }

    public static final ReadOnlyPerson[] TYPICAL_PERSONS =
            new ReadOnlyPerson[]{ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE};

    private TypicalPersons() {} // prevents instantiation

    /**
     * Loads the {@code ab} with all the typical persons.
     */
    public static void loadAddressBookWithSampleData(AddressBook ab) {
        for (ReadOnlyPerson person : TYPICAL_PERSONS) {
            try {
                ab.addPerson(new Person(person));
            } catch (DuplicatePersonException e) {
                assert false : "not possible";
            }
        }
    }

    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
