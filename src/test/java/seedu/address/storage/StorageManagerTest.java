package seedu.address.storage;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.commons.core.Config;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.SerializableTestClass;
import seedu.address.testutil.TestUtil;

import java.io.File;
import java.io.IOException;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

public class StorageManagerTest {
    private static final File SERIALIZATION_FILE = new File(TestUtil.appendToSandboxPath("serialize.json"));
    private static final String TESTING_DATA_FILE_PATH = TestUtil.appendToSandboxPath("dummyAddressBook.xml");
    private static final File TESTING_DATA_FILE = new File(TESTING_DATA_FILE_PATH);
    private Config config;

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Before
    public void before() throws IOException, DataConversionException {
        config = new Config();
        config.setLocalDataFilePath(TESTING_DATA_FILE_PATH);
    }

    @After
    public void after() {
        TESTING_DATA_FILE.delete();
    }


    @Test
    public void loadDataFromFile() {} // This is not implemented as it requires reflection

    @Test
    public void testHandleLoadDataRequestEvent() {
        //Not tested, can't figure out a proper way to load new data and check if the model is updated.
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
    public void readConfig() throws DataConversionException {
        Config config = StorageManager.readConfig(JsonConfigStorageTest.TYPICAL_CONFIG_FILE).get();
        assertNotNull(config);
        //More extensive testing of this method is done in JsonConfigStorageTest
    }

    @Test
    public void saveConfig(){
        //TODO
    }

    @Test
    public void readPrefs() throws DataConversionException {
        UserPrefs prefs = StorageManager.readPrefs(JsonPrefStorageTest.TYPICAL_PREFS_FILE).get();
        assertNotNull(prefs);
        //More extensive testing of this method is done in JsonPrefStorageTest
    }

    @Test
    public void savePrefs(){
        //TODO
    }




    //TODO: finish the rest of the public methods in StorageManager
}
