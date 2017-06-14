package guitests.guihandles;

import javafx.collections.ObservableList;
import seedu.address.ui.CommandBox;

/**
 * A handle to the Command Box in the GUI.
 */
public class CommandBoxHandle extends GuiHandle {

    private static final String COMMAND_INPUT_FIELD_ID = "#commandTextField";

    public CommandBoxHandle(String stageTitle) {
        super(stageTitle);
    }

    /**
     * Clicks on the TextField.
     */
    public void clickOnTextField() {
        guiRobot.clickOn(COMMAND_INPUT_FIELD_ID);
    }

    public String getCommandInput() {
        return getTextFieldText(COMMAND_INPUT_FIELD_ID);
    }

    /**
     * Enters the given {@code command} in the {@code CommandBox} and presses enter.
     * @return true if the command succeeded, false otherwise.
     */
    public boolean enterCommand(String command) {
        guiRobot.enterText(getNode(COMMAND_INPUT_FIELD_ID), command);
        guiRobot.pressEnter();
        return !getStyleClass().contains(CommandBox.ERROR_STYLE_CLASS);
    }

    public ObservableList<String> getStyleClass() {
        return getNode(COMMAND_INPUT_FIELD_ID).getStyleClass();
    }
}
