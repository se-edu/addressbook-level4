package guitests.guihandles;

import java.net.MalformedURLException;
import java.net.URL;

import javafx.scene.web.WebView;

/**
 * Helper methods for dealing with {@code WebView}.
 */
public class WebViewUtil {

    /**
     * Returns the {@code URL} of the currently loaded page in the {@code webView}.
     */
    public static URL getLoadedUrl(WebView webView) {
        try {
            return new URL(webView.getEngine().getLocation());
        } catch (MalformedURLException mue) {
            throw new AssertionError("webView should not be displaying an invalid URL.", mue);
        }
    }
}
