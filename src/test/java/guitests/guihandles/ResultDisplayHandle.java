package guitests.guihandles;

import java.util.List;

import javafx.scene.control.TextArea;

/**
 * A handler for the {@code ResultDisplay} of the UI
 */
public class ResultDisplayHandle extends NodeHandle<TextArea> {

    public static final String RESULT_DISPLAY_ID = "#resultDisplay";

    public ResultDisplayHandle(TextArea resultDisplayNode) {
        super(resultDisplayNode);
    }

    /**
     * Returns the text in the result display.
     */
    public String getText() {
        return getRootNode().getText();
    }

    /**
     * Returns the list of style classes present in the result display.
     */
    public List<String> getStyleClass() {
        return getRootNode().getStyleClass();
    }
}
