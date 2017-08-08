package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.commands.SelectCommand.MESSAGE_SELECT_PERSON_SUCCESS;
import static seedu.address.testutil.TypicalPersons.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;
import static systemtests.AppStateAsserts.assertCommandFailure;
import static systemtests.AppStateAsserts.assertCommandSuccess;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class SelectCommandSystemTest extends AddressBookSystemTest {
    @Test
    public void selectPerson_nonEmptyList() throws Exception {
        Model expectedModel = new ModelManager(new AddressBook(getTestApp().getModel().getAddressBook()),
                new UserPrefs());
        int invalidIndex = getTestApp().getModel().getFilteredPersonList().size() + 1;

        assertCommandFailure(this, SelectCommand.COMMAND_WORD + " " + invalidIndex,
                MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        assertNoCardSelected();

        String command = SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased();
        assertSelectCommandSuccess(command, expectedModel, INDEX_FIRST_PERSON, true, true); // first person in the list

        Index personCount = Index.fromOneBased(getTypicalPersons().length); // last person in the list
        command = SelectCommand.COMMAND_WORD + " " + personCount.getOneBased();
        assertSelectCommandSuccess(command, expectedModel, personCount, true, true);

        Index middleIndex = Index.fromOneBased(personCount.getOneBased() / 2); // a person in the middle of the list
        command = SelectCommand.COMMAND_WORD + " " + middleIndex.getOneBased();
        assertSelectCommandSuccess(command, expectedModel, middleIndex, true, true);

        assertCommandFailure(this, SelectCommand.COMMAND_WORD + " " + invalidIndex,
                MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        assertCardSelected(middleIndex); // assert previous selection remains
    }

    @Test
    public void selectPerson_emptyList() throws Exception {
        runCommand(ClearCommand.COMMAND_WORD);
        assert getPersonListPanel().getListSize() == 0;
        assertCommandFailure(this, SelectCommand.COMMAND_WORD + " "
                        + INDEX_FIRST_PERSON.getOneBased(), MESSAGE_INVALID_PERSON_DISPLAYED_INDEX); //invalid index
        assertNoCardSelected();
    }

    private void assertCardSelected(Index index) throws Exception {
        PersonCardHandle expectedCard = getPersonListPanel().getPersonCardHandle(index.getZeroBased());
        PersonCardHandle selectedCard = getPersonListPanel().getHandleToSelectedCard();
        assertCardEquals(expectedCard, selectedCard);
    }

    private void assertNoCardSelected() {
        assertFalse(getPersonListPanel().isAnyCardSelected());
    }

    /**
     * Asserts that after executing the {@code command}, the GUI components display what we expected,
     * and the model and storage are modified accordingly.
     */
    private void assertSelectCommandSuccess(String command, Model expectedModel, Index index,
                                            boolean browserUrlWillChange,
                                            boolean personListSelectionWillChange) throws Exception {
        String expectedResultMessage = String.format(MESSAGE_SELECT_PERSON_SUCCESS, index.getOneBased());

        assertCommandSuccess(this, command, expectedModel, expectedResultMessage, false, browserUrlWillChange,
                personListSelectionWillChange);
        assertCardSelected(index);
    }
}
