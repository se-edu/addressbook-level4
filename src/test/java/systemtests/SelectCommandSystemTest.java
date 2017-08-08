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
        assertSelectCommandSuccess(command, INDEX_FIRST_PERSON, true);

        /* Case: select the last card in the list -> selected */
        Index personCount = Index.fromOneBased(getTypicalPersons().size());
        command = SelectCommand.COMMAND_WORD + " " + personCount.getOneBased();
        assertSelectCommandSuccess(command, personCount, true);

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
        assertSelectCommandSuccess(command, middleIndex, true);

        /* Case: invalid index (size + 1) -> rejected */
        assertCommandFailure(SelectCommand.COMMAND_WORD + " " + invalidIndex, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: select the current selected card -> selected */
        assertSelectCommandSuccess(command, middleIndex, false);

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
    }

    @Test
    public void selectPerson_emptyList() throws Exception {
        runCommand(ClearCommand.COMMAND_WORD);
        assert getPersonListPanel().getListSize() == 0;

        /* Case: invalid index (1) -> rejected */
        assertCommandFailure(SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased(),
                MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays the success message of executing select command with the {@code index} of the selected person,
     * and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertCommandExecution(String, String, String, Model)}. Also verifies that
     * the command box has the default style class, the status bar remain unchanged, the browser url and selected
     * card changes depending on {@code browserUrlWillChange}.
     * @see AddressBookSystemTest#assertCommandExecution(String, String, String, Model)
     */
    private void assertSelectCommandSuccess(String command, Index index,
            boolean browserUrlWillChange) throws Exception {
        Model expectedModel = new ModelManager(new AddressBook(getTestApp().getModel().getAddressBook()),
                new UserPrefs());
        String expectedResultMessage = String.format(MESSAGE_SELECT_PERSON_SUCCESS, index.getOneBased());

        assertCommandExecution(command, "", expectedResultMessage, expectedModel);
        if (browserUrlWillChange) {
            waitUntilBrowserLoaded(getBrowserPanel());
            assertBrowserUrlAndSelectedCardChanged();
        } else {
            assertBrowserUrlAndSelectedCardUnchanged();
        }

        assertCommandBoxStyleDefault();
        assertStatusBarUnchanged();

        clockRule.setInjectedClockToCurrentTime();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertCommandExecution(String, String, String, Model)}. Also verifies that
     * the browser url, selected card and status bar remain unchanged, and the command box has the error style.
     * @see AddressBookSystemTest#assertCommandExecution(String, String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) throws Exception {
        Model expectedModel = new ModelManager(new AddressBook(getTestApp().getModel().getAddressBook()),
                new UserPrefs());
        assertCommandExecution(command, command, expectedResultMessage, expectedModel);
        assertCommandBoxStyleError();
        assertBrowserUrlAndSelectedCardUnchanged();
        assertStatusBarUnchanged();

        clockRule.setInjectedClockToCurrentTime();
    }
}
