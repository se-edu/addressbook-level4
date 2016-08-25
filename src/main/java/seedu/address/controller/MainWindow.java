package seedu.address.controller;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.browser.BrowserManager;
import seedu.address.commands.Command;
import seedu.address.commands.CommandResult;
import seedu.address.commands.IncorrectCommand;
import seedu.address.events.hotkey.KeyBindingEvent;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.parser.Parser;
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

    private MainApp mainApp; //TODO: remove this dependency as per TODOs given in methods below
    private Ui ui; //TODO: remove this dependency as per TODOs given in methods below

    //Link to the model
    private ModelManager modelManager;

    private BrowserManager browserManager;

    private Parser parser;

    //Independent Ui parts residing in this Ui container
    private PersonListPanel personListPanel;
    private StatusBarHeader statusBarHeader;
    private StatusBarFooter statusBarFooter;
    private WebView browser;

    //Handles to elements of this Ui container
    private VBox rootLayout;
    private Scene scene;

    private String addressBookName;

    @FXML
    private AnchorPane personListPanelPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private TextField filterField;

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

    public static MainWindow load(Stage primaryStage, Config config, UserPrefs prefs, MainApp mainApp, Ui ui,
                                  ModelManager modelManager, BrowserManager browserManager) {
        logger.debug("Initializing main window.");
        MainWindow mainWindow = UiPartLoader.loadUiPart(primaryStage, new MainWindow());
        mainWindow.configure(config.getAppTitle(), config.getAddressBookName(), prefs, mainApp, ui, modelManager,
                             browserManager);
        mainWindow.setKeyEventHandler();
        mainWindow.setAccelerators();
        return mainWindow;
    }

    private void configure(String appTitle, String addressBookName, UserPrefs prefs, MainApp mainApp, Ui ui,
                           ModelManager modelManager, BrowserManager browserManager) {
        //Set connections
        this.mainApp = mainApp;
        this.ui = ui;
        this.modelManager = modelManager;
        this.addressBookName = addressBookName;
        this.browserManager = browserManager;
        this.parser.configure(ui);
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

    public void fillInnerParts() {
        personListPanel = PersonListPanel.load(primaryStage, getPersonListPlaceholder(), modelManager, browserManager);
        browser = loadBrowser();
    }

    private WebView loadBrowser() {
        AnchorPane pane = this.getAnchorPane("#personWebpage");
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
    private void handleFilterChanged() {

        if (filterField.getStyleClass().contains("error")) filterField.getStyleClass().remove("error");

        Command command = parser.parseCommand(filterField.getText());
        command.setData(modelManager);
        CommandResult result = command.execute();

        if (command instanceof IncorrectCommand) {
            filterField.getStyleClass().add("error");
        } else {
            filterField.getStyleClass().add("");
        }

        logger.info("Result: " + result.feedbackToUser);
        logger.debug("Invalid command: {}", filterField.getText());
    }

    @FXML
    private void handleHelp() {
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
        logger.debug("Showing information about the application.");
        ui.showAlertDialogAndWait(AlertType.INFORMATION, "AddressApp", "About",
                "Version " + MainApp.VERSION.toString() + "\nSome code adapted from http://code.makery.ch");
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        //TODO: remove dependency on mainApp by using an event
        mainApp.stop();
    }

    public PersonListPanel getPersonListPanel() {
        return this.personListPanel;
    }
}
