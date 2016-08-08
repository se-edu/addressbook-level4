package seedu.address.controller;

import seedu.address.MainApp;
import seedu.address.browser.BrowserManager;
import seedu.address.events.controller.MinimizeAppRequestEvent;
import seedu.address.events.controller.ResizeAppRequestEvent;
import seedu.address.events.hotkey.KeyBindingEvent;
import seedu.address.events.storage.FileOpeningExceptionEvent;
import seedu.address.events.storage.FileSavingExceptionEvent;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.datatypes.person.ReadOnlyPerson;
import seedu.address.model.datatypes.tag.Tag;
import seedu.address.util.AppLogger;
import seedu.address.util.Config;
import seedu.address.util.LoggerManager;
import seedu.address.util.collections.UnmodifiableObservableList;
import com.google.common.eventbus.Subscribe;
import seedu.address.commons.FxViewUtil;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * The controller that creates the other controllers
 */
public class MainController extends UiController{
    public static final String DIALOG_TITLE_TAG_LIST = "List of Tags";
    private static final AppLogger logger = LoggerManager.getLogger(MainController.class);
    private static final String FXML_HELP = "/view/Help.fxml";
    private static final String FXML_STATUS_BAR_FOOTER = "/view/StatusBarFooter.fxml";
    private static final String FXML_PERSON_LIST_PANEL = "/view/PersonListPanel.fxml";
    private static final String FXML_TAG_LIST = "/view/TagList.fxml";
    private static final String FXML_ROOT_LAYOUT = "/view/RootLayout.fxml";
    private static final String ICON_APPLICATION = "/images/address_book_32.png";
    private static final String ICON_HELP = "/images/help_icon.png";
    private static final int MIN_HEIGHT = 600;
    private static final int MIN_WIDTH = 450;

    private static final String PERSON_LIST_PANEL_FIELD_ID = "#personListPanel";
    private static final String HEADER_STATUSBAR_PLACEHOLDER_FIELD_ID = "#headerStatusbarPlaceholder";
    private static final String FOOTER_STATUSBAR_PLACEHOLDER_FIELD_ID = "#footerStatusbarPlaceholder";
    private static final String PERSON_WEBPAGE_PLACE_HOLDER_FIELD_ID = "#personWebpage";

    private Stage primaryStage;
    private VBox rootLayout;

    private ModelManager modelManager;
    private BrowserManager browserManager;
    private MainApp mainApp;
    private Config config;
    private UserPrefs prefs;

    private StatusBarHeaderController statusBarHeaderController;
    private StatusBarFooterController statusBarFooterController;

    private UnmodifiableObservableList<ReadOnlyPerson> personList;

    /**
     * Constructor for mainController
     *
     * @param mainApp
     * @param modelManager
     * @param config should have appTitle and updateInterval set
     */
    public MainController(MainApp mainApp, ModelManager modelManager, Config config, UserPrefs prefs) {
        super();
        this.mainApp = mainApp;
        this.modelManager = modelManager;
        this.config = config;
        this.prefs = prefs;
        this.personList = modelManager.getPersonsAsReadOnlyObservableList();
        this.browserManager = new BrowserManager();
    }

    public void start(Stage primaryStage) {
        logger.info("Starting main controller.");
        this.primaryStage = primaryStage;
        this.browserManager.start();
        primaryStage.setTitle(config.getAppTitle());

        // Set the application icon.
        this.primaryStage.getIcons().add(getImage(ICON_APPLICATION));

        initRootLayout();
        showPersonListPanel();
        showPersonWebPage();
        showFooterStatusBar();
        showHeaderStatusBar();
    }

    /**
     * Initializes the root layout and tries to load the last opened
     * person file.
     */
    public void initRootLayout() {
        logger.debug("Initializing root layout.");
        final String fxmlResourcePath = FXML_ROOT_LAYOUT;
        // Load root layout from fxml file.
        FXMLLoader loader = loadFxml(fxmlResourcePath);
        rootLayout = (VBox) loadLoader(loader, "Error initializing root layout");

        // Show the scene containing the root layout.
        Scene scene = new Scene(rootLayout);
        scene.setOnKeyPressed(event -> raisePotentialEvent(new KeyBindingEvent(event)));
        primaryStage.setScene(scene);
        setMinSize();
        setDefaultSize();

        // Give the rootController access to the main controller and modelManager
        RootLayoutController rootController = loader.getController();
        rootController.setConnections(mainApp, this, modelManager);
        rootController.setAccelerators();

        primaryStage.show();
    }

    public StatusBarHeaderController getStatusBarHeaderController() {
        return statusBarHeaderController;
    }

    /**
     * Shows the person list panel inside the root layout.
     */
    public void showPersonListPanel() {
        logger.debug("Loading person list panel.");
        final String fxmlResourcePath = FXML_PERSON_LIST_PANEL;
        // Load person overview.
        FXMLLoader loader = loadFxml(fxmlResourcePath);
        VBox personListPanel = (VBox) loadLoader(loader, "Error loading person list panel");
        AnchorPane pane = (AnchorPane) rootLayout.lookup(PERSON_LIST_PANEL_FIELD_ID);
        SplitPane.setResizableWithParent(pane, false);
        // Give the personListPanelController access to the main app and modelManager.
        PersonListPanelController personListPanelController = loader.getController();
        personListPanelController.setConnections(this, modelManager, personList);

        pane.getChildren().add(personListPanel);
    }

