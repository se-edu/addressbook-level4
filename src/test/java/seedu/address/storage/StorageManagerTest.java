package seedu.address.storage;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import seedu.address.commons.core.Config;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalTestPersons;

import static org.junit.Assert.assertEquals;

public class StorageManagerTest {
    private StorageManager storageManager;

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();


    @Before
    public void setup() {
        storageManager = new StorageManager(getTempFilePath("ab"), getTempFilePath("prefs"));
    }

    @Test
    public void configReadSave() throws Exception {
        Config original = new Config();
        original.setAppTitle("Some App Title");
        String tempFilePath = getTempFilePath("TempConfig.json");
        storageManager.saveConfig(original, tempFilePath);
        Config retrieved = storageManager.readConfig(tempFilePath).get();
        assertEquals(original, retrieved);
        //More extensive testing of Config saving/reading is done in JsonConfigStorageTest
    }

    private String getTempFilePath(String fileName) {
        return testFolder.getRoot().getPath() + fileName;
    }

    @Test
    public void prefsReadSave() throws Exception {
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(300, 600, 4, 6);
        String tempFilePath = getTempFilePath("TempPrefs.json");
        storageManager.savePrefs(original, tempFilePath);
        UserPrefs retrieved = storageManager.readPrefs(tempFilePath).get();
        assertEquals(original, retrieved);
        //More extensive testing of UserPref saving/reading is done in JsonPrefStorageTest
    }

    @Test
    public void addressBookReadSave() throws Exception {
        AddressBook original = new TypicalTestPersons().getTypicalAddressBook();
        storageManager.saveAddressBook(original);
        ReadOnlyAddressBook retrieved = storageManager.readAddressBook().get();
        assertEquals(original, new AddressBook(retrieved));
        //More extensive testing of AddressBook saving/reading is done in XmlAddressBookStorageTest
    }



}
