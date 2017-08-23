package guitests.guihandles;

import java.net.MalformedURLException;
import java.net.URL;

import guitests.GuiRobot;
import javafx.scene.web.WebView;

/**
 * Helper methods for dealing with {@code WebView}.
 */
public class WebViewUtil {

    /**
     * Returns the {@code URL} of the currently loaded page in the {@code webView}.
     */
    public static URL getLoadedUrl(WebView webView) throws MalformedURLException {
        return new URL(webView.getEngine().getLocation());
    }

    /**
     * Sleeps the thread till the {@code browserPanelHandle}'s {@code WebView} is successfully loaded.
     */
    public static void waitUntilBrowserLoaded(BrowserPanelHandle browserPanelHandle) {
        new GuiRobot().waitForEvent(browserPanelHandle::getIsWebViewLoaded);
        browserPanelHandle.setIsWebViewLoading(false);
        browserPanelHandle.setIsWebViewLoaded(false);
    }
}
