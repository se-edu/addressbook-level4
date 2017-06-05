package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.INDEX_SECOND_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.TypicalPersons;

/**
 * Contains integration tests (interaction with the Model) and unit tests for DeleteCommand.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(new TypicalPersons().getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndex_succeeds() throws Exception {
        ReadOnlyPerson deletedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, deletedPerson);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(deletedPerson);

        CommandTestUtil.assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = prepareCommand(outOfBoundIndex);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        CommandTestUtil.assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                expectedModel);
    }

    @Test
    public void execute_validIndexInFilteredList_succeeds() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson deletedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, deletedPerson);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(deletedPerson);
        expectedModel.updateFilteredPersonList(Collections.emptySet());

        CommandTestUtil.assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexInFilteredList_throwsCommandException() throws Exception {
        showFirstPersonOnly(model);

        // with only the first person shown, there is no second person in the new filtered list,
        // even if the original list do have more than two people
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        assertTrue(outOfBoundIndex.getZeroBased() <= model.getAddressBook().getPersonList().size());

        DeleteCommand deleteCommand = prepareCommand(outOfBoundIndex);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showFirstPersonOnly(expectedModel);

        CommandTestUtil.assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                expectedModel);
    }

    /**
     * Returns a {@code DeleteCommand} with the parameter {@code index}.
     */
    private DeleteCommand prepareCommand(Index index) {
        DeleteCommand deleteCommand = new DeleteCommand(index);
        deleteCommand.setData(model, new CommandHistory());
        return deleteCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show only the first person from the address book.
     */
    private void showFirstPersonOnly(Model model) {
        ReadOnlyPerson person = model.getAddressBook().getPersonList().get(0);
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new HashSet<>(Arrays.asList(splitName)));

        assertTrue(model.getFilteredPersonList().size() == 1);
    }
}
