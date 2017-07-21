package guitests;

import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.TYPICAL_PERSONS;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonUtil;

public class AddCommandTest extends AddressBookGuiTest {

    @Test
    public void add() throws Exception {
        //add one person
        ArrayList<ReadOnlyPerson> expectedList = new ArrayList<>(Arrays.asList(TYPICAL_PERSONS));
        ReadOnlyPerson personToAdd = HOON;
        expectedList.add(personToAdd);
        assertAddSuccess(personToAdd, expectedList);

        //add another person
        personToAdd = IDA;
        expectedList.add(personToAdd);
        assertAddSuccess(personToAdd, expectedList);

        //add duplicate person
        runCommand(PersonUtil.getAddCommand(HOON));
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_PERSON);
        getPersonListPanel().assertListMatching(expectedList.toArray(new Person[expectedList.size()]));

        //add to empty list
        runCommand(ClearCommand.COMMAND_WORD);
        expectedList.clear();
        personToAdd = ALICE;
        expectedList.add(personToAdd);
        assertAddSuccess(ALICE, expectedList);

        //invalid command
        runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(ReadOnlyPerson personToAdd, ArrayList<ReadOnlyPerson> expectedList) throws Exception {
        runCommand(PersonUtil.getAddCommand(personToAdd));

        //confirm the new card contains the right data
        getPersonListPanel().navigateToCard(personToAdd);
        PersonCardHandle addedCard = getPersonListPanel().getPersonCardHandle(personToAdd);
        assertCardMatchesPerson(addedCard, personToAdd);

        //confirm the list now contains all previous persons plus the new person
        getPersonListPanel().assertListMatching(expectedList.toArray(new ReadOnlyPerson[expectedList.size()]));
    }

}
