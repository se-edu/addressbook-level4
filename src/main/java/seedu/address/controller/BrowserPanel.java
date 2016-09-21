package seedu.address.controller;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import seedu.address.commons.FxViewUtil;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.util.LoggerManager;

import java.util.logging.Logger;

/**
 * Manages the AddressBook browser.
 * To begin using this class: call start().
 */
public class BrowserPanel extends UiPart{

    private static Logger logger = LoggerManager.getLogger(BrowserPanel.class);
    private WebView browser;

    public BrowserPanel() {

    }

    @Override
    public void setNode(Node node) {
        //not applicable
    }

    @Override
    public String getFxmlPath() {
        return null; //not applicable
    }

    public static BrowserPanel load(AnchorPane placeholder){
        BrowserPanel browserPanel = new BrowserPanel();
        browserPanel.start();
        browserPanel.configure(placeholder);
        return browserPanel;
    }

    private void configure(AnchorPane placeholder){
        placeholder.setOnKeyPressed(Event::consume); // To prevent triggering events for typing inside the loaded Web page.
        placeholder.getChildren().add(browser);
    }

    /**
     * Starts the browser.
     * Precondition: FX runtime is initialized and in FX application thread.
     */
    public void start() {
        logger.info("Initializing browser");
        browser = new WebView();
    }

    public void loadPersonPage(ReadOnlyPerson person) {
        browser.getEngine().load("https://www.google.com.sg/#safe=off&q=" + person.getName().fullName.replaceAll(" ", "+"));
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

    public WebView getBrowserView() {
        FxViewUtil.applyAnchorBoundaryParameters(browser, 0.0, 0.0, 0.0, 0.0);
        return browser;
    }
}
