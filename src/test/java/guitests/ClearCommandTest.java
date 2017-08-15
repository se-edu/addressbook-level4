package guitests;

import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;
import static seedu.address.ui.testutil.GuiTestAssert.assertListMatching;
import static seedu.address.ui.testutil.GuiTestAssert.assertListSize;
import static seedu.address.ui.testutil.GuiTestAssert.assertResultMessage;

import org.junit.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.testutil.PersonUtil;

public class ClearCommandTest extends AddressBookGuiTest {

    @Test
    public void clear() {

        //verify a non-empty list can be cleared
        assertListMatching(getPersonListPanel(), getTypicalPersons());
        assertClearCommandSuccess();

        //verify other commands can work after a clear command
        runCommand(PersonUtil.getAddCommand(HOON));
        assertListMatching(getPersonListPanel(), HOON);
        runCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertListSize(getPersonListPanel(), 0);

        //verify clear command works when the list is empty
        assertClearCommandSuccess();
    }

    private void assertClearCommandSuccess() {
        runCommand(ClearCommand.COMMAND_WORD);
        assertListSize(getPersonListPanel(), 0);
        assertResultMessage(getResultDisplay(), "Address book has been cleared!");
    }
}
