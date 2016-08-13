package seedu.address.controller;

import com.google.common.eventbus.Subscribe;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.commons.OsDetector;
import seedu.address.events.controller.MinimizeAppRequestEvent;
import seedu.address.events.controller.ResizeAppRequestEvent;
import seedu.address.events.hotkey.KeyBindingEvent;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.parser.Command;
import seedu.address.parser.CommandParser;
import seedu.address.util.AppLogger;
import seedu.address.util.Config;
import seedu.address.util.GuiSettings;
import seedu.address.util.LoggerManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends BaseUiPart {
    private static AppLogger logger = LoggerManager.getLogger(MainWindow.class);
    private static final String ICON = "/images/address_book_32.png";
    private static final String FXML = "MainWindow.fxml";
    public static final int MIN_HEIGHT = 600;
    public static final int MIN_WIDTH = 450;
    public static final String PERSON_LIST_PANEL_PLACEHOLDER_ID = "#personListPanel";



    private MainApp mainApp; //TODO: remove this dependency as per TODOs given in methods below
    private Ui ui; //TODO: remove this dependency as per TODOs given in methods below

    //Link to the model
    private ModelManager modelManager;

    //Independent Ui parts residing in this Ui container
    private CommandParser parser;

    //Independent Ui parts residing in this Ui container
    private PersonListPanel personListPanel;

    //Handles to elements of this Ui container
    private VBox rootLayout;
    private Scene scene;

    @FXML
    private AnchorPane personListPanelPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private TextField filterField;

    public MainWindow() {
        super();
        parser = new CommandParser();
    }

    @Override
    public void setNode(Node node) {
        rootLayout = (VBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    public static MainWindow load(Stage primaryStage, Config config, UserPrefs prefs, MainApp mainApp, Ui ui, ModelManager modelManager) {
        logger.debug("Initializing main window.");
        MainWindow mainWindow = UiPartLoader.loadUiPart(primaryStage, new MainWindow());
        mainWindow.configure(config.getAppTitle(), prefs, mainApp, ui, modelManager);
        mainWindow.setKeyEventHandler();
        mainWindow.setAccelerators();
        return mainWindow;
    }

    private void configure(String appTitle, UserPrefs prefs, MainApp mainApp, Ui ui, ModelManager modelManager) {
        //Set connections
        this.mainApp = mainApp;
        this.ui = ui;
        this.modelManager = modelManager;

        //Configure the UI
        setTitle(appTitle);
        setIcon(ICON);
        setWindowMinSize();
        setWindowDefaultSize(prefs);
        scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
    }

    //TODO: to be removed with more specific method e.g. getListPanelSlot
    public AnchorPane getAnchorPane(String anchorPaneId) {
        return (AnchorPane) rootLayout.lookup(anchorPaneId);
    }


    private void setKeyEventHandler(){
        scene.setOnKeyPressed((e) -> raisePotentialEvent(new KeyBindingEvent(e)));
    }

    public void setConnections(MainApp mainApp, Ui ui, ModelManager modelManager) {
        this.ui = ui;
        this.modelManager = modelManager;
        this.mainApp = mainApp;
    }

    public void fillInnerParts() {
        personListPanel = PersonListPanel.load(primaryStage, getPersonListPlaceholder(), ui, modelManager);
        //TODO: more to be added here (i.e. headerStatusBar, footerStatusBar etc.)
    }

    /**
     * Returns the AnchorPane where the PersonListPanel is to added.
     */
    public AnchorPane getPersonListPlaceholder() {
        return personListPanelPlaceholder;
    }

    public void hide() {
        primaryStage.hide();
    }

    private void setTitle(String appTitle) {
        primaryStage.setTitle(appTitle);
    }

    /**
     * Sets the default size based on user preferences.
     */
    protected void setWindowDefaultSize(UserPrefs prefs) {
        primaryStage.setHeight(prefs.getGuiSettings().getWindowHeight());
        primaryStage.setWidth(prefs.getGuiSettings().getWindowWidth());
        if (prefs.getGuiSettings().getWindowCoordinates() != null) {
            primaryStage.setX(prefs.getGuiSettings().getWindowCoordinates().getX());
            primaryStage.setY(prefs.getGuiSettings().getWindowCoordinates().getY());
        }
    }

    private void setWindowMinSize() {
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setMinWidth(MIN_WIDTH);
    }

    /**
     * Returns the current size and the position of the main Window.
     * @return
     */
    public GuiSettings getCurrentGuiSetting() {
        return new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
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
        ui.showHelpPage();
    }

    public void show() {
        primaryStage.show();
    }

    /**
     * Opens an about dialog.
     */
    @FXML
    private void handleAbout() {
        logger.debug("Showing information about the application.");
        ui.showAlertDialogAndWait(AlertType.INFORMATION, "AddressApp", "About",
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
        ui.showTagList(modelManager.getTagsAsReadOnlyObservableList());
    }

    @Subscribe
    private void handleResizeAppRequestEvent(ResizeAppRequestEvent event){
        logger.debug("Handling the resize app window request");
        Platform.runLater(this::handleResizeRequest);
    }

    @Subscribe
    private void handleMinimizeAppRequestEvent(MinimizeAppRequestEvent event){
        logger.debug("Handling the minimize app window request");
        Platform.runLater(this::minimizeWindow);
    }


    public void minimizeWindow() {
        primaryStage.setIconified(true);
        primaryStage.setMaximized(false);
    }

    public void handleResizeRequest() {
        logger.info("Handling resize request.");
        if (primaryStage.isIconified()) {
            logger.debug("Cannot resize as window is iconified, attempting to show window instead.");
            primaryStage.setIconified(false);
        } else {
            resizeWindow();
        }
    }

    public void resizeWindow() {
        logger.info("Resizing window");
        // specially handle since stage operations on Mac seem to not be working as intended
        if (OsDetector.isOnMac()) {
            // refresh stage so that resizing effects (apart from the first resize after iconify-ing) are applied
            // however, this will cause minor flinching in window visibility
            primaryStage.hide(); // hide has to be called before setMaximized,
            // or first resize attempt after iconify-ing will resize twice
            primaryStage.show();

            // on Mac, setMaximized seems to work like "setResize"
            // isMaximized also does not seem to return the correct value
            primaryStage.setMaximized(true);
        } else {
            primaryStage.setMaximized(!primaryStage.isMaximized());
        }

        logger.debug("Stage width: {}", primaryStage.getWidth());
        logger.debug("Stage height: {}", primaryStage.getHeight());
    }
}
