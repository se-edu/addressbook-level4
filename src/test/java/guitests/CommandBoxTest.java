package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import seedu.address.ui.CommandBox;

public class CommandBoxTest extends AddressBookGuiTest {

    private static final String COMMAND_THAT_SUCCEEDS = "select 3";
    private static final String COMMAND_THAT_FAILS = "invalid command";

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

        // verify that ERROR_STYLE_CLASS is added
        assertBehaviorForFailedCommand();
    }

    @Test
    public void commandBox_startingWithFailedCommand() {
        assertBehaviorForFailedCommand();

        // verify that ERROR_STYLE_CLASS is removed
        assertBehaviorForSuccessfulCommand();

        // verify that style is changed correctly even after multiple consecutive successful/failed commands
        assertBehaviorForSuccessfulCommand();
        assertBehaviorForFailedCommand();
        assertBehaviorForFailedCommand();
        assertBehaviorForSuccessfulCommand();
    }

    private void assertBehaviorForFailedCommand() {
        commandBox.runCommand(COMMAND_THAT_FAILS);

        // verify that text remains
        assertEquals(COMMAND_THAT_FAILS, commandBox.getCommandInput());

        // verify that command box has only *one* ERROR_STYLE_CLASS, and other style classes are untouched
        assertEquals(errorStyleOfCommandBox, commandBox.getStyleClass());
    }

    private void assertBehaviorForSuccessfulCommand() {
        commandBox.runCommand(COMMAND_THAT_SUCCEEDS);

        // verify that text is cleared
        assertEquals("", commandBox.getCommandInput());

        // verify that command box does not have any ERROR_STYLE_CLASS, with style classes the same as default
        assertEquals(defaultStyleOfCommandBox, commandBox.getStyleClass());
    }

}
