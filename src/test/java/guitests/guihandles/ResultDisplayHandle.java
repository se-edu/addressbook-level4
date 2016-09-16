package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import seedu.address.TestApp;
import seedu.address.controller.ResultDisplay;

/**
 * A handler for the ResultDisplay of the UI
 */
public class ResultDisplayHandle extends GuiHandle {

    public ResultDisplayHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public String getText() {
        return getResultDisplay().getText();
    }

    private TextArea getResultDisplay() {
        return (TextArea) getNode("#" + ResultDisplay.RESULT_DISPLAY_ID);
    }
}
