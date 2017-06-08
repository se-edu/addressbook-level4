package guitests.guihandles;

/**
 * A handler for the BrowserPanel of the UI
 */
public class BrowserPanelHandle extends NodeHandle {

    public static final String BROWSER_ID = "#browser";

    public BrowserPanelHandle(MainWindowHandle mainWindowHandle) {
        super(mainWindowHandle.getNode(BROWSER_ID));
    }

}
