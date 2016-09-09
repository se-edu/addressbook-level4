package seedu.address.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.commons.FxViewUtil;
import seedu.address.util.LoggerManager;

import java.util.logging.Logger;

/**
 * Controller for a help page
 */
public class HelpWindow extends BaseUiPart {

    private static final Logger logger = LoggerManager.getLogger(HelpWindow.class);
    private static final String ICON = "/images/help_icon.png";
    public static final String FXML = "HelpWindow.fxml";
    public static final String TITLE = "Help";

    private AnchorPane mainPane;

    private Stage dialogStage;

    public static HelpWindow load(Stage primaryStage) {
        logger.fine("Showing help page about the application.");
        HelpWindow helpWindow = UiPartLoader.loadUiPart(primaryStage, new HelpWindow());
        helpWindow.configure();
        return helpWindow;
    }

    @Override
    public void setNode(Node node) {
        mainPane = (AnchorPane) node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    @FXML
    public void initialize() {

    }

    private void configure(){
        Scene scene = new Scene(mainPane);
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setMaximized(true); //TODO: set a more appropriate initial size
        setIcon(dialogStage, ICON);

        WebView browser = new WebView();
        browser.getEngine().load(MainApp.class.getResource("/help_html/index.html").toExternalForm());
        FxViewUtil.applyAnchorBoundaryParameters(browser, 0.0, 0.0, 0.0, 0.0);
        mainPane.getChildren().add(browser);
    }

    public void show() {
        dialogStage.showAndWait();
    }
}
