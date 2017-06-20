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
        defaultStyleOfCommandBox = new ArrayList<>(commandBox.getStyleClass());
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
    public void handleKeyPress_sequentialExecution() {
        // setup
        commandBox.runCommand(COMMAND_THAT_SUCCEEDS);
        commandBox.runCommand(COMMAND_THAT_FAILS);

        // Previous commands are returned in order
        assertInputHistory(KeyCode.UP, COMMAND_THAT_FAILS, COMMAND_THAT_SUCCEEDS);

        // Pressing `KeyCode.UP` where there are no more previous command
        // to be shown, causes input to remain unchanged.
        assertInputHistory(KeyCode.UP, COMMAND_THAT_SUCCEEDS);

        // Subsequent executed command returned correctly
        assertInputHistory(KeyCode.DOWN, COMMAND_THAT_FAILS);

        // Reset command box if there's no more subsequent executed command.
        assertInputHistory(KeyCode.DOWN, "");
    }

    @Test
    public void handleKeyPress_nonSequentialExecution() {
        // setup
        commandBox.runCommand(COMMAND_THAT_SUCCEEDS);
        commandBox.runCommand(COMMAND_THAT_FAILS);
        guiRobot.push(KeyCode.UP);
        guiRobot.push(KeyCode.UP); // command box displays COMMAND_THAT_SUCCEEDS now
        String anotherCommandThatFails = "foo";
        commandBox.runCommand(anotherCommandThatFails);

        // Previous commands are returned in order
        // despite executing a new command after pushing `KeyCode.UP`.
        assertInputHistory(KeyCode.UP, anotherCommandThatFails, COMMAND_THAT_FAILS, COMMAND_THAT_SUCCEEDS);
    }

    /**
     * Runs a command that fails, then verifies that
     * - the return value of runCommand(...) is false,
     * - the text remains,
     * - the command box has only one ERROR_STYLE_CLASS, with other style classes untouched.
     */
    private void assertBehaviorForFailedCommand() {
        assertFalse(commandBox.runCommand(COMMAND_THAT_FAILS));
        assertEquals(COMMAND_THAT_FAILS, commandBox.getCommandInput());
        assertEquals(errorStyleOfCommandBox, commandBox.getStyleClass());
    }

    /**
     * Runs a command that succeeds, then verifies that
     * - the return value of runCommand(...) is true,
     * - the text is cleared,
     * - the command box does not have any ERROR_STYLE_CLASS, with style classes the same as default.
     */
    private void assertBehaviorForSuccessfulCommand() {
        assertTrue(commandBox.runCommand(COMMAND_THAT_SUCCEEDS));
        assertEquals("", commandBox.getCommandInput());
        assertEquals(defaultStyleOfCommandBox, commandBox.getStyleClass());
    }

    /**
     * Pushes {@code keycode} for {@code expectedCommands#length} number of times, with each time
     * checking that the input in the {@code commandBox} equals to {@code expectedCommands}.
     */
    private void assertInputHistory(KeyCode keycode, String... expectedCommands) {
        for (String expectedCommand : expectedCommands) {
            guiRobot.push(keycode);
            assertEquals(expectedCommand, commandBox.getCommandInput());
        }
    }
}
