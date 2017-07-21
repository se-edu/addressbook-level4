package guitests;

import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.TYPICAL_PERSONS;

import org.junit.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.testutil.PersonUtil;

public class ClearCommandTest extends AddressBookGuiTest {

    @Test
    public void clear() throws Exception {

        //verify a non-empty list can be cleared
        getPersonListPanel().assertListMatching(TYPICAL_PERSONS);
        assertClearCommandSuccess();

        //verify other commands can work after a clear command
        runCommand(PersonUtil.getAddCommand(HOON));
        getPersonListPanel().assertListMatching(HOON);
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
