package guitests;

import guitests.guihandles.PersonListPanelHandle;
import org.junit.Test;
import seedu.address.exceptions.IllegalValueException;
import seedu.address.model.person.ReadOnlyPerson;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PersonDeleteCommandTest extends GuiTestBase {

    @Test
    public void deletePerson_firstPerson_successful() throws IllegalValueException {
        int numberOfPeopleBefore = personListPanel.getNumberOfPeople();
        ReadOnlyPerson secondPersonBefore = personListPanel.getPerson(1);
        ReadOnlyPerson targetPerson = personListPanel.getPerson(0);

        personListPanel.enterCommandAndApply("delete 1");

        int numberOfPeopleAfter = personListPanel.getNumberOfPeople();
        ReadOnlyPerson firstPersonAfter = personListPanel.getPerson(0);
        assertEquals(PersonListPanelHandle.NOT_FOUND, personListPanel.getPersonIndex(targetPerson));
        assertEquals(numberOfPeopleBefore - 1, numberOfPeopleAfter);
        assertTrue(secondPersonBefore.isSameStateAs(firstPersonAfter));
    }

    @Test
    public void deletePerson_lastPerson_successful() throws IllegalValueException {
        int numberOfPeopleBefore = personListPanel.getNumberOfPeople();
        ReadOnlyPerson secondLastPersonBefore = personListPanel.getPerson(numberOfPeopleBefore - 2);
        ReadOnlyPerson targetPerson = personListPanel.getPerson(numberOfPeopleBefore - 1);

        personListPanel.enterCommandAndApply("delete " + numberOfPeopleBefore);

        int numberOfPeopleAfter = personListPanel.getNumberOfPeople();
        ReadOnlyPerson lastPersonAfter = personListPanel.getPerson(numberOfPeopleAfter - 1);
        assertEquals(PersonListPanelHandle.NOT_FOUND, personListPanel.getPersonIndex(targetPerson));
        assertEquals(numberOfPeopleBefore - 1, numberOfPeopleAfter);
        assertTrue(secondLastPersonBefore.isSameStateAs(lastPersonAfter));
    }

    @Test
    public void deletePerson_middlePerson_successful() throws IllegalValueException {
        int numberOfPeopleBefore = personListPanel.getNumberOfPeople();
        ReadOnlyPerson secondPersonBefore = personListPanel.getPerson(1);
        ReadOnlyPerson fourthPersonBefore = personListPanel.getPerson(3);
        ReadOnlyPerson targetPerson = personListPanel.getPerson(2);

        personListPanel.enterCommandAndApply("delete 3");

        int numberOfPeopleAfter = personListPanel.getNumberOfPeople();
        ReadOnlyPerson secondPersonAfter = personListPanel.getPerson(1);
        ReadOnlyPerson thirdPersonAfter = personListPanel.getPerson(2);
        assertEquals(PersonListPanelHandle.NOT_FOUND, personListPanel.getPersonIndex(targetPerson));
        assertEquals(numberOfPeopleBefore - 1, numberOfPeopleAfter);
        assertTrue(secondPersonBefore.isSameStateAs(secondPersonAfter));
        assertTrue(fourthPersonBefore.isSameStateAs(thirdPersonAfter));
    }

    @Test
    public void deletePerson_outOfBoundIndex_fail() {
        personListPanel.enterCommandAndApply("delete " + (personListPanel.getNumberOfPeople() + 1));
        assertEquals("The person index provided is invalid", headerStatusBar.getText());
    }

    @Test
    public void deletePerson_zeroIndex_fail() {
        personListPanel.enterCommandAndApply("delete 0");
        assertEquals("The person index provided is invalid", headerStatusBar.getText());
    }

    @Test
    public void deletePerson_negativeIndex_fail() {
        personListPanel.enterCommandAndApply("delete -1");
        assertEquals("The person index provided is invalid", headerStatusBar.getText());
    }
}
