package guitests.guihandles;

/**
 * A handler for the {@code BrowserPanel} of the UI.
 */
public class BrowserPanelHandle extends NodeHandle {

    private static final String BROWSER_ID = "#browser";

    public BrowserPanelHandle(MainWindowHandle mainWindowHandle) {
        super(mainWindowHandle.getChildNode(BROWSER_ID));
    }
}
