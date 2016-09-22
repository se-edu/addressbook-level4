package seedu.address.storage;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.events.model.LocalModelChangedEvent;
import seedu.address.commons.events.storage.SaveDataRequestEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.DuplicateTagException;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.UniquePersonList;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.SerializableTestClass;
import seedu.address.testutil.TestUtil;
import seedu.address.commons.core.Config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class StorageManagerTest {
    private static final File SERIALIZATION_FILE = new File(TestUtil.appendToSandboxPath("serialize.json"));
    private static final String DEFAULT_CONFIG_FILE = TestUtil.appendToSandboxPath("config.json");
    private static final String DEFAULT_PREF_FILE = TestUtil.appendToSandboxPath("preferences.json");
    private static final String TESTING_DATA_FILE_PATH = TestUtil.appendToSandboxPath("dummyAddressBook.xml");
    private static final File TESTING_DATA_FILE = new File(TESTING_DATA_FILE_PATH);
    private static final String TEMP_SAVE_FILE_PATH = TestUtil.appendToSandboxPath("tempAddressBook.xml");
    private static final File TEMP_SAVE_FILE = new File(TEMP_SAVE_FILE_PATH);
    private StorageManager storageManager;
    private ModelManager modelManager;
    private Config config;
    private UserPrefs prefs;

    @Before
    public void before() throws IOException, DataConversionException {
        StorageManager.saveAddressBook(TESTING_DATA_FILE, new AddressBook());
        config = StorageManager.getConfig(DEFAULT_CONFIG_FILE);
        config.setLocalDataFilePath(TESTING_DATA_FILE_PATH);
        prefs = StorageManager.getUserPrefs(new File(DEFAULT_PREF_FILE));
        modelManager = new ModelManager();
        storageManager = new StorageManager(modelManager::resetData, AddressBook::getEmptyAddressBook,
                config, prefs);
    }

    @After
    public void after() {
        TESTING_DATA_FILE.delete();
    }

    @Test
    public void recreateFile() {} // This is not implemented as it requires reflection
    @Test
    public void createAndWriteToConfigFile() {} // This is not implemented as it requires reflection
    @Test
    public void deleteConfigFileIfExists() {} // This is not implemented as it requires reflection
    @Test
    public void readFromConfigFile() {} // This is not implemented as it requires reflection

    /**
     * This is not implemented due to the need to mock static methods of StorageManager which will prevent some
     * real methods to be called, hence leaving other unrelated methods untested
     */
    @Test
    public void savePrefsToFile_correspondingMethodCalled() {}

    @Test
    public void loadDataFromFile() {} // This is not implemented as it requires reflection

    @Test
    public void testHandleLoadDataRequestEvent() {
        //Not tested, can't figure out a proper way to load new data and check if the model is updated.
    }

    @Test
    public void testGetConfig() {
        //This is called in the before() method. Already tested indirectly.
    }

    @Test
    public void serializeObjectToJsonFile_noExceptionThrown() throws IOException {
        SerializableTestClass serializableTestClass = new SerializableTestClass();
        serializableTestClass.setTestValues();

        FileUtil.serializeObjectToJsonFile(SERIALIZATION_FILE, serializableTestClass);

        assertEquals(FileUtil.readFromFile(SERIALIZATION_FILE), SerializableTestClass.JSON_STRING_REPRESENTATION);
    }

    @Test
    public void deserializeObjectFromJsonFile_noExceptionThrown() throws IOException {
        FileUtil.writeToFile(SERIALIZATION_FILE, SerializableTestClass.JSON_STRING_REPRESENTATION);

        SerializableTestClass serializableTestClass = FileUtil
                .deserializeObjectFromJsonFile(SERIALIZATION_FILE, SerializableTestClass.class);

        assertEquals(serializableTestClass.getName(), SerializableTestClass.getNameTestValue());
        assertEquals(serializableTestClass.getListOfLocalDateTimes(), SerializableTestClass.getListTestValues());
        assertEquals(serializableTestClass.getMapOfIntegerToString(), SerializableTestClass.getHashMapTestValues());
    }

    @Test
    public void saveDataToFile() throws FileNotFoundException, DataConversionException, UniquePersonList.DuplicatePersonException {
        ReadOnlyAddressBook oldAb = new AddressBook(storageManager.getData());
        AddressBook editedAb = new AddressBookBuilder(new AddressBook(storageManager.getData())).withPerson(TestUtil.generateSamplePersonData().get(1)).build();
        assertNotEquals(editedAb, oldAb);

        storageManager.saveDataToFile(TEMP_SAVE_FILE, editedAb);

        ReadOnlyAddressBook savedAddressbook = new AddressBook(XmlFileStorage.loadDataFromSaveFile(TEMP_SAVE_FILE));

        assertEquals(editedAb.getPersonList(), savedAddressbook.getPersonList());
        assertEquals(editedAb.getTagList(), savedAddressbook.getTagList());
    }

    @Test
    public void testConfig_openConfigFile_exist() {
        Config config = StorageManager.getConfig(DEFAULT_CONFIG_FILE);
        assertNotNull(config);
    }

    @Test
    public void testHandleLocalModelChangedEvent() throws DuplicateTagException, InterruptedException, FileNotFoundException, DataConversionException, UniquePersonList.DuplicatePersonException {
        ReadOnlyAddressBook oldAb = new AddressBook(storageManager.getData());
        AddressBook editedAb = new AddressBookBuilder(new AddressBook(storageManager.getData())).withPerson(TestUtil.generateSamplePersonData().get(0)).build();
        assertNotEquals(editedAb, oldAb);

        storageManager.handleLocalModelChangedEvent(new LocalModelChangedEvent(editedAb));
        AddressBook newAb = new AddressBook(storageManager.getData());

        assertEquals(editedAb.getPersonList(), newAb.getPersonList());
        assertEquals(editedAb.getTagList(), newAb.getTagList());
    }

    @Test
    public void testHandleSaveDataRequestEvent() throws FileNotFoundException, DataConversionException {
        storageManager.handleSaveDataRequestEvent(new SaveDataRequestEvent(TEMP_SAVE_FILE, storageManager.getData()));
        StorageAddressBook storageAddressBook = XmlFileStorage.loadDataFromSaveFile(TEMP_SAVE_FILE);
        Assert.assertEquals(storageAddressBook.getPersonList(), storageManager.getData().getPersonList());
        Assert.assertEquals(storageAddressBook.getTagList(), storageManager.getData().getTagList());
    }

    //TODO: finish the rest of the public methods in StorageManager
}
