package guitests;

import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import org.junit.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.testutil.PersonUtil;

public class ClearCommandTest extends AddressBookGuiTest {

    @Test
    public void clear() throws Exception {

        //verify a non-empty list can be cleared
        assertTrue(getPersonListPanel().isListMatching(getTypicalPersons()));
        assertClearCommandSuccess();

        //verify other commands can work after a clear command
        runCommand(PersonUtil.getAddCommand(HOON));
        assertTrue(getPersonListPanel().isListMatching(HOON));
        runCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertListSize(0);

        //verify clear command works when the list is empty
        assertClearCommandSuccess();
    }

    private void assertClearCommandSuccess() {
        runCommand(ClearCommand.COMMAND_WORD);
        assertListSize(0);
        assertResultMessage("Address book has been cleared!");
    }
}
