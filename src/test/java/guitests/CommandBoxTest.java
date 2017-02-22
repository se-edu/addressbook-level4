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
    public void commandBox_commandSucceeds() {
        // verify that no error style class is added
        assertBehaviorForSuccessfulCommand();
    }

    @Test
    public void commandBox_commandFails() {
        assertBehaviorForFailedCommand();

        // verify that error style class is removed
        assertBehaviorForSuccessfulCommand();

        // verify that at most one error style class is added for failed commands
        assertBehaviorForFailedCommand();
        assertBehaviorForFailedCommand();

        assertBehaviorForSuccessfulCommand();
    }

    private void assertBehaviorForFailedCommand() {
        commandBox.runCommand(COMMAND_THAT_FAILS);

        assertEquals(COMMAND_THAT_FAILS, commandBox.getCommandInput()); // text remains
        assertEquals(errorStyleOfCommandBox, commandBox.getStyleClass()); // contains an error style class
    }

    private void assertBehaviorForSuccessfulCommand() {
        commandBox.runCommand(COMMAND_THAT_SUCCEEDS);

        assertEquals("", commandBox.getCommandInput()); // text cleared
        assertEquals(defaultStyleOfCommandBox, commandBox.getStyleClass()); // does not contain any error style class
    }

}
