package seedu.address.storage;

import com.google.common.eventbus.Subscribe;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.LocalModelChangedEvent;
import seedu.address.commons.events.storage.*;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Manages storage of addressbook data in local disk.
 * Handles storage related events.
 */
public class StorageManager extends ComponentManager {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    //private Consumer<ReadOnlyAddressBook> loadedDataCallback;
    //private Supplier<ReadOnlyAddressBook> defaultDataSupplier;
    private File saveFile;
    private File userPrefsFile;


    public StorageManager(Config config) {
        super();
        //this.loadedDataCallback = loadedDataCallback;
        //this.defaultDataSupplier = defaultDataSupplier;
        this.saveFile = new File(config.getLocalDataFilePath());
        this.userPrefsFile = new File(config.getPrefsFileLocation());
    }



    /**
     * Loads the data from the local data file (based on user preferences).
     */
    public void start() {
        logger.info("Starting storage manager.");
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

    /**
     * Raises FileSavingExceptionEvent if there was an error during saving or data conversion.
     */
    public void savePrefs(UserPrefs prefs) {
        try {
            savePrefs(prefs, userPrefsFile.getPath());
        } catch (IOException e) {
            raise(new FileSavingExceptionEvent(e, userPrefsFile));
        }
    }

    /**
     * Raises FileSavingExceptionEvent
     */
    @Subscribe
    public void handleSavePrefsRequestEvent(SavePrefsRequestEvent spre) {
        logger.info("Save prefs request received: " + spre.prefs);
        savePrefs(spre.prefs);
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

    /**
     * Creates the file if it is missing before saving.
     * Raises FileSavingExceptionEvent if the file is not found or if there was an error during
     * saving or data conversion.
     */
    public void saveDataToFile(File file, ReadOnlyAddressBook data) {
        try {
            saveAddressBook(file, data);
        } catch (IOException e) {
            raise(new FileSavingExceptionEvent(e, file));
        }
    }

    /**
     * Saves the address book data in the file specified.
     */
    public static void saveAddressBook(File file, ReadOnlyAddressBook data) throws IOException {
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new StorageAddressBook(data));
    }


    /**
     * Raises FileSavingExceptionEvent (similar to {@link #saveDataToFile(File, ReadOnlyAddressBook)})
     */
    @Subscribe
    public void handleLocalModelChangedEvent(LocalModelChangedEvent lmce) {
        logger.info("Local data changed, saving to primary data file");
        saveDataToFile(saveFile, lmce.data);
    }

    /**
     * Raises FileSavingExceptionEvent (similar to {@link #saveDataToFile(File, ReadOnlyAddressBook)})
     */
    @Subscribe
    public void handleSaveDataRequestEvent(SaveDataRequestEvent sdre) {
        logger.info("Save data request received: " + sdre.data);
        saveDataToFile(sdre.file, sdre.data);
    }

}
