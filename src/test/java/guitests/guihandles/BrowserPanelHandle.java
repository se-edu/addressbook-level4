package guitests.guihandles;

import javafx.scene.web.WebView;
import seedu.address.TestApp;

/**
 * A handler for the BrowserPanel of the UI
 */
public class BrowserPanelHandle extends GuiHandle {

    private static final String BROWSER_ID = "#browser";

    public BrowserPanelHandle() {
        super(TestApp.APP_TITLE);
    }

    /**
     * Clicks on the WebView.
     */
    public void clickOnWebView() {
        guiRobot.clickOn(BROWSER_ID);
    }

    /**
     * Get the URL of the currently loaded page.
     */
    public String getLoadedUrl() {
        WebView webView = getNode(BROWSER_ID);
        return webView.getEngine().getLocation();
    }
}
