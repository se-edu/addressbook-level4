package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import javafx.scene.input.KeyCode;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.ui.CommandBox;

public class CommandBoxTest extends AddressBookGuiTest {

    private static final String COMMAND_THAT_SUCCEEDS = SelectCommand.COMMAND_WORD + " 3";
    private static final String COMMAND_THAT_FAILS = "invalid command";

    private GuiRobot guiRobot = new GuiRobot();
    private ArrayList<String> defaultStyleOfCommandBox;
    private ArrayList<String> errorStyleOfCommandBox;

    @Before
    public void setUp() {
        defaultStyleOfCommandBox = new ArrayList<>(getCommandBox().getStyleClass());
        assertFalse("CommandBox default style classes should not contain error style class.",
                    defaultStyleOfCommandBox.contains(CommandBox.ERROR_STYLE_CLASS));

        // build style class for error
        errorStyleOfCommandBox = new ArrayList<>(defaultStyleOfCommandBox);
        errorStyleOfCommandBox.add(CommandBox.ERROR_STYLE_CLASS);
    }

    @Test
    public void commandBox_startingWithSuccessfulCommand() {
        assertBehaviorForSuccessfulCommand();
        assertBehaviorForFailedCommand();
    }

    @Test
    public void commandBox_startingWithFailedCommand() {
        assertBehaviorForFailedCommand();
        assertBehaviorForSuccessfulCommand();

        // verify that style is changed correctly even after multiple consecutive successful/failed commands
        assertBehaviorForSuccessfulCommand();
        assertBehaviorForFailedCommand();
        assertBehaviorForFailedCommand();
        assertBehaviorForSuccessfulCommand();
    }

    @Test
    public void commandBox_handleKeyPress() {
        getCommandBox().runCommand(COMMAND_THAT_FAILS);
        assertEquals(errorStyleOfCommandBox, getCommandBox().getStyleClass());
        guiRobot.push(KeyCode.ESCAPE);
        assertEquals(errorStyleOfCommandBox, getCommandBox().getStyleClass());

        guiRobot.push(KeyCode.A);
        guiRobot.pauseForHuman();
        assertEquals(defaultStyleOfCommandBox, getCommandBox().getStyleClass());
    }

    @Test
    public void handleKeyPress_startingWithUp() {
        // empty history
        assertInputHistory(KeyCode.UP, "");
        assertInputHistory(KeyCode.DOWN, "");

        // one command
        getCommandBox().runCommand(COMMAND_THAT_SUCCEEDS);
        assertInputHistory(KeyCode.UP, COMMAND_THAT_SUCCEEDS);
        assertInputHistory(KeyCode.DOWN, "");

        // two commands
        getCommandBox().runCommand(COMMAND_THAT_FAILS);
        assertInputHistory(KeyCode.UP, COMMAND_THAT_SUCCEEDS);
        assertInputHistory(KeyCode.UP, COMMAND_THAT_SUCCEEDS);
        assertInputHistory(KeyCode.DOWN, COMMAND_THAT_FAILS);
        assertInputHistory(KeyCode.DOWN, "");
        assertInputHistory(KeyCode.DOWN, "");
        assertInputHistory(KeyCode.UP, COMMAND_THAT_FAILS);

        // insert command in the middle of retrieving previous commands
        guiRobot.push(KeyCode.UP);
        String thirdCommand = "list";
        getCommandBox().runCommand(thirdCommand);
        assertInputHistory(KeyCode.UP, thirdCommand);
        assertInputHistory(KeyCode.UP, COMMAND_THAT_FAILS);
        assertInputHistory(KeyCode.UP, COMMAND_THAT_SUCCEEDS);
        assertInputHistory(KeyCode.DOWN, COMMAND_THAT_FAILS);
        assertInputHistory(KeyCode.DOWN, thirdCommand);
        assertInputHistory(KeyCode.DOWN, "");
    }

    @Test
    public void handleKeyPress_startingWithDown() {
        // empty history
        assertInputHistory(KeyCode.DOWN, "");
        assertInputHistory(KeyCode.UP, "");

        // one command
        getCommandBox().runCommand(COMMAND_THAT_SUCCEEDS);
        assertInputHistory(KeyCode.DOWN, "");
        assertInputHistory(KeyCode.UP, COMMAND_THAT_SUCCEEDS);

        // two commands
        getCommandBox().runCommand(COMMAND_THAT_FAILS);
        assertInputHistory(KeyCode.DOWN, "");
        assertInputHistory(KeyCode.UP, COMMAND_THAT_FAILS);

        // insert command in the middle of retrieving previous commands
        guiRobot.push(KeyCode.UP);
        String thirdCommand = "list";
        getCommandBox().runCommand(thirdCommand);
        assertInputHistory(KeyCode.DOWN, "");
        assertInputHistory(KeyCode.UP, thirdCommand);
    }

    /**
     * Runs a command that fails, then verifies that
     * - the return value of runCommand(...) is false,
     * - the text remains,
     * - the command box has only one ERROR_STYLE_CLASS, with other style classes untouched.
     */
    private void assertBehaviorForFailedCommand() {
        assertFalse(runCommand(COMMAND_THAT_FAILS));
        assertEquals(COMMAND_THAT_FAILS, getCommandBox().getCommandInput());
        assertEquals(errorStyleOfCommandBox, getCommandBox().getStyleClass());
    }

    /**
     * Runs a command that succeeds, then verifies that
     * - the return value of runCommand(...) is true,
     * - the text is cleared,
     * - the command box does not have any ERROR_STYLE_CLASS, with style classes the same as default.
     */
    private void assertBehaviorForSuccessfulCommand() {
        assertTrue(runCommand(COMMAND_THAT_SUCCEEDS));
        assertEquals("", getCommandBox().getCommandInput());
        assertEquals(defaultStyleOfCommandBox, getCommandBox().getStyleClass());
    }

    /**
     * Pushes {@code keycode} and checks that the input in the {@code commandBox} equals to {@code expectedCommand}.
     */
    private void assertInputHistory(KeyCode keycode, String expectedCommand) {
        guiRobot.push(keycode);
        assertEquals(expectedCommand, getCommandBox().getCommandInput());
    }
}
