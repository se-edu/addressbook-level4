package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS;
import static seedu.address.testutil.TestUtil.getLastIndex;
import static seedu.address.testutil.TestUtil.getMidIndex;
import static seedu.address.testutil.TestUtil.getPerson;
import static seedu.address.testutil.TypicalPersons.INDEX_FIRST_PERSON;
import static systemtests.AppStateAsserts.assertCommandFailure;
import static systemtests.AppStateAsserts.assertCommandSuccess;

import org.junit.Test;

import guitests.GuiRobot;
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

    private static final String INVALID_DELETE_COMMAND_FORMAT_MESSAGE =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);

    @Test
    public void delete() throws Exception {
        Model expectedModel = new ModelManager(new AddressBook(getTestApp().getModel().getAddressBook()),
                new UserPrefs());

        // delete the first in the list
        ReadOnlyPerson targetPerson = getPerson(expectedModel, INDEX_FIRST_PERSON);
        String command = "     " + DeleteCommand.COMMAND_WORD + "      " + INDEX_FIRST_PERSON.getOneBased() + "       ";
        String expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, targetPerson);
        expectedModel.deletePerson(targetPerson);
        assertCommandSuccess(this, command, expectedModel, expectedResultMessage, false, false);

        // delete the last in the list
        Model modelBeforeDeletingLast = new ModelManager(expectedModel.getAddressBook(), new UserPrefs());
        Index lastPersonIndex = getLastIndex(expectedModel);
        targetPerson = getPerson(expectedModel, lastPersonIndex);
        command = DeleteCommand.COMMAND_WORD + " " + String.valueOf(lastPersonIndex.getOneBased());
        expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, targetPerson);
        expectedModel.deletePerson(targetPerson);
        assertCommandSuccess(this, command, expectedModel, expectedResultMessage, false, false);

        // undo deleting the last person
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(this, command, modelBeforeDeletingLast, expectedResultMessage, false, false);

        // redo deleting the last person
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(this, command, expectedModel, expectedResultMessage, false, false);

        // delete from the middle of the list
        Index middlePersonIndex = getMidIndex(expectedModel);
        targetPerson = getPerson(expectedModel, middlePersonIndex);
        command = DeleteCommand.COMMAND_WORD + " " + String.valueOf(middlePersonIndex.getOneBased());
        expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, targetPerson);
        expectedModel.deletePerson(targetPerson);
        assertCommandSuccess(this, command, expectedModel, expectedResultMessage, false, false);

        // delete the selected person -> person list panel now selects the person that was before the deleted person
        Index selectedIndex = getMidIndex(expectedModel);
        getPersonListPanel().select(selectedIndex.getZeroBased());
        targetPerson = getPerson(expectedModel, selectedIndex);
        command = DeleteCommand.COMMAND_WORD + " " + String.valueOf(selectedIndex.getOneBased());
        expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, targetPerson);
        expectedModel.deletePerson(targetPerson);
        assertCommandSuccess(this, command, expectedModel, expectedResultMessage, true, true);

        // as a new person is selected in the previous test case, causing the browser to reload,
        // it needs time to load the new page
        new GuiRobot().sleep(5000);

        // invalid index
        Index outOfBoundsIndex = Index.fromOneBased(expectedModel.getAddressBook().getPersonList().size() + 1);
        command = DeleteCommand.COMMAND_WORD + " " + String.valueOf(outOfBoundsIndex.getOneBased());
        assertCommandFailure(this, command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // invalid arguments
        assertCommandFailure(this,
                DeleteCommand.COMMAND_WORD + " abc", INVALID_DELETE_COMMAND_FORMAT_MESSAGE);
        assertCommandFailure(this,
                DeleteCommand.COMMAND_WORD + " 1 abc", INVALID_DELETE_COMMAND_FORMAT_MESSAGE);

        // case sensitivity
        assertCommandFailure(this, "    DelETE 1", MESSAGE_UNKNOWN_COMMAND);
    }

}
