package guitests;

import org.junit.Test;
import seedu.address.exceptions.IllegalValueException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FindCommandTest extends AddressBookGuiTest {

    @Test
    public void searchPerson_searchInvalidPerson_noResult() throws IllegalValueException {
        runCommand("find Mark");
        assertEquals(0, personListPanel.getNumberOfPeople());
        assertEquals("0 persons listed!", resultDisplay.getText());
    }

    @Test
    public void searchPerson_withSameLastName_foundMultiple() throws IllegalValueException {
        runCommand("find Meier");
        assertEquals(2, personListPanel.getNumberOfPeople());
        assertEquals("2 persons listed!", resultDisplay.getText());
        assertTrue(personListPanel.isListMatching(td.benson, td.daniel));
    }

    @Test
    public void searchPerson_withUniqueLastName_foundSingle() throws IllegalValueException {
        runCommand("find Pauline");
        assertEquals(1, personListPanel.getNumberOfPeople());
        assertEquals("1 persons listed!", resultDisplay.getText());
        assertTrue(personListPanel.isListMatching(td.alice));
    }


    @Test
    public void searchPerson_withUniqueFirstName_foundSingle() throws IllegalValueException {
        runCommand("find George");
        assertEquals(1, personListPanel.getNumberOfPeople());
        assertEquals("1 persons listed!", resultDisplay.getText());
        assertTrue(personListPanel.isListMatching(td.george));
    }

    @Test
    public void searchPerson_withFullName_foundSingle() throws IllegalValueException {
        runCommand("find Elle Meyer");
        assertEquals(1, personListPanel.getNumberOfPeople());
        assertEquals("1 persons listed!", resultDisplay.getText());
        assertTrue(personListPanel.isListMatching(td.elle));
    }

    @Test
    public void searchPerson_withSameSubsetOfCharacters_foundNone() throws IllegalValueException {
        runCommand("find on");
        assertEquals(0, personListPanel.getNumberOfPeople());
        assertEquals("0 persons listed!", resultDisplay.getText());
    }

    @Test
    public void searchPerson_invalidCommand_fail() {
        runCommand("findgeorge");
        assertEquals("Invalid command", resultDisplay.getText());
    }
}
