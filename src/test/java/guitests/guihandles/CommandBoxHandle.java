package guitests.guihandles;

import static guitests.GuiRobotUtil.SHORT_WAIT;

import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import seedu.address.ui.CommandBox;

/**
 * A handle to the {@code CommandBox} in the GUI.
 */
public class CommandBoxHandle extends NodeHandle {

    private static final String COMMAND_INPUT_FIELD_ID = "#commandTextField";

    public CommandBoxHandle(MainWindowHandle mainWindowHandle) {
        super(mainWindowHandle.getChildNode(COMMAND_INPUT_FIELD_ID));
    }

    public String getInput() {
        return ((TextField) getNode()).getText();
    }

    /**
     * Enters the given {@code command} in the {@code CommandBox} and presses enter.
     * @return true if the command succeeded, false otherwise.
     */
    public boolean enterCommand(String command) {
        GUI_ROBOT.enterText((TextField) getNode(), command);
        GUI_ROBOT.pressEnter();
        GUI_ROBOT.pauseForHuman(SHORT_WAIT);
        return !getStyleClass().contains(CommandBox.ERROR_STYLE_CLASS);
    }

    public ObservableList<String> getStyleClass() {
        return getNode().getStyleClass();
    }
}
