package seedu.address.storage;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Manages storage of addressbook data in local disk.
 * Handles storage related events.
 */
public class StorageManager extends ComponentManager implements AddressBookStorage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private XmlAddressBookStorage addressBookStorage;
    private String prefsFilePath;


    public StorageManager(String addressBookFilePath, String preferencesFilePath) {
        super();
        this.addressBookStorage = new XmlAddressBookStorage(addressBookFilePath);
        this.prefsFilePath = preferencesFilePath;
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
    public Optional<UserPrefs> readPrefs() throws DataConversionException {
        return new JsonPrefStorage().readPrefs(prefsFilePath);
    }

    //TODO: add comment
    public void savePrefs(UserPrefs prefs) throws IOException {
        new JsonPrefStorage().savePrefs(prefs, prefsFilePath);
    }


    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, FileNotFoundException {
        logger.fine("Attempting to read data from file: " + addressBookStorage.getAddressBookFilePath());

        return addressBookStorage.readAddressBook(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        addressBookStorage.saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
    }


    @Override
    public String getAddressBookFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

}
