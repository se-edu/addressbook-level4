package seedu.address.storage;

import com.google.common.eventbus.Subscribe;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.events.model.LocalModelChangedEvent;
import seedu.address.commons.events.storage.*;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.core.ComponentManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.logging.Logger;

/**
 * Manages storage of addressbook data in local disk.
 * Handles storage related events.
 */
public class StorageManager extends ComponentManager {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private Consumer<ReadOnlyAddressBook> loadedDataCallback;
    private Supplier<ReadOnlyAddressBook> defaultDataSupplier;
    private File saveFile;
    private File userPrefsFile;


    public StorageManager(Consumer<ReadOnlyAddressBook> loadedDataCallback,
                          Supplier<ReadOnlyAddressBook> defaultDataSupplier, Config config) {
        super();
        this.loadedDataCallback = loadedDataCallback;
        this.defaultDataSupplier = defaultDataSupplier;
        this.saveFile = new File(config.getLocalDataFilePath());
        this.userPrefsFile = config.getPrefsFileLocation();
    }

    //TODO: add comment
    public static Optional<Config> readConfig(String configFilePath) throws DataConversionException {
        return new JsonConfigStorage().readConfig(configFilePath);
    }

    //TODO: add comment
    public static void saveConfig(Config config, String configFilePath) throws IOException{
        new JsonConfigStorage().saveConfig(config, configFilePath);
    }

    /**
     *  Raises a {@link FileOpeningExceptionEvent} if there was any problem in reading data from the file
     *  or if the file is not in the correct format.
     */
    @Subscribe
    public void handleLoadDataRequestEvent(LoadDataRequestEvent ldre) {
        File dataFile = ldre.file;
        logger.info("Handling load data request received: " + dataFile);
        loadDataFile(dataFile);
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

    /**
     * Creates the file if it is missing before saving.
     * Raises FileSavingExceptionEvent if the file is not found or if there was an error during
     * saving or data conversion.
     */
    public void saveDataToFile(File file, ReadOnlyAddressBook data) {
        try {
            saveAddressBook(file, data);
        } catch (IOException | DataConversionException e) {
            raise(new FileSavingExceptionEvent(e, file));
        }
    }

    /**
     * Saves the address book data in the file specified.
     */
    public static void saveAddressBook(File file, ReadOnlyAddressBook data) throws IOException,
                                                                                   DataConversionException {
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new StorageAddressBook(data));
    }

    /**
     * Raises FileSavingExceptionEvent
     */
    @Subscribe
    public void handleSavePrefsRequestEvent(SavePrefsRequestEvent spre) {
        logger.info("Save prefs request received: " + spre.prefs);
        savePrefs(spre.prefs);
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

    //TODO: add comment
    public static Optional<UserPrefs> readPrefs(String prefsFilePath) throws DataConversionException {
        return new JsonPrefStorage().readPrefs(prefsFilePath);
    }

    //TODO: add comment
    public static void savePrefs(UserPrefs prefs, String prefsFilePath) throws IOException{
        new JsonPrefStorage().savePrefs(prefs, prefsFilePath);
    }

    /**
     * Loads the data from the local data file (based on user preferences).
     */
    public void start() {
        logger.info("Starting storage manager.");
        initializeDataFile(saveFile);
    }

    protected void initializeDataFile(File dataFile) {
        try {
            loadDataFromFile(dataFile);
        } catch (FileNotFoundException e) {
            logger.fine("File " + dataFile + " not found, attempting to create file with default data");
            try {
                saveAddressBook(saveFile, defaultDataSupplier.get());
            } catch (DataConversionException | IOException e1) {
                logger.severe("Unable to initialize local data file with default data.");
                assert false : "Unable to initialize local data file with default data.";
            }
        }
    }

    protected void loadDataFile(File dataFile) {
        try {
            loadDataFromFile(dataFile);
        } catch (FileNotFoundException e) {
            logger.fine("File not found: " + dataFile);
            raise(new FileOpeningExceptionEvent(e, dataFile));
        }
    }

    protected void loadDataFromFile(File dataFile) throws FileNotFoundException {
        try {
            logger.fine("Attempting to load data from file: " + dataFile);
            loadedDataCallback.accept(getData());
        } catch (DataConversionException e) {
            logger.fine("Error loading data from file: " + e);
            raise(new FileOpeningExceptionEvent(e, dataFile));
        }
    }

    public ReadOnlyAddressBook getData() throws FileNotFoundException, DataConversionException {
        logger.fine("Attempting to read data from file: " + saveFile);
        return XmlFileStorage.loadDataFromSaveFile(saveFile);
    }
}
