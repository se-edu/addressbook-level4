package systemtests;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS;
import static seedu.address.testutil.TestUtil.getLastIndex;
import static seedu.address.testutil.TestUtil.getMidIndex;
import static seedu.address.testutil.TestUtil.getPerson;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.ui.testutil.GuiTestAssert.assertListMatching;

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
        assertDeleteCommandSuccess(command, expectedModel, INDEX_FIRST_PERSON, false);

        /* Case: delete the last person in the list -> deleted */
        Model modelBeforeDeletingLast = new ModelManager(expectedModel.getAddressBook(), new UserPrefs());
        Index lastPersonIndex = getLastIndex(expectedModel);
        command = DeleteCommand.COMMAND_WORD + " " + String.valueOf(lastPersonIndex.getOneBased());
        assertDeleteCommandSuccess(command, expectedModel, lastPersonIndex, false);

        /* Case: undo deleting the last person in the list -> last person restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage, false);

        /* Case: redo deleting the last person in the list -> last person deleted again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedModel, expectedResultMessage, false);

        /* Case: delete the middle person in the list -> deleted */
        Index middlePersonIndex = getMidIndex(expectedModel);
        command = DeleteCommand.COMMAND_WORD + " " + String.valueOf(middlePersonIndex.getOneBased());
        assertDeleteCommandSuccess(command, expectedModel, middlePersonIndex, false);

        /* Case: delete the selected person -> person list panel selects the person before the deleted person */
        Index selectedIndex = getMidIndex(expectedModel);
        getPersonListPanel().select(selectedIndex.getZeroBased());
        command = DeleteCommand.COMMAND_WORD + " " + String.valueOf(selectedIndex.getOneBased());
        assertDeleteCommandSuccess(command, expectedModel, selectedIndex, true);

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
     * Asserts that after executing the {@code command}, the GUI components display what we expected,
     * and the model and storage are modified accordingly.
     */
    private void assertDeleteCommandSuccess(String command, Model expectedModel, Index index,
            boolean browserUrlWillChange) throws Exception {
        ReadOnlyPerson targetPerson = getPerson(expectedModel, index);
        String expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, targetPerson);
        expectedModel.deletePerson(targetPerson);

        assertCommandSuccess(command, expectedModel, expectedResultMessage, browserUrlWillChange);
    }

    /**
     * Asserts that after executing the command {@code commandToRun}, the GUI components display what we expected,
     * and the model and storage are modified accordingly.
     */
    private void assertCommandSuccess(String commandToRun, Model expectedModel, String expectedResultMessage,
            boolean browserUrlWillChange) throws Exception {

        rememberStates();
        runCommand(commandToRun);
        if (browserUrlWillChange) {
            waitUntilBrowserLoaded(getBrowserPanel());
            assertBrowserUrlAndSelectedCardChanged();
        } else {
            assertBrowserUrlAndSelectedCardUnchanged();
        }

        assertEquals("", getCommandBox().getInput());
        assertListMatching(getPersonListPanel(),
                expectedModel.getAddressBook().getPersonList().toArray(new ReadOnlyPerson[0]));
        assertEquals(expectedResultMessage, getResultDisplay().getText());
        assertEquals(expectedModel.getAddressBook(), getTestApp().readStorageAddressBook());
        assertEquals(expectedModel, getTestApp().getModel());
        assertOnlySyncStatusChanged();

        clockRule.setInjectedClockToCurrentTime();
    }

    /**
     * Asserts that after executing the command {@code commandToRun}, the GUI components remain unchanged, except for
     * the {@code ResultDisplay} displaying {@code expectedResultMessage}. The model and storage remains unchanged.
     */
    private void assertCommandFailure(String commandToRun, String expectedResultMessage) throws Exception {
        Model expectedModel = new ModelManager(
                new AddressBook(getTestApp().getModel().getAddressBook()), new UserPrefs());

        rememberStates();
        runCommand(commandToRun);

        assertEquals(commandToRun, getCommandBox().getInput());
        assertBrowserUrlAndSelectedCardUnchanged();
        assertEquals(expectedResultMessage, getResultDisplay().getText());
        assertEquals(expectedModel.getAddressBook(), getTestApp().readStorageAddressBook());
        assertEquals(expectedModel, getTestApp().getModel());
        assertStatusBarUnchanged();

        clockRule.setInjectedClockToCurrentTime();
    }
}
