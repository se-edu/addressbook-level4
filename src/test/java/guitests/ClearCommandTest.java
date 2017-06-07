package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.testutil.PersonUtil;

public class ClearCommandTest extends AddressBookGuiTest {

    @Test
    public void clear() {

        //verify a non-empty list can be cleared
        assertTrue(mainWindowHandle.getPersonListPanel().isListMatching(td.getTypicalPersons()));
        assertClearCommandSuccess();

        //verify other commands can work after a clear command
        mainWindowHandle.getCommandBox().runCommand(PersonUtil.getAddCommand(td.hoon));
        assertTrue(mainWindowHandle.getPersonListPanel().isListMatching(td.hoon));
        mainWindowHandle.getCommandBox().runCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertListSize(0);

        //verify clear command works when the list is empty
        assertClearCommandSuccess();
    }

    private void assertClearCommandSuccess() {
        mainWindowHandle.getCommandBox().runCommand(ClearCommand.COMMAND_WORD);
        assertListSize(0);
        assertResultMessage("Address book has been cleared!");
    }
}
