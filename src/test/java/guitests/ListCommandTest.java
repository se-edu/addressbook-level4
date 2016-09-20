package guitests;

import org.junit.Test;
import seedu.address.testutil.TestPerson;
import seedu.address.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

public class ListCommandTest extends AddressBookGuiTest {

    @Test
    public void listAllPerson_afterNoResultSearch_showAllPersons() {
        runCommand("find Mark");
        assertListSize(0);
        assertResultMessage("0 persons listed!");

        assertListSuccess(td.getTypicalPersons());
    }

    @Test
    public void listAllPerson_afterPositiveSearch_showAllPersons() {
        runCommand("find Meier");
        assertListSize(2);
        assertResultMessage("2 persons listed!");
        assertTrue(personListPanel.isListMatching(td.benson, td.daniel));

        assertListSuccess(td.getTypicalPersons());
    }

    @Test
    public void listAllPerson_noSearch_showAllPersons() {
        assertTrue(personListPanel.isListMatching(td.getTypicalPersons()));
        assertListSuccess(td.getTypicalPersons());
    }

    @Test
    public void listAllPerson_afterDelete_showCorrectPersonsAfterDeletion() {
        assertTrue(personListPanel.isListMatching(td.getTypicalPersons()));
        runCommand("delete 1");
        assertTrue(personListPanel.isListMatching(TestUtil.removePersonsFromList(td.getTypicalPersons(), td.alice)));

        assertListSuccess(TestUtil.removePersonsFromList(td.getTypicalPersons(), td.alice));
    }

    @Test
    public void listAllPerson_afterCreate_showCorrectPersonsAfterCreation() {
        assertTrue(personListPanel.isListMatching(td.getTypicalPersons()));
        runCommand(td.hoon.getCommandString());
        assertTrue(personListPanel.isListMatching(TestUtil.addPersonsToList(td.getTypicalPersons(), td.hoon)));

        assertListSuccess(TestUtil.addPersonsToList(td.getTypicalPersons(), td.hoon));
    }


    private void assertListSuccess(TestPerson[] exepctedHits) {
        runCommand("list");
        assertTrue(personListPanel.isListMatching(exepctedHits));
        assertResultMessage("Listed all persons");
    }

}
