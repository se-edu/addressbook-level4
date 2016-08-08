package seedu.address.controller;

import seedu.address.MainApp;
import seedu.address.events.parser.FilterCommittedEvent;
import seedu.address.model.ModelManager;
import seedu.address.parser.Command;
import seedu.address.parser.CommandParser;
import seedu.address.parser.ParseException;
import seedu.address.parser.Parser;
import seedu.address.parser.expr.Expr;
import seedu.address.parser.expr.PredExpr;
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

    private Parser parser;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private TextField filterField;

    public RootLayoutController() {
        super();
        parser = new Parser();
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

        if (CommandParser.isCommandInput(filterField.getText())) {
            Command cmd = CommandParser.parse(filterField.getText());
            cmd.execute(modelManager);
            return;
        }

        Expr filterExpression;
        try {
            filterExpression = parser.parse(filterField.getText());
            if (filterField.getStyleClass().contains("error")) filterField.getStyleClass().remove("error");
        } catch (ParseException e) {
            logger.debug("Invalid filter found: {}", e);
            filterExpression = PredExpr.TRUE;
            if (!filterField.getStyleClass().contains("error")) filterField.getStyleClass().add("error");
        }

        raise(new FilterCommittedEvent(filterExpression));
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
