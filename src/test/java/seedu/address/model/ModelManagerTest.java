package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.TypicalPersons;

public class ModelManagerTest {

    private TypicalPersons typicalPersons = new TypicalPersons();

    @Test
    public void equals() throws Exception {
        AddressBook addressBook = new AddressBookBuilder().withPerson(typicalPersons.alice)
                .withPerson(typicalPersons.benson).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(addressBook, userPrefs);

        // same values -> returns true
        ModelManager modelManagerCopy = new ModelManager(modelManager.getAddressBook(), userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(new AddressBook(), userPrefs)));

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookName("differentName");
        assertTrue(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}
