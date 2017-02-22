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
    public void commandBox_commandSucceeds_normalBehavior() {
        commandBox.runCommand(COMMAND_THAT_SUCCEEDS);
        assertNormalBehavior();
    }

    @Test
    public void commandBox_commandFails() {
        commandBox.runCommand(COMMAND_THAT_FAILS);
        assertErrorBehavior(COMMAND_THAT_FAILS);

        commandBox.runCommand(COMMAND_THAT_SUCCEEDS);
        assertNormalBehavior();

        // run multiple failed commands
        commandBox.runCommand(COMMAND_THAT_FAILS);
        commandBox.runCommand(COMMAND_THAT_FAILS);
        assertErrorBehavior(COMMAND_THAT_FAILS);

        commandBox.runCommand(COMMAND_THAT_SUCCEEDS);
        assertNormalBehavior();
    }

    private void assertNormalBehavior() {
        assertEquals("", commandBox.getCommandInput()); // text cleared
        assertEquals(defaultStyleOfCommandBox, commandBox.getStyleClass()); // style class unchanged
    }

    private void assertErrorBehavior(String commandInput) {
        assertEquals(commandInput, commandBox.getCommandInput()); // text remains
        assertEquals(errorStyleOfCommandBox, commandBox.getStyleClass()); // error style class added
    }

}