    private void showHeaderStatusBar() {
        statusBarHeaderController = new StatusBarHeaderController(this);
        AnchorPane sbPlaceHolder = (AnchorPane) rootLayout.lookup(HEADER_STATUSBAR_PLACEHOLDER_FIELD_ID);

        assert sbPlaceHolder != null : "headerStatusbarPlaceHolder node not found in rootLayout";

        FxViewUtil.applyAnchorBoundaryParameters(statusBarHeaderController.getHeaderStatusBarView(),
                                                 0.0, 0.0, 0.0, 0.0);
        sbPlaceHolder.getChildren().add(statusBarHeaderController.getHeaderStatusBarView());
    }

    private void showFooterStatusBar() {
        logger.debug("Loading footer status bar.");
        final String fxmlResourcePath = FXML_STATUS_BAR_FOOTER;
        FXMLLoader loader = loadFxml(fxmlResourcePath);
        GridPane gridPane = (GridPane) loadLoader(loader, "Error Loading footer status bar");
        gridPane.getStyleClass().add("grid-pane");
        statusBarFooterController = loader.getController();
        statusBarFooterController.init(config.getAddressBookName());
        AnchorPane placeHolder = (AnchorPane) rootLayout.lookup(FOOTER_STATUSBAR_PLACEHOLDER_FIELD_ID);
        FxViewUtil.applyAnchorBoundaryParameters(gridPane, 0.0, 0.0, 0.0, 0.0);
        placeHolder.getChildren().add(gridPane);
    }

    public void showPersonWebPage() {
        AnchorPane pane = (AnchorPane) rootLayout.lookup(PERSON_WEBPAGE_PLACE_HOLDER_FIELD_ID);
        disableKeyboardShortcutOnNode(pane);
        pane.getChildren().add(browserManager.getBrowserView());
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

    public void showTagList(ObservableList<Tag> tags) {
        logger.debug("Loading tag list.");
        final String fxmlResourcePath = FXML_TAG_LIST;
        // Load the fxml file and create a new stage for the popup dialog.
        FXMLLoader loader = loadFxml(fxmlResourcePath);
        AnchorPane page = (AnchorPane) loadLoader(loader, "Error loading tag list view");

        Scene scene = new Scene(page);
        Stage dialogStage = loadDialogStage(DIALOG_TITLE_TAG_LIST, primaryStage, scene);

        // Set the tag into the controller.
        TagListController tagListController = loader.getController();
        tagListController.setMainController(this);
        tagListController.setModelManager(modelManager);
        tagListController.setTags(tags);
        tagListController.setStage(dialogStage);

        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();
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
        return primaryStage;
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

    public void showAlertDialogAndWait(AlertType type, String title, String headerText, String contentText) {
        showAlertDialogAndWait(primaryStage, type, title, headerText, contentText);
    }

    public static void showAlertDialogAndWait(Stage owner, AlertType type, String title, String headerText,
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
    public void releaseResourcesForAppTermination(){
        browserManager.freeBrowserResources();
    }

    public void loadGithubProfilePage(ReadOnlyPerson person){
        browserManager.loadProfilePage(person);
    }

    private void disableKeyboardShortcutOnNode(Node pane) {
        pane.addEventHandler(EventType.ROOT, event -> event.consume());
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

    protected void setDefaultSize() {
        primaryStage.setHeight(prefs.getGuiSettings().getWindowHeight());
        primaryStage.setWidth(prefs.getGuiSettings().getWindowWidth());
        if (prefs.getGuiSettings().getWindowCoordinates() != null) {
            primaryStage.setX(prefs.getGuiSettings().getWindowCoordinates().getX());
            primaryStage.setY(prefs.getGuiSettings().getWindowCoordinates().getY());
        }
    }

    private void setMinSize() {
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setMinWidth(MIN_WIDTH);
    }

    private void minimizeWindow() {
        logger.info("Minimizing App");
        logger.debug("PrimaryStage title: " + primaryStage.getTitle());
        primaryStage.setIconified(true);
        primaryStage.setMaximized(false);
    }

    private void handleResizeRequest() {
        logger.info("Handling resize request.");
        if (primaryStage.isIconified()) {
            logger.debug("Cannot resize as window is iconified, attempting to show window instead.");
            primaryStage.setIconified(false);
        } else {
            resizeWindow();
        }
    }

    private void resizeWindow() {
        logger.info("Resizing window");
        // specially handle since stage operations on Mac seem to not be working as intended
        if (seedu.address.commons.OsDetector.isOnMac()) {
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

        logger.debug("After: Stage width: " + primaryStage.getWidth() + " Stage Height: " + primaryStage.getHeight());
    }

    public void stop() {
        getPrimaryStage().hide();
        releaseResourcesForAppTermination();
    }

    private void showFatalErrorDialogAndShutdown(String title, String headerText, String contentText,
                                                 String errorLocation) {
        showAlertDialogAndWait(AlertType.ERROR, title, headerText,
                contentText + errorLocation);
        Platform.exit();
        System.exit(1);
    }
}
