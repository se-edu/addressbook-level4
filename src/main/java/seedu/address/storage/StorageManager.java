package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.TranscriptChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyTranscript;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of Transcript data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private UserPrefsStorage userPrefsStorage;
    private TranscriptStorage transcriptStorage;

    public StorageManager(AddressBookStorage addressBookStorage,
            UserPrefsStorage userPrefsStorage,
            TranscriptStorage transcriptStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.transcriptStorage = transcriptStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ AddressBook methods ==============================

    @Override
    public Path getAddressBookFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
    }


    @Override
    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveAddressBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    // ================ Transcript methods ==============================

    @Override
    public Path getTranscriptFilePath() {
        return transcriptStorage.getTranscriptFilePath();
    }

    @Override
    public Optional<ReadOnlyTranscript> readTranscript() throws DataConversionException, IOException {
        return readTranscript(transcriptStorage.getTranscriptFilePath());
    }

    @Override
    public Optional<ReadOnlyTranscript> readTranscript(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return transcriptStorage.readTranscript(filePath);
    }

    @Override
    public void saveTranscript(ReadOnlyTranscript transcript) throws IOException {
        saveTranscript(transcript, transcriptStorage.getTranscriptFilePath());
    }

    @Override
    public void saveTranscript(ReadOnlyTranscript transcript, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        transcriptStorage.saveTranscript(transcript, filePath);
    }

    @Override
    @Subscribe
    public void handleTranscriptChangedEvent(TranscriptChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local transcript data changed, saving to file"));
        try {
            saveTranscript(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
