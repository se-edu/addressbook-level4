package guitests.guihandles;

import javafx.scene.control.TextArea;
import seedu.address.TestApp;

/**
 * A handler for the ResultDisplay of the UI
 */
public class ResultDisplayHandle extends GuiHandle {

    public static final String RESULT_DISPLAY_ID = "#resultDisplay";

    public ResultDisplayHandle() {
        super(TestApp.APP_TITLE);
    }

    /**
     * Clicks on the TextArea.
     */
    public void clickOnTextArea() {
        guiRobot.clickOn(RESULT_DISPLAY_ID);
    }

    public String getText() {
        return getResultDisplay().getText();
    }

    private TextArea getResultDisplay() {
        return getNode(RESULT_DISPLAY_ID);
    }
}
