package guitests;

import org.junit.Test;
import seedu.address.testutil.TestUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ListAllPersonsCommandTest extends AddressBookGuiTest {

    @Test
    public void listAllPerson_afterNoResultSearch_showAllPersons() {
        runCommand("find Mark");
        assertEquals(0, personListPanel.getNumberOfPeople());
        assertResultMessage("0 persons listed!");

        runCommand("list");
        assertTrue(personListPanel.isListMatching(td.getTypicalPersons()));
        assertResultMessage("Listed all persons");
    }

    @Test
    public void listAllPerson_afterPositiveSearch_showAllPersons() {
        runCommand("find Meier");
        assertEquals(2, personListPanel.getNumberOfPeople());
        assertResultMessage("2 persons listed!");
        assertTrue(personListPanel.isListMatching(td.benson, td.daniel));

        runCommand("list");
        assertTrue(personListPanel.isListMatching(td.getTypicalPersons()));
        assertResultMessage("Listed all persons");
    }

    @Test
    public void listAllPerson_noSearch_showAllPersons() {
        assertTrue(personListPanel.isListMatching(td.getTypicalPersons()));
        runCommand("list");
        assertTrue(personListPanel.isListMatching(td.getTypicalPersons()));
        assertResultMessage("Listed all persons");
    }

    @Test
    public void listAllPerson_afterDelete_showCorrectPersonsAfterDeletion() {
        assertTrue(personListPanel.isListMatching(td.getTypicalPersons()));
        runCommand("delete 1");
        assertTrue(personListPanel.isListMatching(TestUtil.removePersonsFromList(td.getTypicalPersons(), td.alice)));
        runCommand("list");
        assertTrue(personListPanel.isListMatching(TestUtil.removePersonsFromList(td.getTypicalPersons(), td.alice)));
        assertResultMessage("Listed all persons");
    }

    @Test
    public void listAllPerson_afterCreate_showCorrectPersonsAfterCreation() {
        assertTrue(personListPanel.isListMatching(td.getTypicalPersons()));
        runCommand(td.hoon.getCommandString());
        assertTrue(personListPanel.isListMatching(TestUtil.addPersonsToList(td.getTypicalPersons(), td.hoon)));
        runCommand("list");
        assertTrue(personListPanel.isListMatching(TestUtil.addPersonsToList(td.getTypicalPersons(), td.hoon)));
        assertResultMessage("Listed all persons");
    }

}
