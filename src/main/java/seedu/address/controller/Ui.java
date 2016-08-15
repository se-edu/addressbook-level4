package seedu.address.controller;

import seedu.address.MainApp;
import seedu.address.browser.BrowserManager;
import seedu.address.events.EventManager;
import seedu.address.events.storage.FileOpeningExceptionEvent;
import seedu.address.events.storage.FileSavingExceptionEvent;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.datatypes.tag.Tag;
import seedu.address.util.AppLogger;
import seedu.address.util.Config;
import seedu.address.util.LoggerManager;
import com.google.common.eventbus.Subscribe;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * The controller that creates the other controllers
 */
public class Ui {
    public static final String DIALOG_TITLE_TAG_LIST = "List of Tags";
    private static final AppLogger logger = LoggerManager.getLogger(Ui.class);
    private static final String FXML_HELP = "/view/HelpWindow.fxml";
    private static final String FXML_PERSON_LIST_PANEL = "/view/PersonListPanel.fxml";
    private static final String FXML_TAG_LIST = "/view/TagList.fxml";
    private static final String FXML_ROOT_LAYOUT = "/view/MainWindow.fxml";
    private static final String ICON_APPLICATION = "/images/address_book_32.png";
    private static final String ICON_HELP = "/images/help_icon.png";
    private static final int MIN_HEIGHT = 600;
    private static final int MIN_WIDTH = 450;

    private static final String PERSON_WEBPAGE_PLACE_HOLDER_FIELD_ID = "#personWebpage";

    private ModelManager modelManager;

    private BrowserManager browserManager;

    private Config config;
    private UserPrefs prefs;

    //Main Window of the app
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
        logger.info("Starting main controller.");
        this.browserManager.start();
        primaryStage.setTitle(config.getAppTitle());

        // Set the application icon.
        primaryStage.getIcons().add(getImage(ICON_APPLICATION));

        try {
            logger.info("Starting main UI.");

            mainWindow = MainWindow.load(primaryStage, config, prefs, mainApp, this, modelManager, browserManager);
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

    private Node loadLoader(FXMLLoader loader, String errorMsg) {
        try {
            return loader.load();
        } catch (IOException e) {
            logger.fatal(errorMsg + ": {}", e);
            showFatalErrorDialogAndShutdown("FXML Load Error", errorMsg,
                    "IOException when trying to load ", loader.getLocation().toExternalForm());
            return null;
        }
    }

    private FXMLLoader loadFxml(String fxmlResourcePath) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource(fxmlResourcePath));
        return loader;
    }

    private Stage loadDialogStage(String value, Stage primaryStage, Scene scene) {
        Stage dialogStage = new Stage();
        dialogStage.setTitle(value);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        dialogStage.setScene(scene);
        return dialogStage;
    }

    /**
     * Opens a dialog to show the help page
     */
    public void showHelpPage() {
        logger.debug("Loading help page.");
        final String fxmlResourcePath = FXML_HELP;
        // Load the fxml file and create a new stage for the popup dialog.
        FXMLLoader loader = loadFxml(fxmlResourcePath);
        AnchorPane page = (AnchorPane) loadLoader(loader, "Error loading help page");

        Scene scene = new Scene(page);
        Stage dialogStage = loadDialogStage("Help", null, scene);
        dialogStage.getIcons().add(getImage(ICON_HELP));
        dialogStage.setMaximized(true);
        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();
    }

    /**
     * Returns the main stage.
     * @return
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

    private void showFileOperationAlertAndWait(String description, String details, File file, Throwable cause) {
        final String content = details + ":\n" + (file == null ? "none" : file.getPath()) + "\n\nDetails:\n======\n"
                                + cause.toString();
        showAlertDialogAndWait(AlertType.ERROR, "File Op Error", description, content);
    }

    private Image getImage(String imagePath) {
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

    public void showAlertDialogAndWait(Alert.AlertType type, String title, String headerText, String contentText) {
        showAlertDialogAndWait(mainWindow.getPrimaryStage(), type, title, headerText, contentText);
    }

    public static void showAlertDialogAndWait(Stage owner, AlertType type, String title, String headerText,
                                              String contentText) {
        final Alert alert = new Alert(type);
        alert.getDialogPane().getStylesheets().add("view/Theme.css");
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        alert.showAndWait();
    }

    /**
     *  Releases resources to ensure successful application termination.
     */
    public void releaseResourcesForAppTermination(){
        browserManager.freeBrowserResources();
    }

    private void showFatalErrorDialogAndShutdown(String title, String headerText, String contentText, String errorLocation) {
        showAlertDialogAndWait(Alert.AlertType.ERROR, title, headerText, contentText + errorLocation);
        Platform.exit();
        System.exit(1);
    }

    public void showFatalErrorDialogAndShutdown(String title, Throwable e) {
        //TODO: Do a more detailed error reporting e.g. stack trace
        logger.fatal(title + " " + e.getMessage());
        showAlertDialogAndWait(Alert.AlertType.ERROR, title, e.getMessage(), e.toString());
        Platform.exit();
        System.exit(1);
    }
}
