package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.TextArea;

/**
 * A handler for the ResultDisplay of the UI
 */
public class ResultDisplayHandle extends NodeHandle {

    public static final String RESULT_DISPLAY_ID = "#resultDisplay";

    public ResultDisplayHandle(Node resultDisplayHandle) {
        super(resultDisplayHandle);
    }

    /**
     * Gets the text in the result display.
     */
    public String getText() {
        return ((TextArea) getRootNode()).getText();
    }
}
