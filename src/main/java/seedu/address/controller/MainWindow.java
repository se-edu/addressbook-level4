package seedu.address.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.browser.BrowserManager;
import seedu.address.events.controller.ExitAppRequestEvent;
import seedu.address.events.hotkey.KeyBindingEvent;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.parser.Parser;
import seedu.address.util.Config;
import seedu.address.util.GuiSettings;
import seedu.address.util.LoggerManager;

import java.util.logging.Logger;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends BaseUiPart {
    private final Logger logger = LoggerManager.getLogger(MainWindow.class);
    private static final String ICON = "/images/address_book_32.png";
    private static final String FXML = "MainWindow.fxml";
    private static final String RESULT_DISPLAY_PLACEHOLDER_FIELD_ID = "#resultDisplayPlaceholder";
    private static final String FOOTER_STATUSBAR_PLACEHOLDER_FIELD_ID = "#footerStatusbarPlaceholder";
    private static final String COMMAND_BOX_PLACEHOLDER_FIELD_ID = "#commandBoxPlaceholder";
    private static final String BROWSER_PLACEHOLDER = "#personWebpage";
    public static final int MIN_HEIGHT = 600;
    public static final int MIN_WIDTH = 450;

    private Ui ui; //TODO: remove this dependency as per TODOs given in methods below

    // Link to the model
    private ModelManager modelManager;

    private BrowserManager browserManager;

    private Parser parser;

    // Independent Ui parts residing in this Ui container
    private PersonListPanel personListPanel;
    private ResultDisplay resultDisplay;
    private StatusBarFooter statusBarFooter;
    private CommandBox commandBox;
    private WebView browser;

    // Handles to elements of this Ui container
    private VBox rootLayout;
    private Scene scene;

    private String addressBookName;

    @FXML
    private AnchorPane personListPanelPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    public MainWindow() {
        super();
        parser = new Parser();
    }

    @Override
    public void setNode(Node node) {
        rootLayout = (VBox) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    public static MainWindow load(Stage primaryStage, Config config, UserPrefs prefs, Ui ui,
                                  ModelManager modelManager, BrowserManager browserManager) {
        MainWindow mainWindow = UiPartLoader.loadUiPart(primaryStage, new MainWindow());
        mainWindow.configure(config.getAppTitle(), config.getAddressBookName(), prefs, ui, modelManager,
                             browserManager);
        mainWindow.setKeyEventHandler();
        mainWindow.setAccelerators();
        return mainWindow;
    }

    private void configure(String appTitle, String addressBookName, UserPrefs prefs, Ui ui,
                           ModelManager modelManager, BrowserManager browserManager) {
        // Set connections
        this.ui = ui;
        this.modelManager = modelManager;
        this.addressBookName = addressBookName;
        this.browserManager = browserManager;
        this.parser.configure(ui);
        // Configure the UI
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

    private void setKeyEventHandler() {
        scene.setOnKeyPressed(e -> raisePotentialEvent(new KeyBindingEvent(e)));
    }

    public void fillInnerParts() {
        personListPanel = PersonListPanel.load(primaryStage, getPersonListPlaceholder(), modelManager, browserManager);
        resultDisplay = ResultDisplay.load(primaryStage, getHeaderStatusbarPlaceholder());
        statusBarFooter = StatusBarFooter.load(primaryStage, getFooterStatusbarPlaceholder(), addressBookName);
        commandBox = CommandBox.load(primaryStage, getCommandBoxPlaceholder(), parser, resultDisplay, modelManager);
        browser = loadBrowser();
    }

    private AnchorPane getCommandBoxPlaceholder() {
        return this.getAnchorPane(COMMAND_BOX_PLACEHOLDER_FIELD_ID);
    }

    private AnchorPane getFooterStatusbarPlaceholder() {
        return this.getAnchorPane(FOOTER_STATUSBAR_PLACEHOLDER_FIELD_ID);
    }

    private AnchorPane getHeaderStatusbarPlaceholder() {
        return this.getAnchorPane(RESULT_DISPLAY_PLACEHOLDER_FIELD_ID);
    }
    
    private WebView loadBrowser() {
        AnchorPane pane = this.getAnchorPane(BROWSER_PLACEHOLDER);
        pane.setOnKeyPressed(Event::consume); // Stops triggering of keybinding event.
        pane.getChildren().add(browserManager.getBrowserView());
        return (WebView) browserManager.getBrowserView();
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
    public void handleHelp() {
        HelpWindow helpWindow = HelpWindow.load(primaryStage);
        helpWindow.show();
    }

    public void show() {
        primaryStage.show();
    }

    /**
     * Opens an about dialog.
     */
    @FXML
    private void handleAbout() {//TODO: refactor to be similar to handleHelp and remove the dependency to ui
        logger.fine("Showing information about the application.");
        ui.showAlertDialogAndWait(AlertType.INFORMATION, "AddressApp", "About",
                "Version " + MainApp.VERSION.toString() + "\nSome code adapted from http://code.makery.ch");
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
    }

    public PersonListPanel getPersonListPanel() {
        return this.personListPanel;
    }
}
