package seedu.address.ui;

import com.google.common.eventbus.Subscribe;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.controller.JumpToListRequestEvent;
import seedu.address.commons.events.controller.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.controller.ShowHelpEvent;
import seedu.address.commons.events.storage.DataReadingExceptionEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.LogicManager;
import seedu.address.model.UserPrefs;

import java.util.logging.Logger;

/**
 * The manager of the UI component.
 */
public class UiManager extends ComponentManager{
    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private static final String ICON_APPLICATION = "/images/address_book_32.png";

    private LogicManager logicManager;
    private Config config;
    private UserPrefs prefs;
    private MainWindow mainWindow;

    public UiManager(LogicManager logicManager, Config config, UserPrefs prefs) {
        super();
        this.logicManager = logicManager;
        this.config = config;
        this.prefs = prefs;
    }

    public void start(Stage primaryStage, MainApp mainApp) {
        logger.info("Starting UI...");
        primaryStage.setTitle(config.getAppTitle());

        //Set the application icon.
        primaryStage.getIcons().add(getImage(ICON_APPLICATION));

        try {
            mainWindow = MainWindow.load(primaryStage, config, prefs, logicManager);
            mainWindow.show(); //This should be called before creating other UI parts
            mainWindow.fillInnerParts();

        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    public void stop() {
        prefs.updateLastUsedGuiSetting(mainWindow.getCurrentGuiSetting());
        mainWindow.hide();
        mainWindow.releaseResources();
    }

    /**
     * Returns the main stage.
     */
    public Stage getPrimaryStage() {
        return mainWindow.primaryStage;
    }

    private void showFileOperationAlertAndWait(String description, String details, Throwable cause) {
        final String content = details + ":\n" + cause.toString();
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

    private void showFatalErrorDialogAndShutdown(String title, Throwable e) {
        logger.severe(title + " " + e.getMessage() + StringUtil.getDetails(e));
        showAlertDialogAndWait(Alert.AlertType.ERROR, title, e.getMessage(), e.toString());
        Platform.exit();
        System.exit(1);
    }

    //==================== Event Handling Code =================================================================

    @Subscribe
    private void handleDataReadingExceptionEvent(DataReadingExceptionEvent foee) {
        showFileOperationAlertAndWait("Could not load data", "Could not load data from file",
                                      foee.exception);
    }

    @Subscribe
    private void handleDataSavingExceptionEvent(DataSavingExceptionEvent fsee) {
        showFileOperationAlertAndWait("Could not save data", "Could not save data to file", fsee.exception);
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpEvent event) {
        mainWindow.handleHelp();
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        mainWindow.getPersonListPanel().scrollTo(event.targetIndex);
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent ppsce){
        mainWindow.loadPersonPage(ppsce.getNewSelection());
    }

}
