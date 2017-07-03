package guitests.guihandles;

import java.net.MalformedURLException;
import java.net.URL;

import javafx.scene.web.WebView;

/**
 * A handle to the {@code HelpWindow} of the application.
 */
public class HelpWindowHandle extends StageHandle {

    private static final String HELP_WINDOW_BROWSER_ID = "#browser";

    public HelpWindowHandle() {
        super(HELP_WINDOW_TITLE);
        guiRobot.pauseForHuman();
    }

    public HelpWindowHandle(Stage helpWindowStage) {
        super(helpWindowStage);
    }

    /**
     * Returns true if a help window is currently present in the application.
     */
    public static boolean isWindowPresent() {
        return new GuiRobot().isWindowShown(HELP_WINDOW_TITLE);
    }

    /**
     * Returns the URL of the currently loaded page.
     */
    public URL getLoadedUrl() throws MalformedURLException {
        WebView webView = getNode(HELP_WINDOW_BROWSER_ID);
        return new URL(webView.getEngine().getLocation());
    }
}
