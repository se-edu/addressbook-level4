package guitests.guihandles;

import java.net.MalformedURLException;
import java.net.URL;

import javafx.scene.web.WebView;
import seedu.address.TestApp;

/**
 * A handler for the {@code BrowserPanel} of the UI.
 */
public class BrowserPanelHandle extends NodeHandle<Node> {

    public static final String BROWSER_ID = "#browser";

    public BrowserPanelHandle(Node browserPanelNode) {
        super(browserPanelNode);
    }

    /**
     * Clicks on the WebView.
     */
    public void clickOnWebView() {
        guiRobot.clickOn(BROWSER_ID);
    }

    /**
     * Returns the {@code URL} of the currently loaded page.
     */
    public URL getLoadedUrl() throws MalformedURLException {
        WebView webView = getNode(BROWSER_ID);
        return new URL(webView.getEngine().getLocation());
    }
}
