package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.TypicalPersons;

public class ModelManagerTest {

    private TypicalPersons typicalPersons = new TypicalPersons();

    @Test
    public void equals() throws Exception {
        AddressBook addressBook = new AddressBookBuilder().withPerson(typicalPersons.alice)
                .withPerson(typicalPersons.benson).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        ModelManager modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different filteredList -> returns false
        modelManager.updateFilteredPersonList(new HashSet<>(
                Arrays.asList(typicalPersons.alice.getName().fullName.split(" "))));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));
        modelManager.updateFilteredListToShowAll(); // resets modelManager to initial state for upcoming tests

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookName("differentName");
        assertTrue(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}
