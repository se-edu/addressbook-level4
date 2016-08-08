package seedu.address;

import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.storage.StorageManager;
import seedu.address.ui.Ui;
import seedu.address.util.AppLogger;
import seedu.address.util.Config;
import seedu.address.util.DependencyChecker;
import seedu.address.util.LoggerManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.Map;

/**
 * The main entry point to the application.
 */
public class MainApp extends Application {
    private static final AppLogger logger = LoggerManager.getLogger(MainApp.class);

    private static final int VERSION_MAJOR = 1;
    private static final int VERSION_MINOR = 6;
    private static final int VERSION_PATCH = 1;
    private static final boolean VERSION_EARLY_ACCESS = true;

    public static final seedu.address.commons.Version VERSION = new seedu.address.commons.Version(
            VERSION_MAJOR, VERSION_MINOR, VERSION_PATCH, VERSION_EARLY_ACCESS);

    /**
     * Minimum Java Version Required
     *
     * Due to usage of ControlsFX 8.40.10, requires minimum Java version of 1.8.0_60.
     */
    public static final String REQUIRED_JAVA_VERSION = "1.8.0_60"; // update docs if this is changed

    protected StorageManager storageManager;
    protected ModelManager modelManager;

    protected Ui ui;
    protected Config config;
    protected UserPrefs userPrefs;

    public MainApp() {}

    @Override
    public void init() throws Exception {
        logger.info("Initializing app ...");
        super.init();
        new DependencyChecker(REQUIRED_JAVA_VERSION, this::quit).verify();
        Map<String, String> applicationParameters = getParameters().getNamed();
        config = initConfig(applicationParameters.get("config"));
        userPrefs = initPrefs(config);
        initComponents(config, userPrefs);
    }

    protected Config initConfig(String configFilePath) {
        return StorageManager.getConfig(configFilePath);
    }

    protected UserPrefs initPrefs(Config config) {
        return StorageManager.getUserPrefs(config.getPrefsFileLocation());
    }

    private void initComponents(Config config, UserPrefs userPrefs) {
        LoggerManager.init(config);

        modelManager = initModelManager(config);
        storageManager = initStorageManager(modelManager, config, userPrefs);
        ui = initUi(config, modelManager);
    }

    protected Ui initUi(Config config, ModelManager modelManager) {
        return new Ui(this, modelManager, config, userPrefs);
    }

    protected StorageManager initStorageManager(ModelManager modelManager, Config config, UserPrefs userPrefs) {
        return new StorageManager(modelManager::resetData, modelManager::getDefaultAddressBook, config, userPrefs);
    }

    protected ModelManager initModelManager(Config config) {
        return new ModelManager(config);
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting application: {}", MainApp.VERSION);
        ui.start(primaryStage);
        storageManager.start();
    }

    @Override
    public void stop() {
        logger.info("Stopping application.");
        ui.stop();
        storageManager.savePrefsToFile(userPrefs);
        quit();
    }

    private void quit() {
        Platform.exit();
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
