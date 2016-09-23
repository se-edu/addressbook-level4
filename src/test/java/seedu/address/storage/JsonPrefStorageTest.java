package seedu.address.storage;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.UserPrefs;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class JsonPrefStorageTest {

    private static String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/JsonPrefStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void read_nullFilePath_assertionFailure() throws DataConversionException {
        thrown.expect(AssertionError.class);
        readPrefs(null);
    }

    private Optional<UserPrefs> readPrefs(String prefsFileInTestDataFolder) throws DataConversionException {
        String prefsFilePath = addToTestDataPathIfNotNull(prefsFileInTestDataFolder);
        return new JsonPrefStorage().readPrefs(prefsFilePath);
    }

    @Test
    public void read_missingFile_emptyResult() throws DataConversionException {
        assertFalse(readPrefs("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJasonFormat_exceptionThrown() throws DataConversionException {

        thrown.expect(DataConversionException.class);
        readPrefs("NotJsonFormatPrefs.json");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_fileInOrder_successfullyRead() throws DataConversionException {
        UserPrefs expected = new UserPrefs();
        expected.setGuiSettings(1000, 500, 300, 100);
        UserPrefs actual = readPrefs("TypicalUserPref.json").get();
        assertEquals(expected, actual);
    }

    @Test
    public void read_valuesMissingFromFile_defaultValuesUsed() throws DataConversionException {
        UserPrefs actual = readPrefs("EmptyPrefs.json").get();
        assertEquals(new UserPrefs(), actual);
    }

    @Test
    public void read_extraValuesInFile_extraValuesIgnored() throws DataConversionException {
        UserPrefs expected = new UserPrefs();
        expected.setGuiSettings(1000, 500, 300, 100);
        UserPrefs actual = readPrefs("ExtraValuesPref.json").get();

        assertEquals(expected, actual);
    }

    @Test
    public void save_nullPrefs_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        savePrefs(null, "SomeFile.json");
    }

    @Test
    public void save_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        savePrefs(new UserPrefs(), null);
    }

    private void savePrefs(UserPrefs userPrefs, String prefsFileInTestDataFolder) throws IOException {
        new JsonPrefStorage().savePrefs(userPrefs, addToTestDataPathIfNotNull(prefsFileInTestDataFolder));
    }

    @Test
    public void saveConfig_allInOrder_success() throws DataConversionException, IOException {

        UserPrefs original = new UserPrefs();
        original.setGuiSettings(1200, 200, 0, 2);

        String pefsFilePath = testFolder.getRoot() + File.separator + "TempPrefs.json";
        JsonPrefStorage jsonPrefStorage = new JsonPrefStorage();

        //Try writing when the file doesn't exist
        jsonPrefStorage.savePrefs(original, pefsFilePath);
        UserPrefs readBack = jsonPrefStorage.readPrefs(pefsFilePath).get();
        assertEquals(original, readBack);

        //Try saving when the file exists
        original.setGuiSettings(5, 5, 5, 5);
        jsonPrefStorage.savePrefs(original, pefsFilePath);
        readBack = jsonPrefStorage.readPrefs(pefsFilePath).get();
        assertEquals(original, readBack);
    }

}
