package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static seedu.address.testutil.TypicalPersons.getTypicalContactList;

import java.nio.file.Path;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.ContactList;
import seedu.address.model.ReadOnlyContactList;
import seedu.address.model.UserPrefs;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        JsonContactListStorage addressBookStorage = new JsonContactListStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        JsonTaskListStorage taskListStorage = new JsonTaskListStorage(getTempFilePath("")); // TODO
        JsonTickedTaskListStorage tickedTaskListStorage =
                new JsonTickedTaskListStorage(getTempFilePath(""));
        JsonWorkoutBookStorage workoutBookStorage = new JsonWorkoutBookStorage(getTempFilePath("")); //TODO
        JsonExpenditureListStorage expenditureListStorage = new JsonExpenditureListStorage(getTempFilePath("")); // TODO
        storageManager = new StorageManager(addressBookStorage, userPrefsStorage,
                taskListStorage, expenditureListStorage, workoutBookStorage, tickedTaskListStorage );
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.getRoot().toPath().resolve(fileName);
    }


    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void addressBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonContactListStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonContactListStorageTest} class.
         */
        ContactList original = getTypicalContactList();
        storageManager.saveAddressBook(original);
        ReadOnlyContactList retrieved = storageManager.readAddressBook().get();
        assertEquals(original, new ContactList(retrieved));
    }

    @Test
    public void getAddressBookFilePath() {
        assertNotNull(storageManager.getAddressBookFilePath());
    }

}
