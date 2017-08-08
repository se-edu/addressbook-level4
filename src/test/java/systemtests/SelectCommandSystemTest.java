package systemtests;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.SelectCommand.MESSAGE_SELECT_PERSON_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class SelectCommandSystemTest extends AddressBookSystemTest {
    @Test
    public void select_nonEmptyList() throws Exception {
        int invalidIndex = getTestApp().getModel().getFilteredPersonList().size() + 1;

        /* Case: invalid index (size + 1) -> rejected */
        assertCommandFailure(SelectCommand.COMMAND_WORD + " " + invalidIndex, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: select the first card in the list, command with leading spaces and trailing spaces -> selected */
        String command = "   " + SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + "   ";
        assertCommandSuccess(command, INDEX_FIRST_PERSON);

        /* Case: select the last card in the list -> selected */
        Index personCount = Index.fromOneBased(getTypicalPersons().size());
        command = SelectCommand.COMMAND_WORD + " " + personCount.getOneBased();
        assertCommandSuccess(command, personCount);

        /* Case: undo previous selection -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo selecting last card in the list -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: select the middle card in the list -> selected */
        Index middleIndex = Index.fromOneBased(personCount.getOneBased() / 2);
        command = SelectCommand.COMMAND_WORD + " " + middleIndex.getOneBased();
        assertCommandSuccess(command, middleIndex);

        /* Case: select the current selected card -> selected */
        assertCommandSuccess(command, middleIndex);

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(SelectCommand.COMMAND_WORD + " " + 0,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(SelectCommand.COMMAND_WORD + " " + -1,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(SelectCommand.COMMAND_WORD + " abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(SelectCommand.COMMAND_WORD + " 1 abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("SeLeCt 1", MESSAGE_UNKNOWN_COMMAND);

        /* Case: select from empty list -> rejected */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getTestApp().getModel().getAddressBook().getPersonList().size() == 0;
        assertCommandFailure(SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased(),
                MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays the success message of executing select command with the {@code expectedSelectedCardIndex}
     * of the selected person, and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertCommandExecution(String, String, String, Model)}.<br>
     * Also verifies that the command box has the default style class and the status bar remain unchanged. The resulting
     * browser url and selected card will be verified if the current selected card and the card at
     * {@code expectedSelectedCardIndex} are different.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Index expectedSelectedCardIndex) throws Exception {
        Model expectedModel = new ModelManager(new AddressBook(getTestApp().getModel().getAddressBook()),
                new UserPrefs());
        String expectedResultMessage = String.format(
                MESSAGE_SELECT_PERSON_SUCCESS, expectedSelectedCardIndex.getOneBased());
        int currentSelectedCardIndex = getPersonListPanel().getSelectedCardIndex();

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (currentSelectedCardIndex != expectedSelectedCardIndex.getZeroBased()) {
            waitUntilBrowserLoaded(getBrowserPanel());
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }

        assertCommandBoxStyleDefault();
        assertStatusBarUnchanged();

        clockRule.setInjectedClockToCurrentTime();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertCommandExecution(String, String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) throws Exception {
        Model expectedModel = new ModelManager(new AddressBook(getTestApp().getModel().getAddressBook()),
                new UserPrefs());

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxStyleError();
        assertStatusBarUnchanged();

        clockRule.setInjectedClockToCurrentTime();
    }
}
