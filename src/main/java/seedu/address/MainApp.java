package seedu.address;

import com.google.common.eventbus.Subscribe;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.address.commons.core.Version;
import seedu.address.model.AddressBook;
import seedu.address.ui.UiManager;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.controller.ExitAppRequestEvent;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.storage.StorageManager;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;

import java.util.Map;
import java.util.logging.Logger;

/**
 * The main entry point to the application.
 */
public class MainApp extends Application {
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    private static final int VERSION_MAJOR = 1;
    private static final int VERSION_MINOR = 6;
    private static final int VERSION_PATCH = 1;
    private static final boolean VERSION_EARLY_ACCESS = true;

    public static final Version VERSION = new Version(
            VERSION_MAJOR, VERSION_MINOR, VERSION_PATCH, VERSION_EARLY_ACCESS);

    /**
     * Minimum Java Version Required
     *
     * Due to usage of ControlsFX 8.40.10, requires minimum Java version of 1.8.0_60.
     */
    public static final String REQUIRED_JAVA_VERSION = "1.8.0_60"; // update docs if this is changed

    protected StorageManager storageManager;
    protected ModelManager modelManager;

    protected UiManager uiManager;
    protected Config config;
    protected UserPrefs userPrefs;

    public MainApp() {}

    @Override
    public void init() throws Exception {
        logger.info("Initializing app ...");
        super.init();
        Map<String, String> applicationParameters = getParameters().getNamed();
        config = initConfig(applicationParameters.get("config"));
        userPrefs = initPrefs(config);
        initComponents(config, userPrefs);
        EventsCenter.getInstance().registerHandler(this);
    }

    protected Config initConfig(String configFilePath) {
        return StorageManager.getConfig(configFilePath);
    }

    protected UserPrefs initPrefs(Config config) {
        return StorageManager.getUserPrefs(config.getPrefsFileLocation());
    }

    private void initComponents(Config config, UserPrefs userPrefs) {
        LogsCenter.init(config);

        modelManager = new ModelManager();
        storageManager = initStorageManager(modelManager, config, userPrefs);
        uiManager = initUi(config, modelManager);
    }

    protected UiManager initUi(Config config, ModelManager modelManager) {
        return new UiManager(modelManager, config, userPrefs);
    }

    protected StorageManager initStorageManager(ModelManager modelManager, Config config, UserPrefs userPrefs) {
        return new StorageManager(modelManager::resetData, AddressBook::getEmptyAddressBook, config);
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting application: " + MainApp.VERSION);
        uiManager.start(primaryStage, this);
        storageManager.start();
    }

    @Override
    public void stop() {
        logger.info("Stopping application.");
        uiManager.stop();
        storageManager.savePrefsToFile(userPrefs);
        quit();
    }

    private void quit() {
        Platform.exit();
        System.exit(0);
    }

    @Subscribe
    public void handleExitAppRequestEvent(ExitAppRequestEvent event) {
        this.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
