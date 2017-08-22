package systemtests;

import org.junit.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class ClearCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void clear() throws Exception {
        /* Case: clear non-empty address book, command with leading spaces and trailing alphanumeric characters and
        * spaces -> cleared */
        assertCommandSuccess("   " + ClearCommand.COMMAND_WORD + " ab12   ");

        /* Case: clear empty address book -> cleared */
        assertCommandSuccess(ClearCommand.COMMAND_WORD);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code ClearCommand#MESSAGE_SUCCESS} and the model related components equal to an empty model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class, the status bar's sync status changes, the browser
     * url and selected card remains unchanged.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command) throws Exception {
        executeCommand(command);
        assertApplicationDisplaysExpected("", ClearCommand.MESSAGE_SUCCESS, new ModelManager());
        assertSelectedCardUnchanged();
        assertCommandBoxStyleDefault();
        assertStatusBarUnchangedExceptSyncStatus();

        clockRule.setInjectedClockToCurrentTime();
    }
}
