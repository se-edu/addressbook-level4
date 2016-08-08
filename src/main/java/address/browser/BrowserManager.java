package address.browser;

import address.model.datatypes.person.ReadOnlyPerson;
import address.util.AppLogger;
import address.util.LoggerManager;
import commons.FxViewUtil;
import commons.UrlUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.web.WebView;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Manages the AddressBook browser.
 * To begin using this class: call start().
 */
public class BrowserManager {

    private static final String GITHUB_ROOT_URL = "https://github.com/";
    private static final String INVALID_GITHUB_USERNAME_MESSAGE = "Unparsable GitHub Username.";
    private static AppLogger logger = LoggerManager.getLogger(BrowserManager.class);
    private WebView browser;
    private StringProperty selectedPersonUsername;
    private ChangeListener<String> listener;

    public BrowserManager() {
        this.selectedPersonUsername = new SimpleStringProperty();
        initListener();
    }

    /**
     * Initializes the listener for changes in selected person GitHub username.
     */
    private void initListener() {
        listener = (observable,  oldValue,  newValue) -> {
            try {
                URL url = new URL(GITHUB_ROOT_URL + newValue);
                if (!UrlUtil.compareBaseUrls(new URL(browser.getEngine().getLocation()), url)) {
                    browser.getEngine().load(url.toExternalForm());
                }
            } catch (MalformedURLException e) {
                logger.warn("Malformed URL obtained, not attempting to load.");
                if (!newValue.equals("")) {
                    browser.getEngine().loadContent(INVALID_GITHUB_USERNAME_MESSAGE);
                }
            }
        };
    }

    /**
     * Starts the browser manager.
     * Precondition: FX runtime is initialized and in FX application thread.
     */
    public void start() {
        logger.info("Initializing browser");
        browser = new WebView(); //Webview need to be initialize after FX runtime is initialized
    }

    /**
     * Loads the person's profile page to the browser.
     */
    public synchronized void loadProfilePage(ReadOnlyPerson person) {

        selectedPersonUsername.removeListener(listener);

        browser.getEngine().load(person.profilePageUrl().toExternalForm());

        selectedPersonUsername.unbind();
        selectedPersonUsername.bind(person.githubUsernameProperty());
        selectedPersonUsername.addListener(listener);
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeBrowserResources() {
        browser = null;
    }

    public Node getBrowserView() {
        FxViewUtil.applyAnchorBoundaryParameters(browser, 0.0, 0.0, 0.0, 0.0);
        return browser;
    }
}
