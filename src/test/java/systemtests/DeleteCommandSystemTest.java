package systemtests;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS;
import static seedu.address.testutil.TestUtil.getLastIndex;
import static seedu.address.testutil.TestUtil.getMidIndex;
import static seedu.address.testutil.TestUtil.getPerson;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;

public class DeleteCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);

    @Test
    public void delete() throws Exception {
        Model expectedModel = new ModelManager(new AddressBook(getTestApp().getModel().getAddressBook()),
                new UserPrefs());

        /* Case: delete the first person in the list, command with leading spaces and trailing spaces -> deleted */
        String command = "     " + DeleteCommand.COMMAND_WORD + "      " + INDEX_FIRST_PERSON.getOneBased() + "       ";
        ReadOnlyPerson deletedPerson = removePerson(expectedModel, INDEX_FIRST_PERSON);
        String expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, deletedPerson);
        assertCommandSuccess(command, expectedModel, expectedResultMessage, false);

        /* Case: delete the last person in the list -> deleted */
        Model modelBeforeDeletingLast = new ModelManager(expectedModel.getAddressBook(), new UserPrefs());
        Index lastPersonIndex = getLastIndex(expectedModel);
        command = DeleteCommand.COMMAND_WORD + " " + String.valueOf(lastPersonIndex.getOneBased());
        deletedPerson = removePerson(expectedModel, lastPersonIndex);
        expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, deletedPerson);
        assertCommandSuccess(command, expectedModel, expectedResultMessage, false);

        /* Case: undo deleting the last person in the list -> last person restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage, false);

        /* Case: redo deleting the last person in the list -> last person deleted again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedModel, expectedResultMessage, false);

        /* Case: delete the middle person in the list -> deleted */
        Index middlePersonIndex = getMidIndex(expectedModel);
        command = DeleteCommand.COMMAND_WORD + " " + String.valueOf(middlePersonIndex.getOneBased());
        deletedPerson = removePerson(expectedModel, middlePersonIndex);
        expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, deletedPerson);
        assertCommandSuccess(command, expectedModel, expectedResultMessage, false);

        /* Case: delete the selected person -> person list panel selects the person before the deleted person */
        Index selectedIndex = getMidIndex(expectedModel);
        getPersonListPanel().select(selectedIndex.getZeroBased());
        command = DeleteCommand.COMMAND_WORD + " " + String.valueOf(selectedIndex.getOneBased());
        deletedPerson = removePerson(expectedModel, selectedIndex);
        expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, deletedPerson);
        assertCommandSuccess(command, expectedModel, expectedResultMessage, true);

        /* Case: invalid index (0) -> rejected */
        command = DeleteCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = DeleteCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(expectedModel.getAddressBook().getPersonList().size() + 1);
        command = DeleteCommand.COMMAND_WORD + " " + String.valueOf(outOfBoundsIndex.getOneBased());
        assertCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " 1 abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETE 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Removes the {@code ReadOnlyPerson} at the specified {@code index} in {@code model}'s address book.
     * @return the removed person
     */
    private ReadOnlyPerson removePerson(Model model, Index index) throws Exception {
        ReadOnlyPerson targetPerson = getPerson(model, index);
        model.deletePerson(targetPerson);
        return targetPerson;
    }

    /**
     * Asserts that after executing the command {@code commandToRun}, the command box displays an empty string and
     * has the default style class, the result box displays {@code expectedResultMessage}, the model and storage
     * contains the same person objects as {@code expectedModel}, the person list panel displays the persons in the
     * model correctly, the status bar's sync status changes, the browser url and selected card changes depending
     * on {@code browserUrlWillChange}.
     */
    private void assertCommandSuccess(String commandToRun, Model expectedModel, String expectedResultMessage,
            boolean browserUrlWillChange) throws Exception {

        rememberStates();
        runCommand(commandToRun);
        if (browserUrlWillChange) {
            waitUntilBrowserLoaded(getBrowserPanel());
            assertBrowserUrlChanged();
            assertSelectedCardChanged();
        } else {
            assertBrowserUrlUnchanged();
            assertSelectedCardUnchanged();
        }

        assertCommandBoxShows("");
        assertCommandBoxStyleDefault();
        assertResultBoxShows(expectedResultMessage);
        assertSavedAddressBookEquals(expectedModel.getAddressBook());
        assertModelEquals(expectedModel);
        assertPersonListPanelBounded();
        assertOnlySyncStatusChanged();
    }

    /**
     * Asserts that after executing the command {@code commandToRun}, the GUI components remain unchanged, except for
     * the {@code ResultDisplay} displaying {@code expectedResultMessage} and the command box has the error style.
     * The model and storage remains unchanged.
     */
    private void assertCommandFailure(String commandToRun, String expectedResultMessage) throws Exception {
        Model expectedModel = new ModelManager(
                new AddressBook(getTestApp().getModel().getAddressBook()), new UserPrefs());

        rememberStates();
        runCommand(commandToRun);

        assertCommandBoxShows(commandToRun);
        assertCommandBoxStyleError();
        assertBrowserUrlUnchanged();
        assertSelectedCardUnchanged();
        assertResultBoxShows(expectedResultMessage);
        assertSavedAddressBookEquals(expectedModel.getAddressBook());
        assertModelEquals(expectedModel);
        assertPersonListPanelBounded();
        assertStatusBarUnchanged();
    }
}
