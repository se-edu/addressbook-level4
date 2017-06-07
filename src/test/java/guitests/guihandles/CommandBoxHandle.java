package guitests.guihandles;

import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.ui.CommandBox;

/**
 * A handle to the Command Box in the GUI.
 */
public class CommandBoxHandle extends NodeHandle {

    private static final String COMMAND_INPUT_FIELD_ID = "#commandTextField";

    public CommandBoxHandle(MainWindowHandle mainWindowHandle) {
        super(mainWindowHandle.getNode(COMMAND_INPUT_FIELD_ID));
    }

    public void enterCommand(String command) {
        setTextField((TextField) getRootNode(), command);
    }

    public String getCommandInput() {
        return ((TextField) getRootNode()).getText();
    }

    public void setTextField(TextField textField, String newText) {
        guiRobot.clickOn(textField);
        guiRobot.interact(() -> textField.setText(newText));
        guiRobot.pauseForHuman(500);
    }

    public String getTextField(TextField textField) {
        return textField.getText();
    }

    public void pressEnter() {
        guiRobot.type(KeyCode.ENTER);
        guiRobot.pauseForHuman(500);
    }

    /**
     * Enters the given command in the Command Box and presses enter.
     * @return true if the command succeeded, false otherwise.
     */
    public boolean runCommand(String command) {
        enterCommand(command);
        pressEnter();
        guiRobot.pauseForHuman(200);
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
