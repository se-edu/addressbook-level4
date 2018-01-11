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

    private BrowserState browserState = BrowserState.READY;

    private URL lastRememberedUrl;

    public BrowserPanelHandle(Node browserPanelNode) {
        super(browserPanelNode);

        WebView webView = getChildNode(BROWSER_ID);
        WebEngine engine = webView.getEngine();
        new GuiRobot().interact(() -> engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.RUNNING) {
                browserState = BrowserState.LOADING;
            } else if (newState == Worker.State.SUCCEEDED) {
                browserState = BrowserState.LOADED;
            } else if (newState == Worker.State.FAILED) {
                browserState = BrowserState.FAILED;
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
     * Returns true if the browser is currently not loading i.e.:
     * 1. The browser is done loading a page.
     * 2. The browser has yet to load any page.
     * 3. The browser tried to load a page and failed.
     */
    public boolean isNoLoadingInProgress() {
        return browserState != BrowserState.LOADING;
    }

    /**
     * Represents a possible loading state of the {@code BrowserPanel}.
     */
    private enum BrowserState {
        READY, LOADED, LOADING, FAILED
    }
}
