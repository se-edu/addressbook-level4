package systemtests;

import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.HOON;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonUtil;

public class AddCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void add() throws Exception {
        Model expectedModel = new ModelManager(new AddressBook(getTestApp().getModel().getAddressBook()),
                new UserPrefs());

        /* Case: add a person to a non-empty list, command with leading spaces and trailing spaces -> added */
        ReadOnlyPerson personToAdd = HOON;
        String command = "   " + PersonUtil.getAddCommand(personToAdd) + "   ";
        expectedModel.addPerson(personToAdd);
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, personToAdd);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: add a duplicate person -> rejected */
        command = PersonUtil.getAddCommand(personToAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add to empty list -> added */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getPersonListPanel().getListSize() == 0;
        personToAdd = ALICE;
        command = "   " + PersonUtil.getAddCommand(personToAdd) + "   ";
        expectedModel = new ModelManager();
        expectedModel.addPerson(personToAdd);
        expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, personToAdd);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: invalid keyword -> rejected */
        command = "adds " + PersonUtil.getPersonDetails(personToAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}. Also verifies that
     * the command box has the default style class, the status bar's sync status changes, the browser url and selected
     * card remains unchanged.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage)
            throws Exception {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxStyleDefault();
        assertStatusBarUnchangedExceptSyncStatus();

        clockRule.setInjectedClockToCurrentTime();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}. Also verifies that
     * the browser url, selected card and status bar remain unchanged, and the command box has the error style.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) throws Exception {
        Model expectedModel = new ModelManager(
                new AddressBook(getTestApp().getModel().getAddressBook()), new UserPrefs());

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxStyleError();
        assertStatusBarUnchanged();

        clockRule.setInjectedClockToCurrentTime();
    }
}
