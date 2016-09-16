package guitests;

import org.junit.Test;
import seedu.address.exceptions.IllegalValueException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PersonSearchCommandTest extends GuiTestBase {

    @Test
    public void searchPerson_searchInvalidPerson_noResult() throws IllegalValueException {
        personListPanel.enterCommandAndApply("find Mark");
        assertEquals(0, personListPanel.getNumberOfPeople());
        assertEquals("0 persons listed!", resultDisplay.getText());
    }

    @Test
    public void searchPerson_withSameLastName_foundMultiple() throws IllegalValueException {
        personListPanel.enterCommandAndApply("find Meier");
        assertEquals(2, personListPanel.getNumberOfPeople());
        assertEquals("2 persons listed!", resultDisplay.getText());
        assertTrue(personListPanel.isListMatching(td.benson, td.daniel));
    }

    @Test
    public void searchPerson_withUniqueLastName_foundSingle() throws IllegalValueException {
        personListPanel.enterCommandAndApply("find Pauline");
        assertEquals(1, personListPanel.getNumberOfPeople());
        assertEquals("1 persons listed!", resultDisplay.getText());
        assertTrue(personListPanel.isListMatching(td.alice));
    }


    @Test
    public void searchPerson_withUniqueFirstName_foundSingle() throws IllegalValueException {
        personListPanel.enterCommandAndApply("find George");
        assertEquals(1, personListPanel.getNumberOfPeople());
        assertEquals("1 persons listed!", resultDisplay.getText());
        assertTrue(personListPanel.isListMatching(td.george));
    }

    @Test
    public void searchPerson_withFullName_foundSingle() throws IllegalValueException {
        personListPanel.enterCommandAndApply("find Elle Meyer");
        assertEquals(1, personListPanel.getNumberOfPeople());
        assertEquals("1 persons listed!", resultDisplay.getText());
        assertTrue(personListPanel.isListMatching(td.elle));
    }

    @Test
    public void searchPerson_withSameSubsetOfCharacters_foundMultiple() throws IllegalValueException {
        /* TODO: include this when ISSUE #41 is fixed.
        personListPanel.enterCommandAndApply("find on");
        sleep(1, TimeUnit.SECONDS);
        assertEquals(2, personListPanel.getNumberOfPeople());
        assertEquals("2 persons listed!", resultDisplay.getText());
        assertTrue(personListPanel.isListMatching(td.benson, td.fiona));
        */
    }

    @Test
    public void searchPerson_invalidCommand_fail() {
        personListPanel.enterCommandAndApply("findgeorge");
        assertEquals("Invalid command", resultDisplay.getText());
    }
}
