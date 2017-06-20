package guitests.guihandles;

import guitests.GuiRobot;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import seedu.address.logic.commands.HelpCommand;
import seedu.address.ui.CommandBox;

/**
 * A handle to the Command Box in the GUI.
 */
public class CommandBoxHandle extends GuiHandle {

    private static final String COMMAND_INPUT_FIELD_ID = "#commandTextField";

    public CommandBoxHandle(GuiRobot guiRobot, Stage primaryStage, String stageTitle) {
        super(guiRobot, primaryStage, stageTitle);
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

    /**
     * Enters the given command in the Command Box and presses enter.
     * @return true if the command succeeded, false otherwise.
     */
    public boolean runCommand(String command) {
        enterCommand(command);
        guiRobot.type(KeyCode.ENTER);
        guiRobot.sleep(500);
        guiRobot.sleep(200); //Give time for the command to take effect
        return !getStyleClass().contains(CommandBox.ERROR_STYLE_CLASS);
    }

    public HelpWindowHandle runHelpCommand() {
        enterCommand(HelpCommand.COMMAND_WORD);
        guiRobot.type(KeyCode.ENTER);
        guiRobot.sleep(500);
        return new HelpWindowHandle(guiRobot, primaryStage);
    }

    public ObservableList<String> getStyleClass() {
        return getNode(COMMAND_INPUT_FIELD_ID).getStyleClass();
    }
}
