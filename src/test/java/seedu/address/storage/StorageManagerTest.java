package seedu.address.storage;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalTestPersons;

import static org.junit.Assert.assertEquals;

public class StorageManagerTest {
    private Storage storage;

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();


    @Before
    public void setup() {
        storage = new StorageManager(getTempFilePath("ab"), getTempFilePath("prefs"));
    }


    private String getTempFilePath(String fileName) {
        return testFolder.getRoot().getPath() + fileName;
    }

    @Test
    public void prefsReadSave() throws Exception {
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(300, 600, 4, 6);
        storage.saveUserPrefs(original);
        UserPrefs retrieved = storage.readUserPrefs().get();
        assertEquals(original, retrieved);
        //More extensive testing of UserPref saving/reading is done in JsonUserPrefStorageTest
    }

    @Test
    public void addressBookReadSave() throws Exception {
        AddressBook original = new TypicalTestPersons().getTypicalAddressBook();
        storage.saveAddressBook(original);
        ReadOnlyAddressBook retrieved = storage.readAddressBook().get();
        assertEquals(original, new AddressBook(retrieved));
        //More extensive testing of AddressBook saving/reading is done in XmlAddressBookStorageTest
    }


}
