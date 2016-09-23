package seedu.address.storage;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import seedu.address.commons.core.Config;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.UserPrefs;

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

}
