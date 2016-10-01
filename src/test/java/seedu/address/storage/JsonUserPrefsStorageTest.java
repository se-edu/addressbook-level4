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

public class JsonUserPrefsStorageTest {

    private static String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/JsonUserPrefsStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readUserPrefs_nullFilePath_assertionFailure() throws DataConversionException {
        thrown.expect(AssertionError.class);
        readUserPrefs(null);
    }

    private Optional<UserPrefs> readUserPrefs(String userPrefsFileInTestDataFolder) throws DataConversionException {
        String prefsFilePath = addToTestDataPathIfNotNull(userPrefsFileInTestDataFolder);
        return new JsonUserPrefsStorage(prefsFilePath).readUserPrefs(prefsFilePath);
    }

    @Test
    public void readUserPrefs_missingFile_emptyResult() throws DataConversionException {
        assertFalse(readUserPrefs("NonExistentFile.json").isPresent());
    }

    @Test
    public void readUserPrefs_notJasonFormat_exceptionThrown() throws DataConversionException {

        thrown.expect(DataConversionException.class);
        readUserPrefs("NotJsonFormatUserPrefs.json");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    private String addToTestDataPathIfNotNull(String userPrefsFileInTestDataFolder) {
        return userPrefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + userPrefsFileInTestDataFolder
                : null;
    }

    @Test
    public void readUserPrefs_fileInOrder_successfullyRead() throws DataConversionException {
        UserPrefs expected = new UserPrefs();
        expected.setGuiSettings(1000, 500, 300, 100);
        UserPrefs actual = readUserPrefs("TypicalUserPref.json").get();
        assertEquals(expected, actual);
    }

    @Test
    public void readUserPrefs_valuesMissingFromFile_defaultValuesUsed() throws DataConversionException {
        UserPrefs actual = readUserPrefs("EmptyUserPrefs.json").get();
        assertEquals(new UserPrefs(), actual);
    }

    @Test
    public void readUserPrefs_extraValuesInFile_extraValuesIgnored() throws DataConversionException {
        UserPrefs expected = new UserPrefs();
        expected.setGuiSettings(1000, 500, 300, 100);
        UserPrefs actual = readUserPrefs("ExtraValuesUserPref.json").get();

        assertEquals(expected, actual);
    }

    @Test
    public void savePrefs_nullPrefs_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveUserPrefs(null, "SomeFile.json");
    }

    @Test
    public void saveUserPrefs_nullFilePath_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        saveUserPrefs(new UserPrefs(), null);
    }

    private void saveUserPrefs(UserPrefs userPrefs, String prefsFileInTestDataFolder) throws IOException {
        new JsonUserPrefsStorage(addToTestDataPathIfNotNull(prefsFileInTestDataFolder)).saveUserPrefs(userPrefs);
    }

    @Test
    public void saveUserPrefs_allInOrder_success() throws DataConversionException, IOException {

        UserPrefs original = new UserPrefs();
        original.setGuiSettings(1200, 200, 0, 2);

        String pefsFilePath = testFolder.getRoot() + File.separator + "TempPrefs.json";
        JsonUserPrefsStorage jsonUserPrefsStorage = new JsonUserPrefsStorage(pefsFilePath);

        //Try writing when the file doesn't exist
        jsonUserPrefsStorage.saveUserPrefs(original);
        UserPrefs readBack = jsonUserPrefsStorage.readUserPrefs().get();
        assertEquals(original, readBack);

        //Try saving when the file exists
        original.setGuiSettings(5, 5, 5, 5);
        jsonUserPrefsStorage.saveUserPrefs(original);
        readBack = jsonUserPrefsStorage.readUserPrefs().get();
        assertEquals(original, readBack);
    }

}
