package guitests.guihandles;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.ui.CommandBox;

/**
 * A handle to the {@code CommandBox} in the GUI.
 */
public class CommandBoxHandle extends NodeHandle {

    public static final String COMMAND_INPUT_FIELD_ID = "#commandTextField";

    public CommandBoxHandle(Node commandBoxNode) {
        super(commandBoxNode);
    }

    /**
     * Returns the text in the command box.
     */
    public String getCommandInput() {
        return ((TextField) getRootNode()).getText();
    }

    /**
     * Enters the given command in the Command Box and presses enter.
     * @return true if the command succeeded, false otherwise.
     */
    public boolean runCommand(String command) {
        guiRobot.clickOn(getRootNode());
        guiRobot.interact(() -> ((TextField) getRootNode()).setText(command));
        guiRobot.pauseForHuman();

        guiRobot.type(KeyCode.ENTER);
        guiRobot.pauseForHuman();
        return !getStyleClass().contains(CommandBox.ERROR_STYLE_CLASS);
    }

    /**
     * Types {@code HelpCommand#COMMAND_WORD} and executes it.
     * @return the handle of the help window.
     */
    public HelpWindowHandle runHelpCommand() {
        runCommand(HelpCommand.COMMAND_WORD);
        return new HelpWindowHandle();
    }

    /**
     * Gets the list of style classes present in the command box.
     */
    public ObservableList<String> getStyleClass() {
        return getRootNode().getStyleClass();
    }
}
