package seedu.address.browser;

import javafx.scene.Node;
import javafx.scene.web.WebView;
import seedu.address.commons.FxViewUtil;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.util.LoggerManager;

import java.util.logging.Logger;

/**
 * Manages the AddressBook browser.
 * To begin using this class: call start().
 */
public class BrowserManager {

    private static final String GITHUB_ROOT_URL = "https://github.com/";
    private static final String INVALID_GITHUB_USERNAME_MESSAGE = "Unparsable GitHub Username.";
    private static Logger logger = LoggerManager.getLogger(BrowserManager.class);
    private WebView browser;

    public BrowserManager() {

    }

    /**
     * Starts the browser manager.
     * Precondition: FX runtime is initialized and in FX application thread.
     */
    public void start() {
        logger.info("Initializing browser");
        browser = new WebView(); //Webview need to be initialize after FX runtime is initialized
    }

    public void loadPersonPage(ReadOnlyPerson person) {
        browser.getEngine().load("https://www.google.com.sg/#safe=off&q=" + person.getName().fullName.replaceAll(" ", "+"));
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeBrowserResources() {
        browser = null;
    }

    public WebView getBrowserView() {
        FxViewUtil.applyAnchorBoundaryParameters(browser, 0.0, 0.0, 0.0, 0.0);
        return browser;
    }
}
