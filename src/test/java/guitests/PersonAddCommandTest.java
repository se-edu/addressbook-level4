package guitests;

import guitests.guihandles.PersonCardHandle;
import org.junit.Test;
import seedu.address.commands.AddPersonCommand;
import seedu.address.exceptions.IllegalValueException;
import seedu.address.testutil.TestPerson;
import seedu.address.testutil.TestUtil;

import static org.junit.Assert.assertTrue;

public class PersonAddCommandTest extends AddressBookGuiTest {

    @Test
    public void addPerson_singlePerson_successful() throws IllegalValueException {
        //add one person
        TestPerson[] currentList = td.getTypicalPersons();
        TestPerson personToAdd = td.hoon;
        assertAddSuccess(personToAdd, currentList);
        currentList = TestUtil.addPersonsToList(currentList, personToAdd);

        //add another person
        personToAdd = td.ida;
        assertAddSuccess(personToAdd, currentList);
        currentList = TestUtil.addPersonsToList(currentList, personToAdd);

        //add duplicate person
        runCommand(td.hoon.getAddCommand());
        assertResultMessage(AddPersonCommand.MESSAGE_DUPLICATE_PERSON);
        assertTrue(personListPanel.isListMatching(currentList));

        //add to empty list
        runCommand("clear");
        assertAddSuccess(td.alice);

        //invalid command
        runCommand("adds Johnny");
        assertResultMessage("Invalid command");
    }

    private void assertAddSuccess(TestPerson personToAdd, TestPerson... currentList) {
        runCommand(personToAdd.getAddCommand());

        //confirm the new card contains the right data
        PersonCardHandle addedCard = personListPanel.navigateToPerson(personToAdd.getName().fullName);
        assertMatching(personToAdd, addedCard);

        //confirm the list now contains all previous persons plus the new person
        TestPerson[] expectedList = TestUtil.addPersonsToList(currentList, personToAdd);
        assertTrue(personListPanel.isListMatching(expectedList));
    }

}
