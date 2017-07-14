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
    public void add() throws Exception {
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
        runCommand(PersonUtil.getAddCommand(td.hoon));
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_PERSON);
        assertTrue(getPersonListPanel().isListMatching(currentList));

        //add to empty list
        runCommand(ClearCommand.COMMAND_WORD);
        assertAddSuccess(td.alice);

        //invalid command
        runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(Person personToAdd, Person... currentList) throws Exception {
        runCommand(PersonUtil.getAddCommand(personToAdd));

        //confirm the new card contains the right data
        getPersonListPanel().navigateToPerson(personToAdd);
        PersonCardHandle addedCard = getPersonListPanel().getPersonCardHandle(personToAdd);
        assertCardMatchesPerson(addedCard, personToAdd);

        //confirm the list now contains all previous persons plus the new person
        Person[] expectedList = TestUtil.addPersonsToList(currentList, personToAdd);
        assertTrue(getPersonListPanel().isListMatching(expectedList));
    }

}
