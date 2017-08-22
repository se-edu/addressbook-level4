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
        /* Case: add a person without tags to a non-empty address book, command with leading spaces and trailing spaces
        * -> added */
        ReadOnlyPerson toAdd = HOON;
        String command = "   " + PersonUtil.getAddCommand(toAdd) + "   ";
        assertCommandSuccess(command, toAdd);

        /* Case: add a duplicate person -> rejected */
        command = PersonUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add to empty address book -> added */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getTestApp().getModel().getAddressBook().getPersonList().size() == 0;
        toAdd = ALICE;
        command = "   " + PersonUtil.getAddCommand(toAdd) + "   ";
        assertCommandSuccess(command, toAdd);

        /* Case: invalid keyword -> rejected */
        command = "adds " + PersonUtil.getPersonDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays the success message of executing {@code AddCommand} with the details of {@code toAdd}, and the
     * model related components equal to the current model added with {@code toAdd}. These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class, the status bar's sync status changes,
     * the browser url and selected card remains unchanged.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, ReadOnlyPerson toAdd) throws Exception {
        Model expectedModel = new ModelManager(new AddressBook(getTestApp().getModel().getAddressBook()),
                new UserPrefs());
        expectedModel.addPerson(toAdd);
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

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
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
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
