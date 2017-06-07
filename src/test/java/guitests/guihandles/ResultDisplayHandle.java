package guitests.guihandles;

import javafx.scene.control.TextArea;

/**
 * A handler for the ResultDisplay of the UI
 */
public class ResultDisplayHandle extends NodeHandle {

    public static final String RESULT_DISPLAY_ID = "#resultDisplay";

    public ResultDisplayHandle(MainWindowHandle mainWindowHandle) {
        super(mainWindowHandle.getNode(RESULT_DISPLAY_ID));
    }

    public String getText() {
        return ((TextArea) getRootNode()).getText();
    }
}
