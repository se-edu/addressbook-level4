package seedu.address.testutil;

import seedu.address.exceptions.IllegalValueException;
import seedu.address.model.datatypes.AddressBook;
import seedu.address.model.person.UniquePersonList;

/**
 *
 */
public class TypicalTestData {

    public TestPerson alice;

    public TestPerson benson;

    public TypicalTestData() {
        try {
            alice =  new PersonBuilder().withName("Alice Pauline").withAddress("123, Jurong West Ave 6, #08-111", true)
                    .withEmail("alice@gmail.com", true).withPhone("85355255", true)
                    .withTags("friends").build();
            benson = new PersonBuilder().withName("Benson Doe").withAddress("311, Clementi Ave 2, #02-25", true)
                    .withEmail("johnd@gmail.com", true).withPhone("98765432", true)
                    .withTags("owesMoney", "friends").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "impossible";
        }
    }


}
