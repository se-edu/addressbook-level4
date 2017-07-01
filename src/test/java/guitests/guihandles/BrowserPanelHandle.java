package guitests.guihandles;

import java.net.MalformedURLException;
import java.net.URL;

import javafx.scene.Node;
import javafx.scene.web.WebView;


/**
 * A handler for the {@code BrowserPanel} of the UI.
 */
public class BrowserPanelHandle extends NodeHandle<Node> {

    public static final String BROWSER_ID = "#browser";

    public BrowserPanelHandle(Node browserPanelNode) {
        super(browserPanelNode);
    }

    /**
     * Returns the {@code URL} of the currently loaded page.
     */
    public URL getLoadedUrl() throws MalformedURLException {
        WebView webView = getChildNode(BROWSER_ID);
        return new URL(webView.getEngine().getLocation());
    }
}
