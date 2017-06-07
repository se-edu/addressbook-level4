package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonUtil;
import seedu.address.testutil.TestUtil;

public class AddCommandTest extends AddressBookGuiTest {

    @Test
    public void add() {
        //add one person
        Person[] currentList = td.getTypicalPersons();
        Person personToAdd = td.hoon;
        assertAddSuccess(personToAdd, currentList);
        currentList = TestUtil.addPersonsToList(currentList, personToAdd);

        //add another person
        personToAdd = td.ida;
        assertAddSuccess(personToAdd, currentList);
        currentList = TestUtil.addPersonsToList(currentList, personToAdd);

        //add duplicate person
        mainWindowHandle.getCommandBox().runCommand(PersonUtil.getAddCommand(td.hoon));
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_PERSON);
        assertTrue(mainWindowHandle.getPersonListPanel().isListMatching(currentList));

        //add to empty list
        mainWindowHandle.getCommandBox().runCommand(ClearCommand.COMMAND_WORD);
        assertAddSuccess(td.alice);

        //invalid command
        mainWindowHandle.getCommandBox().runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(Person personToAdd, Person... currentList) {
        mainWindowHandle.getCommandBox().runCommand(PersonUtil.getAddCommand(personToAdd));

        //confirm the new card contains the right data
        PersonCardHandle addedCard = mainWindowHandle.getPersonListPanel()
                .navigateToPerson(personToAdd.getName().fullName);
        assertMatching(personToAdd, addedCard);

        //confirm the list now contains all previous persons plus the new person
        Person[] expectedList = TestUtil.addPersonsToList(currentList, personToAdd);
        assertTrue(mainWindowHandle.getPersonListPanel().isListMatching(expectedList));
    }

}
