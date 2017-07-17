package guitests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonUtil;

public class AddCommandTest extends AddressBookGuiTest {

    @Test
    public void add() throws Exception {
        //add one person
        ArrayList<Person> currentList = new ArrayList<>(Arrays.asList(td.getTypicalPersons()));
        Person personToAdd = td.hoon;
        assertAddSuccess(personToAdd, currentList);

        //add another person
        personToAdd = td.ida;
        assertAddSuccess(personToAdd, currentList);

        //add duplicate person
        runCommand(PersonUtil.getAddCommand(td.hoon));
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_PERSON);
        assertTrue(getPersonListPanel().isListMatching(currentList.toArray(new Person[currentList.size()])));

        //add to empty list
        runCommand(ClearCommand.COMMAND_WORD);
        currentList.clear();
        assertAddSuccess(td.alice, currentList);

        //invalid command
        runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(Person personToAdd, ArrayList<Person> currentList) throws Exception {
        runCommand(PersonUtil.getAddCommand(personToAdd));

        //confirm the new card contains the right data
        getPersonListPanel().navigateToPerson(personToAdd);
        PersonCardHandle addedCard = getPersonListPanel().getPersonCardHandle(personToAdd);
        assertCardMatchesPerson(addedCard, personToAdd);

        //confirm the list now contains all previous persons plus the new person
        currentList.add(personToAdd);
        assertTrue(getPersonListPanel().isListMatching(currentList.toArray(new Person[currentList.size()])));
    }

}
