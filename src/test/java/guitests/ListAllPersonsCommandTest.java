package guitests;

import org.junit.Test;
import seedu.address.exceptions.IllegalValueException;
import seedu.address.testutil.TestUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ListAllPersonsCommandTest extends GuiTestBase {

    @Test
    public void listAllPerson_afterNoResultSearch_showAllPersons() {
        personListPanel.enterCommandAndApply("find Mark");
        assertEquals(0, personListPanel.getNumberOfPeople());
        assertEquals("0 persons listed!", resultDisplay.getText());

        personListPanel.enterCommandAndApply("list");
        assertTrue(personListPanel.isListMatching(td.getTypicalPersons()));
        assertEquals("Listed all persons", resultDisplay.getText());
    }

    @Test
    public void listAllPerson_afterPositiveSearch_showAllPersons() {
        personListPanel.enterCommandAndApply("find Meier");
        assertEquals(2, personListPanel.getNumberOfPeople());
        assertEquals("2 persons listed!", resultDisplay.getText());
        assertTrue(personListPanel.isListMatching(td.benson, td.daniel));

        personListPanel.enterCommandAndApply("list");
        assertTrue(personListPanel.isListMatching(td.getTypicalPersons()));
        assertEquals("Listed all persons", resultDisplay.getText());
    }

    @Test
    public void listAllPerson_noSearch_showAllPersons() {
        assertTrue(personListPanel.isListMatching(td.getTypicalPersons()));
        personListPanel.enterCommandAndApply("list");
        assertTrue(personListPanel.isListMatching(td.getTypicalPersons()));
        assertEquals("Listed all persons", resultDisplay.getText());
    }

    @Test
    public void listAllPerson_afterDelete_showCorrectPersonsAfterDeletion() {
        assertTrue(personListPanel.isListMatching(td.getTypicalPersons()));
        personListPanel.enterCommandAndApply("delete 1");
        assertTrue(personListPanel.isListMatching(TestUtil.removePersonsFromList(td.getTypicalPersons(), td.alice)));
        personListPanel.enterCommandAndApply("list");
        assertTrue(personListPanel.isListMatching(TestUtil.removePersonsFromList(td.getTypicalPersons(), td.alice)));
        assertEquals("Listed all persons", resultDisplay.getText());
    }

    @Test
    public void listAllPerson_afterCreate_showCorrectPersonsAfterCreation() {
        assertTrue(personListPanel.isListMatching(td.getTypicalPersons()));
        personListPanel.enterCommandAndApply(td.hoon.getCommandString());
        assertTrue(personListPanel.isListMatching(TestUtil.addPersonsToList(td.getTypicalPersons(), td.hoon)));
        personListPanel.enterCommandAndApply("list");
        assertTrue(personListPanel.isListMatching(TestUtil.addPersonsToList(td.getTypicalPersons(), td.hoon)));
        assertEquals("Listed all persons", resultDisplay.getText());
    }

}
