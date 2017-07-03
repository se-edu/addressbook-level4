package guitests.guihandles;

import javafx.scene.web.WebView;

/**
 * Provides a handle to the help window of the app.
 */
public class HelpWindowHandle extends GuiHandle {

    private static final String HELP_WINDOW_TITLE = "Help";
    private static final String HELP_WINDOW_ROOT_FIELD_ID = "#helpWindowRoot";

    private static final String HELP_WINDOW_BROWSER_ID = "#browser";

    public HelpWindowHandle() {
        super(HELP_WINDOW_TITLE);
        guiRobot.pauseForHuman();
    }

    public boolean isWindowOpen() {
        return guiRobot.lookup(HELP_WINDOW_ROOT_FIELD_ID).tryQuery().isPresent();
    }

    public void closeWindow() {
        super.closeWindow();
        guiRobot.pauseForHuman();
    }

    /**
     * Get the URL of the currently loaded page.
     */
    public String getLoadedUrl() {
        WebView webView = getNode(HELP_WINDOW_BROWSER_ID);
        return webView.getEngine().getLocation();
    }
}
