package guitests.guihandles;

import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.ui.CommandBox;

/**
 * A handle to the {@code CommandBox} in the GUI.
 */
public class CommandBoxHandle extends NodeHandle {

    private static final String COMMAND_INPUT_FIELD_ID = "#commandTextField";

    public CommandBoxHandle(MainWindowHandle mainWindowHandle) {
        super(mainWindowHandle.getChildNode(COMMAND_INPUT_FIELD_ID));
    }

    public String getCommandInput() {
        return ((TextField) getNode()).getText();
    }

    /**
     * Enters the given command in the Command Box and presses enter.
     * @return true if the command succeeded, false otherwise.
     */
    public boolean runCommand(String command) {
        GUI_ROBOT.clickOn(getNode());
        GUI_ROBOT.interact(() -> ((TextField) getNode()).setText(command));
        GUI_ROBOT.pauseForHuman();

        GUI_ROBOT.type(KeyCode.ENTER);
        GUI_ROBOT.pauseForHuman();
        return !getStyleClass().contains(CommandBox.ERROR_STYLE_CLASS);
    }

    public HelpWindowHandle runHelpCommand() {
        runCommand(HelpCommand.COMMAND_WORD);
        return new HelpWindowHandle();
    }

    public ObservableList<String> getStyleClass() {
        return getNode().getStyleClass();
    }
}
