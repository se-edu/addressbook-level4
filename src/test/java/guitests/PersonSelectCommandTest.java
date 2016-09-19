package guitests;

import org.junit.Test;
import seedu.address.model.person.ReadOnlyPerson;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PersonSelectCommandTest extends GuiTestBase {

    @Test
    public void selectPerson_firstPerson_successful() {
        int numberOfPeople = personListPanel.getNumberOfPeople();
        assertTrue(numberOfPeople > 0);
        assertEquals(personListPanel.getSelectedPersons().size(), 0);

        personListPanel.enterCommandAndApply("select 1");
        assertEquals("Selected Person: 1", resultDisplay.getText());
        assertEquals(personListPanel.getSelectedPersons().size(), 1);
        ReadOnlyPerson firstPerson = personListPanel.getSelectedPersons().get(0);
        assertEquals(personListPanel.getPerson(0), firstPerson);
    }

    @Test
    public void selectPerson_lastPerson_successful() {
        int numberOfPeople = personListPanel.getNumberOfPeople();
        assertTrue(numberOfPeople > 0);
        assertEquals(personListPanel.getSelectedPersons().size(), 0);

        personListPanel.enterCommandAndApply("select " + numberOfPeople);
        assertEquals("Selected Person: " + numberOfPeople, resultDisplay.getText());
        assertEquals(personListPanel.getSelectedPersons().size(), 1);
        ReadOnlyPerson firstPerson = personListPanel.getSelectedPersons().get(0);
        assertEquals(personListPanel.getPerson(numberOfPeople - 1), firstPerson);
    }


    @Test
    public void selectPerson_middlePerson_successful() {
        int numberOfPeople = personListPanel.getNumberOfPeople();
        assertTrue(numberOfPeople > 5);
        assertEquals(personListPanel.getSelectedPersons().size(), 0);

        personListPanel.enterCommandAndApply("select 3");
        assertEquals("Selected Person: 3", resultDisplay.getText());
        assertEquals(personListPanel.getSelectedPersons().size(), 1);
        ReadOnlyPerson thirdPerson = personListPanel.getSelectedPersons().get(0);
        assertEquals(personListPanel.getPerson(2), thirdPerson);
    }

    @Test
    public void selectPerson_outOfBoundIndex_fail() {
        int numberOfPeople = personListPanel.getNumberOfPeople();
        assertTrue(numberOfPeople > 0);
        assertEquals(personListPanel.getSelectedPersons().size(), 0);

        personListPanel.enterCommandAndApply("select " + (numberOfPeople + 1));
        assertEquals("The person index provided is invalid", resultDisplay.getText());

        assertEquals(personListPanel.getSelectedPersons().size(), 0);
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
        int numberOfPeople = personListPanel.getNumberOfPeople();
        assertTrue(numberOfPeople > 0);
        assertEquals(personListPanel.getSelectedPersons().size(), 0);

        personListPanel.enterCommandAndApply("select 0");
        assertEquals("The person index provided is invalid", resultDisplay.getText());

        assertEquals(personListPanel.getSelectedPersons().size(), 0);
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
        int numberOfPeople = personListPanel.getNumberOfPeople();
        assertTrue(numberOfPeople > 0);
        assertEquals(personListPanel.getSelectedPersons().size(), 0);

        personListPanel.enterCommandAndApply("select -1");
        assertEquals("The person index provided is invalid", resultDisplay.getText());

        assertEquals(personListPanel.getSelectedPersons().size(), 0);
        personListPanel.enterCommandAndApply("select 1");
        assertEquals(personListPanel.getSelectedPersons().size(), 1);

        personListPanel.enterCommandAndApply("select -1");
        assertEquals("The person index provided is invalid", resultDisplay.getText());

        assertEquals(personListPanel.getSelectedPersons().size(), 1);
        ReadOnlyPerson firstPerson = personListPanel.getSelectedPersons().get(0);
        assertEquals(personListPanel.getPerson(0), firstPerson);
    }
}
