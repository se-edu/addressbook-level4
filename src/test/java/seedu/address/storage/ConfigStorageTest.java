package seedu.address.storage;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.commons.core.Config;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.testutil.TestUtil;

import java.io.File;
import java.util.Optional;
import java.util.logging.Level;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ConfigStorageTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void readConfig_null_assertionFailure() throws DataConversionException {
        thrown.expect(AssertionError.class);
        new ConfigStorage().readConfig(null);
    }

    @Test
    public void readConfig_missingFile_emptyResult() throws DataConversionException {
        assertFalse(readConfig("non-existent-file.json").isPresent());
    }

    @Test
    public void readConfig_corruptedFile_exceptionThrown() throws DataConversionException {

        thrown.expect(DataConversionException.class);
        readConfig("corrupted-config.json");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readConfig_fileInOrder_successfullyRead() throws DataConversionException {

        Config expected = new Config();
        expected.setAppTitle("Typical App Title");
        expected.setCurrentLogLevel(Level.INFO);
        expected.setPrefsFileLocation(new File("C:\\preferences.json"));
        expected.setLocalDataFilePath("addressbook.xml");
        expected.setAddressBookName("TypicalAddressBookName");

        Config actual = readConfig("typical-config.json").get();
        assertEquals(expected, actual);
    }

    private Optional<Config> readConfig(String configFileInSandbox) throws DataConversionException {
        String configFilePath = TestUtil.appendToSandboxPath(configFileInSandbox);
        return new ConfigStorage().readConfig(configFilePath);
    }

}
