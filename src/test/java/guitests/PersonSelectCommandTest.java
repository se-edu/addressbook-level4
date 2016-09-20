package guitests;

import org.junit.Test;
import seedu.address.model.person.ReadOnlyPerson;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PersonSelectCommandTest extends AddressBookGuiTest {

    @Test
    public void selectPerson_firstPerson_successful() {
        assertListNotEmpty();
        assertNoPersonSelected();
        personListPanel.enterCommandAndApply("select 1");
        assertPersonSelectedIs(1);
    }

    private void assertPersonSelectedIs(int index) {
        assertEquals("Selected Person: "+index, resultDisplay.getText());
        assertEquals(personListPanel.getSelectedPersons().size(), 1);
        ReadOnlyPerson selectedPerson = personListPanel.getSelectedPersons().get(0);
        assertEquals(personListPanel.getPerson(index-1), selectedPerson);
    }

    private void assertNoPersonSelected() {
        assertEquals(personListPanel.getSelectedPersons().size(), 0);
    }

    private void assertListNotEmpty() {
        int numberOfPeople = personListPanel.getNumberOfPeople();
        assertTrue(numberOfPeople > 0);
    }

    @Test
    public void selectPerson_lastPerson_successful() {
        int numberOfPeople = personListPanel.getNumberOfPeople();
        assertTrue(numberOfPeople > 0);
        assertNoPersonSelected();
        personListPanel.enterCommandAndApply("select " + numberOfPeople);
        assertPersonSelectedIs(numberOfPeople);
    }


    @Test
    public void selectPerson_middlePerson_successful() {
        int numberOfPeople = personListPanel.getNumberOfPeople();
        assertTrue(numberOfPeople > 5);
        assertNoPersonSelected();
        personListPanel.enterCommandAndApply("select 3");
        assertPersonSelectedIs(3);
    }

    @Test
    public void selectPerson_outOfBoundIndex_fail() {
        int numberOfPeople = personListPanel.getNumberOfPeople();
        assertTrue(numberOfPeople > 0);
        assertNoPersonSelected();

        personListPanel.enterCommandAndApply("select " + (numberOfPeople + 1));
        assertEquals("The person index provided is invalid", resultDisplay.getText());

        assertNoPersonSelected();
        personListPanel.enterCommandAndApply("select 1");
        assertEquals(personListPanel.getSelectedPersons().size(), 1);

        personListPanel.enterCommandAndApply("select " + (numberOfPeople + 1));
        assertEquals("The person index provided is invalid", resultDisplay.getText());

        assertEquals(personListPanel.getSelectedPersons().size(), 1);
        ReadOnlyPerson firstPerson = personListPanel.getSelectedPersons().get(0);
        assertEquals(personListPanel.getPerson(0), firstPerson);

    }

    @Test
    public void selectPerson_zeroIndex_fail() {
        assertListNotEmpty();
        assertNoPersonSelected();

        personListPanel.enterCommandAndApply("select 0");
        assertEquals("The person index provided is invalid", resultDisplay.getText());

        assertNoPersonSelected();
        personListPanel.enterCommandAndApply("select 1");
        assertEquals(personListPanel.getSelectedPersons().size(), 1);

        personListPanel.enterCommandAndApply("select 0");
        assertEquals("The person index provided is invalid", resultDisplay.getText());

        assertEquals(personListPanel.getSelectedPersons().size(), 1);
        ReadOnlyPerson firstPerson = personListPanel.getSelectedPersons().get(0);
        assertEquals(personListPanel.getPerson(0), firstPerson);

    }

    @Test
    public void selectPerson_negativeIndex_fail() {
        assertListNotEmpty();
        assertNoPersonSelected();

        personListPanel.enterCommandAndApply("select -1");
        assertEquals("The person index provided is invalid", resultDisplay.getText());

        assertNoPersonSelected();
        personListPanel.enterCommandAndApply("select 1");
        assertEquals(personListPanel.getSelectedPersons().size(), 1);

        personListPanel.enterCommandAndApply("select -1");
        assertEquals("The person index provided is invalid", resultDisplay.getText());

        assertEquals(personListPanel.getSelectedPersons().size(), 1);
        ReadOnlyPerson firstPerson = personListPanel.getSelectedPersons().get(0);
        assertEquals(personListPanel.getPerson(0), firstPerson);

    }
}
