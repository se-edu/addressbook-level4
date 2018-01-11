package guitests.guihandles;

import java.net.URL;

import guitests.GuiRobot;
import javafx.concurrent.Worker;
import javafx.scene.Node;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * A handler for the {@code BrowserPanel} of the UI.
 */
public class BrowserPanelHandle extends NodeHandle<Node> {

    public static final String BROWSER_ID = "#browser";
    public static final String GOOGLE_TOO_MUCH_TRAFFIC_URL_PREFIX = "https://ipv4.google.com/sorry/";
    public static final String GOOGLE_TOO_MUCH_TRAFFIC_ERROR_MESSAGE = "Google has detected too much traffic from "
            + "your network, thus the expected web page is not loaded.";

    private boolean isWebViewLoaded = true;
    private boolean isLoadingFailed = false;

    private URL lastRememberedUrl;

    public BrowserPanelHandle(Node browserPanelNode) {
        super(browserPanelNode);

        WebView webView = getChildNode(BROWSER_ID);
        WebEngine engine = webView.getEngine();
        new GuiRobot().interact(() -> engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.RUNNING) {
                isWebViewLoaded = false;
                isLoadingFailed = false;
            } else if (newState == Worker.State.SUCCEEDED) {
                isWebViewLoaded = true;
            } else if (newState == Worker.State.FAILED) {
                isLoadingFailed = true;
            }
        }));
    }

    /**
     * Returns the {@code URL} of the currently loaded page.
     */
    public URL getLoadedUrl() {
        return WebViewUtil.getLoadedUrl(getChildNode(BROWSER_ID));
    }

    /**
     * Remembers the {@code URL} of the currently loaded page.
     */
    public void rememberUrl() {
        lastRememberedUrl = getLoadedUrl();
    }

    /**
     * Returns true if the current {@code URL} is different from the value remembered by the most recent
     * {@code rememberUrl()} call.
     */
    public boolean isUrlChanged() {
        return !lastRememberedUrl.equals(getLoadedUrl());
    }

    /**
     * Returns true if:
     * 1. The browser is done loading a page
     * 2. The browser has yet to load any page.
     * 3. The browser tried to load a page and failed.
     */
    public boolean isLoadingAttemptCompleted() {
        return isWebViewLoaded || isLoadingFailed;
    }
}
