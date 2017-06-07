package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.SelectCommand;
import seedu.address.ui.CommandBox;

public class CommandBoxTest extends AddressBookGuiTest {

    private static final String COMMAND_THAT_SUCCEEDS = SelectCommand.COMMAND_WORD + " 3";
    private static final String COMMAND_THAT_FAILS = "invalid command";

    private ArrayList<String> defaultStyleOfCommandBox;
    private ArrayList<String> errorStyleOfCommandBox;

    @Before
    public void setUp() {
        defaultStyleOfCommandBox = new ArrayList<>(mainWindowHandle.getCommandBox().getStyleClass());
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

    /**
     * Runs a command that fails, then verifies that
     * - the return value of runCommand(...) is false,
     * - the text remains,
     * - the command box has only one ERROR_STYLE_CLASS, with other style classes untouched.
     */
    private void assertBehaviorForFailedCommand() {
        assertFalse(mainWindowHandle.getCommandBox().runCommand(COMMAND_THAT_FAILS));
        assertEquals(COMMAND_THAT_FAILS, mainWindowHandle.getCommandBox().getCommandInput());
        assertEquals(errorStyleOfCommandBox, mainWindowHandle.getCommandBox().getStyleClass());
    }

    /**
     * Runs a command that succeeds, then verifies that
     * - the return value of runCommand(...) is true,
     * - the text is cleared,
     * - the command box does not have any ERROR_STYLE_CLASS, with style classes the same as default.
     */
    private void assertBehaviorForSuccessfulCommand() {
        assertTrue(mainWindowHandle.getCommandBox().runCommand(COMMAND_THAT_SUCCEEDS));
        assertEquals("", mainWindowHandle.getCommandBox().getCommandInput());
        assertEquals(defaultStyleOfCommandBox, mainWindowHandle.getCommandBox().getStyleClass());
    }

}
