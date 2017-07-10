package guitests.guihandles;

import java.net.MalformedURLException;
import java.net.URL;

import javafx.scene.web.WebView;

/**
 * Helper methods for dealing with {@code WebView}.
 */
public class WebViewUtil {

    /**
     * Returns the URL of the currently loaded page in the {@code webView}.
     */
    public static URL getLoadedUrl(WebView webView) throws MalformedURLException {
        return new URL(webView.getEngine().getLocation());
    }
}
