package guitests;

import org.junit.Test;
import seedu.address.testutil.TestUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ClearCommandTest extends AddressBookGuiTest {

    @Test
    public void clear_clearWithoutAnyPreOrPostCommand_addressBookCleared() {
        assertTrue(personListPanel.isListMatching(td.getTypicalPersons()));
        runCommand("clear");
        assertEquals(0, personListPanel.getNumberOfPeople());
        assertResultMessage("Address book has been cleared!");
    }

    @Test
    public void clear_createAfterClear_addressBookCleared() {
        assertTrue(personListPanel.isListMatching(td.getTypicalPersons()));
        runCommand("clear");
        assertEquals(0, personListPanel.getNumberOfPeople());
        assertResultMessage("Address book has been cleared!");

        runCommand(td.hoon.getCommandString());
        assertTrue(personListPanel.isListMatching(td.hoon));
    }

    @Test
    public void clear_deleteBeforeClear_addressBookCleared() {
        assertTrue(personListPanel.isListMatching(td.getTypicalPersons()));
        runCommand("delete 2");
        assertTrue(personListPanel.isListMatching(TestUtil.removePersonsFromList(td.getTypicalPersons(), td.benson)));

        runCommand("clear");
        assertEquals(0, personListPanel.getNumberOfPeople());
        assertResultMessage("Address book has been cleared!");
    }
}
