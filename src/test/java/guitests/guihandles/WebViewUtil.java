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
     * If the {@code browserPanelHandle}'s {@code WebView} is loading, sleeps the thread till it is successfully loaded.
     */
    public static void waitUntilBrowserLoaded(BrowserPanelHandle browserPanelHandle) {
        new GuiRobot().waitForEvent(browserPanelHandle::isLoaded);
    }
}
