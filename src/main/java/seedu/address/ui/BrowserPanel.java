package seedu.address.ui;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.commons.core.LogsCenter;

import java.util.logging.Logger;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart {

    private static Logger logger = LogsCenter.getLogger(BrowserPanel.class);
    private static final String FXML = "BrowserPanel.fxml";

    @FXML
    private WebView browser;

    @Override
    public void setNode(Node node) {
        //not applicable
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

    /**
     * Creates a BrowserPanel.
     * This is the factory method for creating a Browser Panel.
     * This method should be called after the FX runtime is initialized and in FX application thread.
     * @param placeholder The AnchorPane where the BrowserPanel must be inserted
     */
    public static BrowserPanel load(AnchorPane placeholder) {
        logger.info("Initializing browser");
        BrowserPanel browserPanel = UiPartLoader.loadUiPart(placeholder, new BrowserPanel());
        placeholder.setOnKeyPressed(Event::consume); // To prevent triggering events for typing inside the
                                                     // loaded Web page.
        FxViewUtil.applyAnchorBoundaryParameters(browserPanel.browser, 0.0, 0.0, 0.0, 0.0);
        placeholder.getChildren().add(browserPanel.browser);
        return browserPanel;
    }

    public void loadPersonPage(ReadOnlyPerson person) {
        loadPage("https://www.google.com.sg/#safe=off&q=" + person.getName().fullName.replaceAll(" ", "+"));
    }

    public void loadPage(String url) {
        browser.getEngine().load(url);
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

}
