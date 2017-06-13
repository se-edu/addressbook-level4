package guitests.guihandles;

import javafx.scene.Node;

/**
 * A handler for the {@code BrowserPanel} of the UI.
 */
public class BrowserPanelHandle extends NodeHandle {

    public static final String BROWSER_ID = "#browser";

    public BrowserPanelHandle(Node browserPanelNode) {
        super(browserPanelNode);
    }
}
