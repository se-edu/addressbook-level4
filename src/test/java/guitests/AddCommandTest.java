package guitests;

import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;
import static seedu.address.ui.testutil.GuiTestAssert.assertListMatching;
import static seedu.address.ui.testutil.GuiTestAssert.assertResultMessage;

import java.util.List;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonUtil;

public class AddCommandTest extends AddressBookGuiTest {

    @Test
    public void add() {
        //add one person
        List<ReadOnlyPerson> expectedList = getTypicalPersons();
        ReadOnlyPerson personToAdd = HOON;
        expectedList.add(personToAdd);
        assertAddSuccess(personToAdd, expectedList);

        //add another person
        personToAdd = IDA;
        expectedList.add(personToAdd);
        assertAddSuccess(personToAdd, expectedList);

        //add duplicate person
        runCommand(PersonUtil.getAddCommand(HOON));
        assertResultMessage(getResultDisplay(), AddCommand.MESSAGE_DUPLICATE_PERSON);
        assertListMatching(getPersonListPanel(), expectedList);

        //add to empty list
        runCommand(ClearCommand.COMMAND_WORD);
        expectedList.clear();
        personToAdd = ALICE;
        expectedList.add(personToAdd);
        assertAddSuccess(ALICE, expectedList);

        //invalid command
        runCommand("adds Johnny");
        assertResultMessage(getResultDisplay(), Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(ReadOnlyPerson personToAdd, List<ReadOnlyPerson> expectedList) {
        runCommand(PersonUtil.getAddCommand(personToAdd));

        //confirm the new card contains the right data
        getPersonListPanel().navigateToCard(personToAdd);
        PersonCardHandle addedCard = getPersonListPanel().getPersonCardHandle(personToAdd);
        assertCardDisplaysPerson(personToAdd, addedCard);

        //confirm the list now contains all previous persons plus the new person
        assertListMatching(getPersonListPanel(), expectedList.toArray(new ReadOnlyPerson[expectedList.size()]));
    }

}
