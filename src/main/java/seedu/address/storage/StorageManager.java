package seedu.address.storage;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Manages storage of addressbook data in local disk.
 * Handles storage related events.
 */
public class StorageManager extends ComponentManager {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private File saveFile;
    private File userPrefsFile;


    public StorageManager(String addressBookFilePath, String preferencesFilePath) {
        super();
        this.saveFile = new File(addressBookFilePath);
        this.userPrefsFile = new File(preferencesFilePath);
    }


    //=================== Config methods ========================

    //TODO: add comment
    public static Optional<Config> readConfig(String configFilePath) throws DataConversionException {
        return new JsonConfigStorage().readConfig(configFilePath);
    }

    //TODO: add comment
    public static void saveConfig(Config config, String configFilePath) throws IOException{
        new JsonConfigStorage().saveConfig(config, configFilePath);
    }


    // ================ Prefs methods ==============================

    //TODO: add comment
    public static Optional<UserPrefs> readPrefs(String prefsFilePath) throws DataConversionException {
        return new JsonPrefStorage().readPrefs(prefsFilePath);
    }

    //TODO: add comment
    public static void savePrefs(UserPrefs prefs, String prefsFilePath) throws IOException{
        new JsonPrefStorage().savePrefs(prefs, prefsFilePath);
    }

    //TODO: add comment
    public void savePrefs(UserPrefs prefs) throws IOException {
        savePrefs(prefs, userPrefsFile.getPath());
    }

    // ============== AddressBook method ============================

    //TODO: add comment
    public Optional<ReadOnlyAddressBook> getData() throws DataConversionException {
        logger.fine("Attempting to read data from file: " + saveFile);

        return new XmlAddressBookStorage().readAddressBook(saveFile.getPath());
    }

    //TODO: add comment
    public void saveData(ReadOnlyAddressBook addressBook) throws IOException {
        new XmlAddressBookStorage().saveAddressBook(addressBook, saveFile.getPath());
    }





}
