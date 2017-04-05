package guitests.guihandles;

import java.net.MalformedURLException;
import java.net.URL;

import guitests.GuiRobot;
import javafx.stage.Stage;

/**
 * A handle to the {@code HelpWindow} of the application.
 */
public class HelpWindowHandle extends StageHandle {

    public static final String HELP_WINDOW_TITLE = "Help";

    private static final String HELP_WINDOW_BROWSER_ID = "#browser";

    public HelpWindowHandle(Stage helpWindowStage) {
        super(helpWindowStage);
    }

    /**
     *
     */
    public void closeWindow() {
        super.closeWindow();
        guiRobot.sleep(500);
    }

    /**
     * Returns the {@code URL} of the currently loaded page.
     */
    public URL getLoadedUrl() throws MalformedURLException {
        return WebViewUtil.getLoadedUrl(getChildNode(HELP_WINDOW_BROWSER_ID));
    }
}
