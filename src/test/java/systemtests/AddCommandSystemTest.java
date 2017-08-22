package systemtests;

import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.HOON;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonUtil;

public class AddCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void add() throws Exception {
        Model expectedModel = new ModelManager(new AddressBook(getTestApp().getModel().getAddressBook()),
                new UserPrefs());

        /* Case: add a person without tags to a non-empty list, command with leading spaces and trailing spaces ->
        * added */
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

        /* Case: add a person with tags, command with parameters in random order -> added */
        personToAdd = BOB;
        command = AddCommand.COMMAND_WORD + TAG_DESC_FRIEND + PHONE_DESC_BOB + ADDRESS_DESC_BOB + NAME_DESC_BOB
                + TAG_DESC_HUSBAND + EMAIL_DESC_BOB;
        expectedModel.addPerson(personToAdd);
        expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, personToAdd);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: selects first card in the list, add a person -> added, card selection remains unchanged */
        executeCommand(SelectCommand.COMMAND_WORD + " 1");
        assert getPersonListPanel().getSelectedCardIndex() == 0;
        personToAdd = CARL;
        command = PersonUtil.getAddCommand(personToAdd);
        expectedModel.addPerson(personToAdd);
        expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, personToAdd);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: invalid keyword -> rejected */
        command = "adds " + PersonUtil.getPersonDetails(personToAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY;
        assertCommandFailure(command, Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + INVALID_PHONE_DESC + EMAIL_DESC_AMY + ADDRESS_DESC_AMY;
        assertCommandFailure(command, Phone.MESSAGE_PHONE_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + INVALID_EMAIL_DESC + ADDRESS_DESC_AMY;
        assertCommandFailure(command, Email.MESSAGE_EMAIL_CONSTRAINTS);

        /* Case: invalid address -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + INVALID_ADDRESS_DESC;
        assertCommandFailure(command, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);
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
