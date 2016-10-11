package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.model.task.ReadOnlyTask;

public class ViewCommandTest extends AddressBookGuiTest{
	  @Test
	    public void viewPerson_nonEmptyList() {

	        assertViewInvalid(10); //invalid index
	        assertNoPersonSelected();

	        assertViewSuccess(1); //first task in the list
	        int personCount = td.getTypicalTask().length;
	        assertViewSuccess(personCount); //last task in the list
	        int middleIndex = personCount / 2;
	        assertViewSuccess(middleIndex); //a task in the middle of the list

	        assertViewInvalid(personCount + 1); //invalid index

	        /* Testing other invalid indexes such as -1 should be done when testing the SelectCommand */
	    }

	    @Test
	    public void viewPerson_emptyList(){
	        commandBox.runCommand("clear");
	        assertListSize(0);
	        assertViewInvalid(1); //invalid index
	    }

	    private void assertViewInvalid(int index) {
	        commandBox.runCommand("view " + index);
	        assertResultMessage("The task index provided is invalid");
	    }

	    private void assertViewSuccess(int index) {
	        commandBox.runCommand("view " + index);
	        assertResultMessage("Viewing Task #"+index);
	    }

	    private void assertNoPersonSelected() {
	        assertEquals(taskListPanel.getSelectedTask().size(), 0);
	    }
}
