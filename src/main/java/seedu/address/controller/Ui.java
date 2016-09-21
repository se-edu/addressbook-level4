package seedu.address.controller;

import com.google.common.eventbus.Subscribe;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.browser.BrowserManager;
import seedu.address.events.EventManager;
import seedu.address.events.controller.JumpToListRequestEvent;
import seedu.address.events.storage.FileOpeningExceptionEvent;
import seedu.address.events.storage.FileSavingExceptionEvent;
import seedu.address.events.ui.ShowHelpEvent;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.util.Config;
import seedu.address.util.LoggerManager;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

/**
 * The controller that creates the other controllers
 */
public class Ui {
    private static final Logger logger = LoggerManager.getLogger(Ui.class);
    private static final String ICON_APPLICATION = "/images/address_book_32.png";

    private ModelManager modelManager;

    private BrowserManager browserManager;

    private Config config;
    private UserPrefs prefs;

    /** Main Window of the app */
    private MainWindow mainWindow;

    /**
     * Constructor for ui
     *
     * @param modelManager
     * @param config should have appTitle and updateInterval set
     */
    public Ui(ModelManager modelManager, Config config, UserPrefs prefs) {
        super();
        this.modelManager = modelManager;
        this.config = config;
        this.prefs = prefs;
        this.browserManager = new BrowserManager();
        EventManager.getInstance().registerHandler(this);
    }

    public void start(Stage primaryStage, MainApp mainApp) {
        logger.info("Starting UI...");
        this.browserManager.start();
        primaryStage.setTitle(config.getAppTitle());

        //Set the application icon.
        primaryStage.getIcons().add(getImage(ICON_APPLICATION));

        try {
            mainWindow = MainWindow.load(primaryStage, config, prefs, this, modelManager, browserManager);
            mainWindow.show(); //This should be called before creating other UI parts
            mainWindow.fillInnerParts();

        } catch (Throwable e) {
            e.printStackTrace();
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    public void stop() {
        prefs.updateLastUsedGuiSetting(mainWindow.getCurrentGuiSetting());
        mainWindow.hide();
        releaseResourcesForAppTermination();
    }

    /**
     * Returns the main stage.
     */
    public Stage getPrimaryStage() {
        return mainWindow.primaryStage;
    }

    @Subscribe
    private void handleFileOpeningExceptionEvent(FileOpeningExceptionEvent foee) {
        showFileOperationAlertAndWait("Could not load data", "Could not load data from file", foee.file,
                                      foee.exception);
    }

    @Subscribe
    private void handleFileSavingExceptionEvent(FileSavingExceptionEvent fsee) {
        showFileOperationAlertAndWait("Could not save data", "Could not save data to file", fsee.file, fsee.exception);
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpEvent event) {
        mainWindow.handleHelp();
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        mainWindow.getPersonListPanel().scrollTo(event.targetIndex);
    }

    private void showFileOperationAlertAndWait(String description, String details, File file, Throwable cause) {
        final String content = details + ":\n" + (file == null ? "none" : file.getPath()) + "\n\nDetails:\n======\n"
                                + cause.toString();
        showAlertDialogAndWait(AlertType.ERROR, "File Op Error", description, content);
    }

    private Image getImage(String imagePath) {
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

    void showAlertDialogAndWait(Alert.AlertType type, String title, String headerText, String contentText) {
        showAlertDialogAndWait(mainWindow.getPrimaryStage(), type, title, headerText, contentText);
    }

    private static void showAlertDialogAndWait(Stage owner, AlertType type, String title, String headerText,
                                               String contentText) {
        final Alert alert = new Alert(type);
        alert.getDialogPane().getStylesheets().add("view/DarkTheme.css");
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        alert.showAndWait();
    }

    /**
     *  Releases resources to ensure successful application termination.
     */
    private void releaseResourcesForAppTermination(){
        browserManager.freeBrowserResources();
    }

    private void showFatalErrorDialogAndShutdown(String title, Throwable e) {
        //TODO: Do a more detailed error reporting e.g. stack trace
        logger.severe(title + " " + e.getMessage() );
        showAlertDialogAndWait(Alert.AlertType.ERROR, title, e.getMessage(), e.toString());
        Platform.exit();
        System.exit(1);
    }

    public ObservableList<ReadOnlyPerson> getDisplayedPersonsView() {
        return this.mainWindow.getPersonListPanel().getDisplayedPersonsView();
    }
}
