package seedu.address.storage;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import seedu.address.commons.core.Config;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.testutil.TestUtil;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ConfigStorageTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readConfig_null_assertionFailure() throws DataConversionException {
        thrown.expect(AssertionError.class);
        readConfig(null);
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
        String configFilePath = addToSandboxPathIfNotNull(configFileInSandbox);
        return new ConfigStorage().readConfig(configFilePath);
    }

    @Test
    public void save_nullConfig_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        save(null, "some-file.json");
    }

    @Test
    public void save_nullFile_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        save(new Config(), null);
    }

    @Test
    public void saveConfig_allInOrder_success() throws DataConversionException, IOException {
        Config original = new Config();
        original.setAppTitle("Typical App Title");
        original.setCurrentLogLevel(Level.INFO);
        original.setPrefsFileLocation(new File("C:\\preferences.json"));
        original.setLocalDataFilePath("addressbook.xml");
        original.setAddressBookName("TypicalAddressBookName");

        String configFilePath = testFolder.getRoot() + File.separator + "temp-config.json";
        ConfigStorage configStorage = new ConfigStorage();

        //Try writing when the file doesn't exist
        configStorage.save(original, configFilePath);
        Config readBack = configStorage.readConfig(configFilePath).get();
        assertEquals(original, readBack);

        //Try saving when the file exists
        original.setAppTitle("Updated Title");
        original.setCurrentLogLevel(Level.FINE);
        configStorage.save(original, configFilePath);
        readBack = configStorage.readConfig(configFilePath).get();
        assertEquals(original, readBack);
    }

    private void save(Config config, String configFileInSandbox) throws IOException {
        String configFilePath = addToSandboxPathIfNotNull(configFileInSandbox);
        new ConfigStorage().save(config, configFilePath);
    }

    private String addToSandboxPathIfNotNull(String configFileInSandbox) {
        return configFileInSandbox != null
                                  ? TestUtil.appendToSandboxPath(configFileInSandbox)
                                  : null;
    }


}
