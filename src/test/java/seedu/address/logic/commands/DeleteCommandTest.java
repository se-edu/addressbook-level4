package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.logic.commands.CommandTestUtil.showSecondPersonOnly;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstPersonOnly(model);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteCommand deleteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void undoRedo_deleteCommandSuccess_success() throws Exception {
        UndoCommand undoCommand = new UndoCommand();
        RedoCommand redoCommand = new RedoCommand();
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        undoCommand.setData(model, new CommandHistory(), undoRedoStack);
        redoCommand.setData(model, new CommandHistory(), undoRedoStack);

        Person personToDelete = model.getFilteredPersonList().get(0);
        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        ModelManager expectedModelBeforeDeleting = new ModelManager(model.getAddressBook(), new UserPrefs());
        ModelManager expectedModelAfterDeleting = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModelAfterDeleting.deletePerson(personToDelete);

        // deleteCommand success, command gets pushed into undoRedoStack
        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModelAfterDeleting);
        undoRedoStack.push(deleteCommand);

        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModelBeforeDeleting);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModelAfterDeleting);
    }

    @Test
    public void undoRedo_deleteCommandFailure_failure() {
        UndoCommand undoCommand = new UndoCommand();
        RedoCommand redoCommand = new RedoCommand();
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        undoCommand.setData(model, new CommandHistory(), undoRedoStack);
        redoCommand.setData(model, new CommandHistory(), undoRedoStack);

        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = prepareCommand(outOfBoundIndex);

        // deleteCommand failed, command not pushed into undoRedoStack
        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // no commands in undoRedoStack, undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void undoRedo_filteredList_samePersonDeleted() throws Exception {
        showSecondPersonOnly(model);
        UndoCommand undoCommand = new UndoCommand();
        RedoCommand redoCommand = new RedoCommand();
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        undoCommand.setData(model, new CommandHistory(), undoRedoStack);
        redoCommand.setData(model, new CommandHistory(), undoRedoStack);

        Person personToDelete = model.getFilteredPersonList().get(0);
        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        ModelManager expectedModelBeforeDeleting = new ModelManager(model.getAddressBook(), new UserPrefs());
        ModelManager expectedModelAfterDeleting = new ModelManager(model.getAddressBook(), new UserPrefs());
        showSecondPersonOnly(expectedModelAfterDeleting);
        expectedModelAfterDeleting.deletePerson(personToDelete);

        // deleteCommand success, command gets pushed into undoRedoStack
        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModelAfterDeleting);
        undoRedoStack.push(deleteCommand);

        // list to show all persons after undoCommand successfully execute
        expectedModelBeforeDeleting.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModelBeforeDeleting);

        // list to show all persons after redoCommand successfully execute
        expectedModelAfterDeleting.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // ensure personToDelete is different from first person in the unfiltered list
        assertNotEquals(personToDelete, model.getFilteredPersonList().get(0));
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModelAfterDeleting);
    }

    @Test
    public void equals() throws Exception {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // command preprocessed -> returns false
        deleteFirstCommandCopy.setData(model, new CommandHistory(), new UndoRedoStack());
        deleteFirstCommandCopy.preprocessUndoableCommand();
        assertFalse(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Returns a {@code DeleteCommand} with the parameter {@code index}.
     */
    private DeleteCommand prepareCommand(Index index) {
        DeleteCommand deleteCommand = new DeleteCommand(index);
        deleteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assert model.getFilteredPersonList().isEmpty();
    }
}
