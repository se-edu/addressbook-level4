package guitests.guihandles;

import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import seedu.address.logic.commands.HelpCommand;
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

    public void enterCommand(String command) {
        TextField textField = getNode(COMMAND_INPUT_FIELD_ID);
        guiRobot.clickOn(textField);
        guiRobot.interact(() -> textField.setText(command));
        guiRobot.sleep(500);
    }

    public String getCommandInput() {
        return getTextFieldText(COMMAND_INPUT_FIELD_ID);
    }

    public void pressEnter() {
        guiRobot.type(KeyCode.ENTER);
        guiRobot.sleep(500);
    }

    /**
     * Enters the given command in the Command Box and presses enter.
     * @return true if the command succeeded, false otherwise.
     */
    public boolean runCommand(String command) {
        enterCommand(command);
        pressEnter();
        guiRobot.sleep(200); //Give time for the command to take effect
        return !getStyleClass().contains(CommandBox.ERROR_STYLE_CLASS);
    }

    public HelpWindowHandle runHelpCommand() {
        enterCommand(HelpCommand.COMMAND_WORD);
        pressEnter();
        return new HelpWindowHandle();
    }

    public ObservableList<String> getStyleClass() {
        return getNode(COMMAND_INPUT_FIELD_ID).getStyleClass();
    }
}
