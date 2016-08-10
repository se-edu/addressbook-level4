package seedu.address.controller;

import seedu.address.MainApp;
import seedu.address.model.ModelManager;
import seedu.address.parser.Command;
import seedu.address.parser.CommandParser;
import seedu.address.util.AppLogger;
import seedu.address.util.LoggerManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;

/**
 * The controller for the root layout. The root layout provides the basic
 * application layout containing a menu bar and space where other JavaFX
 * elements can be placed.
 */
public class RootLayoutController extends UiController {
    private static AppLogger logger = LoggerManager.getLogger(RootLayoutController.class);

    private MainController mainController;
    private ModelManager modelManager;
    private MainApp mainApp;

    private CommandParser parser;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private TextField filterField;

    public RootLayoutController() {
        super();
        parser = new CommandParser();
    }

    public void setConnections(MainApp mainApp, MainController mainController, ModelManager modelManager) {
        this.mainController = mainController;
        this.modelManager = modelManager;
        this.mainApp = mainApp;
    }

    public void setAccelerators() {
        helpMenuItem.setAccelerator(KeyCombination.valueOf("F1"));
    }

    @FXML
    private void handleFilterChanged() {
        if (parser.isCommandInput(filterField.getText())) {
            if (filterField.getStyleClass().contains("error")) filterField.getStyleClass().remove("error");
            Command cmd = parser.parse(filterField.getText());
            cmd.execute(modelManager);
            return;
        }
        logger.debug("Invalid command: {}", filterField.getText());
        if (!filterField.getStyleClass().contains("error")) filterField.getStyleClass().add("error");
    }

    @FXML
    private void handleHelp() {
        logger.debug("Showing help page about the application.");
        mainController.showHelpPage();
    }

    /**
     * Opens an about dialog.
     */
    @FXML
    private void handleAbout() {
        logger.debug("Showing information about the application.");
        mainController.showAlertDialogAndWait(AlertType.INFORMATION, "AddressApp", "About",
                "Version " + MainApp.VERSION.toString() + "\nSome code adapted from http://code.makery.ch");
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        mainApp.stop();
    }

    @FXML
    private void handleShowTags() {
        logger.debug("Attempting to show tag list.");
        mainController.showTagList(modelManager.getTagsAsReadOnlyObservableList());
    }
}
