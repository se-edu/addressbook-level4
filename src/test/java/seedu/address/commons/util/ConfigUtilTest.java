package seedu.address.commons.util;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import seedu.address.commons.core.Config;
import seedu.address.commons.exceptions.DataConversionException;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ConfigUtilTest {

    private static String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/ConfigUtilTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void read_null_assertionFailure() throws DataConversionException {
        thrown.expect(AssertionError.class);
        read(null);
    }

    @Test
    public void read_missingFile_emptyResult() throws DataConversionException {
        assertFalse(read("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJasonFormat_exceptionThrown() throws DataConversionException {

        thrown.expect(DataConversionException.class);
        read("NotJasonFormatConfig.json");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void read_fileInOrder_successfullyRead() throws DataConversionException {

        Config expected = getTypicalConfig();

        Config actual = read("TypicalConfig.json").get();
        assertEquals(expected, actual);
    }

    @Test
    public void read_valuesMissingFromFile_defaultValuesUsed() throws DataConversionException {
        Config actual = read("EmptyConfig.json").get();
        assertEquals(new Config(), actual);
    }

    @Test
    public void read_extraValuesInFile_extraValuesIgnored() throws DataConversionException {
        Config expected = getTypicalConfig();
        Config actual = read("ExtraValuesConfig.json").get();

        assertEquals(expected, actual);
    }

    private Config getTypicalConfig() {
        Config config = new Config();
        config.setAppTitle("Typical App Title");
        config.setLogLevel(Level.INFO);
        config.setUserPrefsFilePath("C:\\preferences.json");
        config.setAddressBookFilePath("addressbook.xml");
        config.setAddressBookName("TypicalAddressBookName");
        return config;
    }

    private Optional<Config> read(String configFileInTestDataFolder) throws DataConversionException {
        String configFilePath = addToTestDataPathIfNotNull(configFileInTestDataFolder);
        return new ConfigUtil().readConfig(configFilePath);
    }

    @Test
    public void save_nullConfig_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        save(null, "SomeFile.json");
    }

    @Test
    public void save_nullFile_assertionFailure() throws IOException {
        thrown.expect(AssertionError.class);
        save(new Config(), null);
    }

    @Test
    public void saveConfig_allInOrder_success() throws DataConversionException, IOException {
        Config original = getTypicalConfig();

        String configFilePath = testFolder.getRoot() + File.separator + "TempConfig.json";
        ConfigUtil configStorage = new ConfigUtil();

        //Try writing when the file doesn't exist
        configStorage.saveConfig(original, configFilePath);
        Config readBack = configStorage.readConfig(configFilePath).get();
        assertEquals(original, readBack);

        //Try saving when the file exists
        original.setAppTitle("Updated Title");
        original.setLogLevel(Level.FINE);
        configStorage.saveConfig(original, configFilePath);
        readBack = configStorage.readConfig(configFilePath).get();
        assertEquals(original, readBack);
    }

    private void save(Config config, String configFileInTestDataFolder) throws IOException {
        String configFilePath = addToTestDataPathIfNotNull(configFileInTestDataFolder);
        new ConfigUtil().saveConfig(config, configFilePath);
    }

    private String addToTestDataPathIfNotNull(String configFileInTestDataFolder) {
        return configFileInTestDataFolder != null
                                  ? TEST_DATA_FOLDER + configFileInTestDataFolder
                                  : null;
    }


}
