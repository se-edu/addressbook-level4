package seedu.address.storage;

import seedu.address.events.model.LocalModelChangedEvent;
import seedu.address.events.storage.*;
import seedu.address.exceptions.DataConversionException;
import seedu.address.main.ComponentManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.datatypes.ReadOnlyAddressBook;
import seedu.address.util.AppLogger;
import seedu.address.util.Config;
import seedu.address.util.LoggerManager;
import com.google.common.eventbus.Subscribe;
import seedu.address.commons.FileUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Manages storage of addressbook data in local disk.
 * Handles storage related events.
 */
public class StorageManager extends ComponentManager {

    private static final AppLogger logger = LoggerManager.getLogger(StorageManager.class);
    private static final String DEFAULT_CONFIG_FILE = "config.json";
    private final Consumer<ReadOnlyAddressBook> loadedDataCallback;
    private final Supplier<ReadOnlyAddressBook> defaultDataSupplier;
    private UserPrefs userPrefs;
    private File saveFile;
    private File userPrefsFile;


    public StorageManager(Consumer<ReadOnlyAddressBook> loadedDataCallback,
                          Supplier<ReadOnlyAddressBook> defaultDataSupplier, Config config, UserPrefs userPrefs) {
        super();
        this.loadedDataCallback = loadedDataCallback;
        this.defaultDataSupplier = defaultDataSupplier;
        this.saveFile = new File(config.getLocalDataFilePath());
        this.userPrefsFile = config.getPrefsFileLocation();
        this.userPrefs = userPrefs;
    }

    private static File getConfigFile(String configFilePath) {
        if (configFilePath == null) return new File(DEFAULT_CONFIG_FILE);
        return new File(configFilePath);
    }

    public static Config getConfig(String configFilePath) {
        File configFile = getConfigFile(configFilePath);

        Config config;
        if (configFile.exists()) {
            logger.info("Config file {} found, attempting to read.", configFile);
            config = readFromConfigFile(configFile);
        } else {
            logger.info("Config file {} not found, using default config.", configFile);
            config = new Config();
        }
        // Recreate the file so that any missing fields will be restored
        recreateFile(configFile, config);
        return config;
    }

    private static void recreateFile(File configFile, Config config) {
        if (!deleteConfigFileIfExists(configFile)) return;
        createAndWriteToConfigFile(configFile, config);
    }

    private static void createAndWriteToConfigFile(File configFile, Config config) {
        try {
            FileUtil.serializeObjectToJsonFile(configFile, config);
        } catch (IOException e) {
            logger.warn("Error writing to config file {}.", configFile);
        }
    }

    /**
     * Attempts to delete configFile if it exists
     *
     * @param configFile
     * @return false if exception is thrown
     */
    private static boolean deleteConfigFileIfExists(File configFile) {
        if (!FileUtil.isFileExists(configFile)) return true;

        try {
            FileUtil.deleteFile(configFile);
            return true;
        } catch (IOException e) {
            logger.warn("Error removing previous config file {}.", configFile);
            return false;
        }
    }

    /**
     * Attempts to read config values from the given file
     *
     * @param configFile
     * @return default config object if reading fails
     */
    private static Config readFromConfigFile(File configFile) {
        try {
            return FileUtil.deserializeObjectFromJsonFile(configFile, Config.class);
        } catch (IOException e) {
            logger.warn("Error reading from config file {}: {}", configFile, e);
            return new Config();
        }
    }

    /**
     *  Raises a {@link FileOpeningExceptionEvent} if there was any problem in reading data from the file
     *  or if the file is not in the correct format.
     */
    @Subscribe
    public void handleLoadDataRequestEvent(LoadDataRequestEvent ldre) {
        File dataFile = ldre.file;
        logger.info("Handling load data request received: {}", dataFile);
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
        logger.info("Save data request received: {}", sdre.data);
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
        logger.info("Save prefs request received: {}", spre.prefs);
        savePrefsToFile(spre.prefs);
    }

    /**
     * Raises FileSavingExceptionEvent if there was an error during saving or data conversion.
     */
    public void savePrefsToFile(UserPrefs prefs) {
        try {
            FileUtil.serializeObjectToJsonFile(userPrefsFile, prefs);
        } catch (IOException e) {
            raise(new FileSavingExceptionEvent(e, userPrefsFile));
        }
    }

    public static UserPrefs getUserPrefs(File prefsFile) {
        UserPrefs prefs = new UserPrefs();

        if (!FileUtil.isFileExists(prefsFile)) {
            return prefs;
        }

        try {
            logger.debug("Attempting to load prefs from file: {}", prefsFile);
            prefs = FileUtil.deserializeObjectFromJsonFile(prefsFile, UserPrefs.class);
        } catch (IOException e) {
            logger.debug("Error loading prefs from file: {}", e);
        }

        return prefs;
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
            logger.debug("File {} not found, attempting to create file with default data", dataFile);
            try {
                saveAddressBook(saveFile, defaultDataSupplier.get());
            } catch (DataConversionException | IOException e1) {
                logger.fatal("Unable to initialize local data file with default data.");
                assert false : "Unable to initialize local data file with default data.";
            }
        }
    }

    protected void loadDataFile(File dataFile) {
        try {
            loadDataFromFile(dataFile);
        } catch (FileNotFoundException e) {
            logger.debug("File not found: {}", dataFile);
            raise(new FileOpeningExceptionEvent(e, dataFile));
        }
    }

    protected void loadDataFromFile(File dataFile) throws FileNotFoundException {
        try {
            logger.debug("Attempting to load data from file: {}", dataFile);
            loadedDataCallback.accept(getData());
        } catch (DataConversionException e) {
            logger.debug("Error loading data from file: {}", e);
            raise(new FileOpeningExceptionEvent(e, dataFile));
        }
    }

    public ReadOnlyAddressBook getData() throws FileNotFoundException, DataConversionException {
        logger.debug("Attempting to read data from file: {}", saveFile);
        return XmlFileStorage.loadDataFromSaveFile(saveFile);
    }
}
