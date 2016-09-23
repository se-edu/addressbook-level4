package seedu.address.storage;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.UserPrefs;

import java.util.Optional;

import static org.junit.Assert.assertFalse;

public class JsonPrefStorageTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void read_nullFilePath_assertionFailure() throws DataConversionException {
        thrown.expect(AssertionError.class);
        readPrefs(null);
    }

    private Optional<UserPrefs> readPrefs(String prefsFilePath) {
        return new JsonPrefStorage().readPrefs(prefsFilePath);
    }

    @Test
    public void read_missingFile_emptyResult() throws DataConversionException {
        assertFalse(readPrefs("NonExistentFile.json").isPresent());
    }

}
