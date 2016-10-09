package guitests;

import guitests.guihandles.PersonCardHandle;
import org.junit.Test;
import seedu.address.logic.commands.AddCommand;
import seedu.address.commons.core.Messages;
import seedu.address.testutil.TestTask;
import seedu.address.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

public class AddCommandTest extends AddressBookGuiTest {

    @Test
    public void add() {
        //add one task
        TestTask[] currentList = td.getTypicalPersons();
        TestTask personToAdd = td.hoon;
        assertAddSuccess(personToAdd, currentList);
        currentList = TestUtil.addPersonsToList(currentList, personToAdd);

        //add another task
        personToAdd = td.ida;
        assertAddSuccess(personToAdd, currentList);
        currentList = TestUtil.addPersonsToList(currentList, personToAdd);

        //add duplicate task
        commandBox.runCommand(td.hoon.getAddCommand());
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        assertTrue(personListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand("clear");
        assertAddSuccess(td.alice);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(TestTask personToAdd, TestTask... currentList) {
        commandBox.runCommand(personToAdd.getAddCommand());

        //confirm the new card contains the right data
        PersonCardHandle addedCard = personListPanel.navigateToPerson(personToAdd.getName().fullName);
        assertMatching(personToAdd, addedCard);

        //confirm the list now contains all previous tasks plus the new task
        TestTask[] expectedList = TestUtil.addPersonsToList(currentList, personToAdd);
        assertTrue(personListPanel.isListMatching(expectedList));
    }

}
