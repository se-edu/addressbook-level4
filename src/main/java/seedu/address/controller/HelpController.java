package seedu.address.controller;

import seedu.address.MainApp;
import seedu.address.commons.FxViewUtil;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;

/**
 * Controller for a help page
 */
public class HelpController {

    @FXML
    private AnchorPane mainPane;

    public HelpController() {

    }

    @FXML
    public void initialize() {
        WebView browser = new WebView();
        browser.getEngine().load(MainApp.class.getResource("/help_html/index.html").toExternalForm());
        FxViewUtil.applyAnchorBoundaryParameters(browser, 0.0, 0.0, 0.0, 0.0);
        mainPane.getChildren().add(browser);
    }
}
