package guitests;

import org.junit.Test;
import seedu.address.exceptions.IllegalValueException;
import seedu.address.testutil.TestUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ClearCommandTest extends GuiTestBase {

    @Test
    public void clear_clearWithoutAnyPreOrPostCommand_addressBookCleared() {
        assertTrue(personListPanel.isListMatching(td.getTypicalPersons()));
        personListPanel.enterCommandAndApply("clear");
        assertEquals(0, personListPanel.getNumberOfPeople());
        assertEquals("Address book has been cleared!", resultDisplay.getText());
    }

    @Test
    public void clear_createAfterClear_addressBookCleared() {
        assertTrue(personListPanel.isListMatching(td.getTypicalPersons()));
        personListPanel.enterCommandAndApply("clear");
        assertEquals(0, personListPanel.getNumberOfPeople());
        assertEquals("Address book has been cleared!", resultDisplay.getText());

        personListPanel.enterCommandAndApply(td.hoon.getCommandString());
        assertTrue(personListPanel.isListMatching(td.hoon));
    }

    @Test
    public void clear_deleteBeforeClear_addressBookCleared() {
        assertTrue(personListPanel.isListMatching(td.getTypicalPersons()));
        personListPanel.enterCommandAndApply("delete 2");
        assertTrue(personListPanel.isListMatching(TestUtil.removePersonsFromList(td.getTypicalPersons(), td.benson)));

        personListPanel.enterCommandAndApply("clear");
        assertEquals(0, personListPanel.getNumberOfPeople());
        assertEquals("Address book has been cleared!", resultDisplay.getText());
    }
}
