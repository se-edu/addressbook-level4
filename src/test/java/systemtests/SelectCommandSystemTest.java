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
    public void select_nonEmptyList() throws Exception {
        Model expectedModel = new ModelManager(new AddressBook(getTestApp().getModel().getAddressBook()),
                new UserPrefs());
        int invalidIndex = getTestApp().getModel().getFilteredPersonList().size() + 1;

        /* Case: invalid index (size + 1) -> rejected */
        assertCommandFailure(this, SelectCommand.COMMAND_WORD + " " + invalidIndex,
                MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        assertNoCardSelected();

        /* Case: select the first card in the list, command with leading spaces and trailing spaces -> selected */
        String command = "   " + SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + "   ";
        assertSelectCommandSuccess(command, expectedModel, INDEX_FIRST_PERSON, true, true); // first person in the list

        /* Case: select the last card in the list -> selected */
        Index personCount = Index.fromOneBased(getTypicalPersons().length);
        command = SelectCommand.COMMAND_WORD + " " + personCount.getOneBased();
        assertSelectCommandSuccess(command, expectedModel, personCount, true, true);

        /* Case: select the middle card in the list -> selected */
        Index middleIndex = Index.fromOneBased(personCount.getOneBased() / 2);
        command = SelectCommand.COMMAND_WORD + " " + middleIndex.getOneBased();
        assertSelectCommandSuccess(command, expectedModel, middleIndex, true, true);

        /* Case: invalid index (size + 1) -> rejected, middle card remains selected */
        assertCommandFailure(this, SelectCommand.COMMAND_WORD + " " + invalidIndex,
                MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        assertCardSelected(middleIndex);
    }

    @Test
    public void selectPerson_emptyList() throws Exception {
        runCommand(ClearCommand.COMMAND_WORD);
        assert getPersonListPanel().getListSize() == 0;

        /* Case: invalid index (1) -> rejected */
        assertCommandFailure(this, SelectCommand.COMMAND_WORD + " "
                        + INDEX_FIRST_PERSON.getOneBased(), MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        assertNoCardSelected();
    }

    /**
     * Asserts that after executing the {@code command}, the GUI components display what we expected,
     * and the model and storage are modified accordingly.
     */
    private void assertSelectCommandSuccess(String command, Model expectedModel, Index index,
            boolean browserUrlWillChange, boolean personListSelectionWillChange) throws Exception {
        String expectedResultMessage = String.format(MESSAGE_SELECT_PERSON_SUCCESS, index.getOneBased());

        assertCommandSuccess(this, command, expectedModel, expectedResultMessage, false, browserUrlWillChange,
                personListSelectionWillChange);
        assertCardSelected(index);
    }

    private void assertCardSelected(Index index) throws Exception {
        PersonCardHandle expectedCard = getPersonListPanel().getPersonCardHandle(index.getZeroBased());
        PersonCardHandle selectedCard = getPersonListPanel().getHandleToSelectedCard();
        assertCardEquals(expectedCard, selectedCard);
    }

    private void assertNoCardSelected() {
        assertFalse(getPersonListPanel().isAnyCardSelected());
    }
}
