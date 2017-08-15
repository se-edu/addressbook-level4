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

    private boolean isWebViewLoaded = false;

    private URL lastRememberedUrl;

    public BrowserPanelHandle(Node browserPanelNode) {
        super(browserPanelNode);

        WebView webView = getChildNode(BROWSER_ID);
        WebEngine engine = webView.getEngine();
        new GuiRobot().interact(() -> engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                isWebViewLoaded = true;
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

    public boolean getIsWebViewLoaded() {
        return isWebViewLoaded;
    }

    public void setIsWebViewLoaded(boolean isWebViewLoaded) {
        this.isWebViewLoaded = isWebViewLoaded;
    }
}
