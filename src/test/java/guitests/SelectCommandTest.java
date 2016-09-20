package guitests;

import org.junit.Test;
import seedu.address.model.person.ReadOnlyPerson;

import static org.junit.Assert.assertEquals;

public class SelectCommandTest extends AddressBookGuiTest {


    @Test
    public void selectPerson_nonEmptyList() {
        int personCount = td.getTypicalPersons().length;
        int middleIndex = personCount / 2;
        assertListSize(personCount);

        assertSelectionInvalid(0); //invalid index
        assertNoPersonSelected();

        assertSelectionSuccess(1); //first person in the list
        assertSelectionSuccess(personCount); //last person in the list
        assertSelectionSuccess(middleIndex); //a person in the middle of the list

        assertSelectionInvalid(personCount + 1); //invalid index
        assertPersonSelected(middleIndex); //assert previous selection remains

        /* Testing other invalid indexes such as -1 should be done when testing the SelectCommand */
    }

    @Test
    public void selectPerson_emptyList(){
        personListPanel.enterCommandAndApply("clear");
        assertListSize(0);
        assertSelectionInvalid(1); //invalid index
    }

    private void assertListSize(int size) {
        int numberOfPeople = personListPanel.getNumberOfPeople();
        assertEquals(size, numberOfPeople);
    }

    private void assertSelectionInvalid(int index) {
        personListPanel.enterCommandAndApply("select " + index);
        assertEquals("The person index provided is invalid", resultDisplay.getText());
    }

    private void assertSelectionSuccess(int index) {
        personListPanel.enterCommandAndApply("select " + index);
        assertEquals("Selected Person: "+index, resultDisplay.getText());
        assertPersonSelected(index);
    }

    private void assertPersonSelected(int index) {
        assertEquals(personListPanel.getSelectedPersons().size(), 1);
        ReadOnlyPerson selectedPerson = personListPanel.getSelectedPersons().get(0);
        assertEquals(personListPanel.getPerson(index-1), selectedPerson);
        //TODO: confirm the correct page is loaded in the Browser Panel
    }

    private void assertNoPersonSelected() {
        assertEquals(personListPanel.getSelectedPersons().size(), 0);
    }

}
