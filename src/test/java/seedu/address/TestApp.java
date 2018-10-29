package seedu.address;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Supplier;

import javafx.stage.Screen;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyTranscript;
import seedu.address.model.Transcript;
import seedu.address.model.UserPrefs;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.storage.XmlSerializableAddressBook;
import seedu.address.testutil.TestUtil;
import systemtests.ModelHelper;

/**
 * This class is meant to override some properties of MainApp so that it will be suited for
 * testing
 */
public class TestApp extends MainApp {

    public static final Path SAVE_LOCATION_FOR_TESTING = TestUtil.getFilePathInSandboxFolder("sampleData.xml");
    public static final Path SAVE_TRANSCRIPT_LOCATION_FOR_TESTING =
            TestUtil.getFilePathInSandboxFolder("sampleTranscriptData.json");
    public static final String APP_TITLE = "Test App";

    protected static final Path DEFAULT_PREF_FILE_LOCATION_FOR_TESTING =
            TestUtil.getFilePathInSandboxFolder("pref_testing.json");
    protected Supplier<ReadOnlyAddressBook> initialDataSupplier = () -> null;
    protected Supplier<ReadOnlyTranscript> initialTranscriptDataSupplier = () -> new Transcript();
    protected Path saveFileLocation = SAVE_LOCATION_FOR_TESTING;
    protected Path saveTranscriptFileLocation = SAVE_TRANSCRIPT_LOCATION_FOR_TESTING;

    public TestApp() {
    }

    public TestApp(Supplier<ReadOnlyAddressBook> initialDataSupplier, Path saveFileLocation,
            Supplier<ReadOnlyTranscript> initialTranscriptDataSupplier, Path saveTranscriptFileLocation) {
        super();
        this.initialDataSupplier = initialDataSupplier;
        this.initialTranscriptDataSupplier = initialTranscriptDataSupplier;
        this.saveFileLocation = saveFileLocation;
        this.saveTranscriptFileLocation = saveTranscriptFileLocation;

        // If some initial local data has been provided, write those to the file
        if (initialDataSupplier.get() != null) {
            createDataFileWithData(new XmlSerializableAddressBook(this.initialDataSupplier.get()),
                    this.saveFileLocation);
        }
        // If some initial local data has been provided, write those to the file
        if (initialTranscriptDataSupplier.get() != null) {
            //For some reason when testing my app locally, this line keeps creating a file
            // in my test directory instead of the sandbox (unlike "sampleData.xml"), which is annoying).
            // createJsonDataFileWithData(new Transcript(this.initialTranscriptDataSupplier.get()),
            // this.saveTranscriptFileLocation);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected Config initConfig(Path configFilePath) {
        Config config = super.initConfig(configFilePath);
        config.setAppTitle(APP_TITLE);
        config.setUserPrefsFilePath(DEFAULT_PREF_FILE_LOCATION_FOR_TESTING);
        return config;
    }

    @Override
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        UserPrefs userPrefs = super.initPrefs(storage);
        double x = Screen.getPrimary().getVisualBounds().getMinX();
        double y = Screen.getPrimary().getVisualBounds().getMinY();
        userPrefs.updateLastUsedGuiSetting(new GuiSettings(600.0, 600.0, (int) x, (int) y));
        userPrefs.setAddressBookFilePath(saveFileLocation);
        userPrefs.setTranscriptFilePath(saveTranscriptFileLocation);
        return userPrefs;
    }

    /**
     * Returns a defensive copy of the address book data stored inside the storage file.
     */
    public AddressBook readStorageAddressBook() {
        try {
            return new AddressBook(storage.readAddressBook().get());
        } catch (DataConversionException dce) {
            throw new AssertionError("Data is not in the AddressBook format.", dce);
        } catch (IOException ioe) {
            throw new AssertionError("Storage file cannot be found.", ioe);
        }
    }

    /**
     * Returns a defensive copy of the transcript data stored inside the storage file.
     */
    public Transcript readStorageTranscript() {
        try {
            return new Transcript(storage.readTranscript().get());
        } catch (DataConversionException dce) {
            throw new AssertionError("Data is not in the Transcript format.", dce);
        } catch (IOException ioe) {
            throw new AssertionError("Storage file cannot be found.", ioe);
        }
    }

    /**
     * Returns the file path of the address book storage file.
     */
    public Path getAddressStorageSaveLocation() {
        return storage.getAddressBookFilePath();
    }

    /**
     * Returns the file path of the transcript storage file.
     */
    public Path getTranscriptStorageSaveLocation() {
        return storage.getTranscriptFilePath();
    }

    /**
     * Returns a defensive copy of the address book model.
     */
    public Model getAddressBookModel() {
        Model copy = new ModelManager((model.getAddressBook()), new UserPrefs());
        ModelHelper.setFilteredList(copy, model.getFilteredPersonList());
        return copy;
    }

    /**
     * Returns a defensive copy of the transcript model.
     */
    public Model getTranscriptModel() {
        Model copy = new ModelManager((model.getTranscript()), new UserPrefs());
        ModelHelper.setFilteredModuleList(copy, model.getFilteredModuleList());
        return copy;
    }

    @Override
    public void start(Stage primaryStage) {
        ui.start(primaryStage);
    }

    /**
     * Creates an XML file at the {@code filePath} with the {@code data}.
     */
    private <T> void createDataFileWithData(T data, Path filePath) {
        try {
            FileUtil.createIfMissing(filePath);
            XmlUtil.saveDataToFile(filePath, data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a json file at the {@code filePath} with the {@code data}.
     */
    private <T> void createJsonDataFileWithData(T data, Path filePath) {
        try {
            FileUtil.createIfMissing(filePath);
            JsonUtil.saveJsonFile(data, filePath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
