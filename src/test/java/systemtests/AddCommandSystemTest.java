package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DATEOFBIRTH_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DATEOFBIRTH_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DEPARTMENT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DEPARTMENT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMPLOYEEID_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMPLOYEEID_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATEOFBIRTH_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEPARTMENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMPLOYEEID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_POSITION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SALARY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.POSITION_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.POSITION_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.SALARY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SALARY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Department;
import seedu.address.model.person.Email;
import seedu.address.model.person.EmployeeId;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Position;
import seedu.address.model.person.Salary;
import seedu.address.model.person.tag.Tag;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void add() {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a person without tags to a non-empty address book, command with leading spaces and trailing spaces
         * -> added
         */
        Person toAdd = AMY;
        String command = "   " + AddCommand.COMMAND_WORD + "  " + EMPLOYEEID_DESC_AMY + " " + NAME_DESC_AMY + "  "
                + DATEOFBIRTH_DESC_AMY + " " + PHONE_DESC_AMY + " " + EMAIL_DESC_AMY + " " + DEPARTMENT_DESC_AMY
                + " " + POSITION_DESC_AMY + "   " + ADDRESS_DESC_AMY + " " + SALARY_DESC_AMY + "   "
                + TAG_DESC_FRIEND + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Amy to the list -> Amy deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Amy to the list -> Amy added again */
        command = RedoCommand.COMMAND_WORD;
        model.addPerson(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a person with all fields same as another person in the address book except name -> added */
        toAdd = new PersonBuilder(AMY).withName(VALID_NAME_BOB).build();
        command = AddCommand.COMMAND_WORD + EMPLOYEEID_DESC_AMY + NAME_DESC_BOB + DATEOFBIRTH_DESC_AMY
                + PHONE_DESC_AMY + EMAIL_DESC_AMY + DEPARTMENT_DESC_AMY + POSITION_DESC_AMY
                + ADDRESS_DESC_AMY + SALARY_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the address book except phone and email
         * -> added
         */
        toAdd = new PersonBuilder(AMY).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        command = PersonUtil.getAddCommand(toAdd);
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty address book -> added */
        deleteAllPersons();
        assertCommandSuccess(ALICE);

        /* Case: add a person with tags, command with parameters in random order -> added */
        toAdd = BOB;
        command = AddCommand.COMMAND_WORD + TAG_DESC_FRIEND + PHONE_DESC_BOB + ADDRESS_DESC_BOB + NAME_DESC_BOB
                + TAG_DESC_HUSBAND + EMAIL_DESC_BOB + EMPLOYEEID_DESC_BOB + SALARY_DESC_BOB + DEPARTMENT_DESC_BOB
                + POSITION_DESC_BOB + DATEOFBIRTH_DESC_BOB;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person, missing tags -> added */
        assertCommandSuccess(HOON);

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the person list before adding -> added */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        assertCommandSuccess(IDA);

        /* ------------------------ Perform add operation while a person card is selected --------------------------- */

        /* Case: selects first card in the person list, add a person -> added, card selection remains unchanged */
        selectPerson(Index.fromOneBased(1));
        assertCommandSuccess(CARL);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate person -> rejected */
        command = PersonUtil.getAddCommand(HOON);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add a duplicate person except with different phone -> rejected */
        toAdd = new PersonBuilder(HOON).withPhone(VALID_PHONE_BOB).build();
        command = PersonUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add a duplicate person except with different email -> rejected */
        toAdd = new PersonBuilder(HOON).withEmail(VALID_EMAIL_BOB).build();
        command = PersonUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add a duplicate person except with different address -> rejected */
        toAdd = new PersonBuilder(HOON).withAddress(VALID_ADDRESS_BOB).build();
        command = PersonUtil.getAddCommand(toAdd);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add a duplicate person except with different tags -> rejected */
        command = PersonUtil.getAddCommand(HOON) + " " + PREFIX_TAG.getPrefix() + "friends";
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: missing employeeId -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + DATEOFBIRTH_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + DEPARTMENT_DESC_AMY + POSITION_DESC_AMY + ADDRESS_DESC_AMY + SALARY_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing name -> rejected */
        command = AddCommand.COMMAND_WORD + EMPLOYEEID_DESC_AMY + DATEOFBIRTH_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + DEPARTMENT_DESC_AMY + POSITION_DESC_AMY + ADDRESS_DESC_AMY + SALARY_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing date of birth -> rejectec */
        command = AddCommand.COMMAND_WORD + EMPLOYEEID_DESC_AMY + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + DEPARTMENT_DESC_AMY + POSITION_DESC_AMY + ADDRESS_DESC_AMY + SALARY_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing phone -> rejected */
        command = AddCommand.COMMAND_WORD + EMPLOYEEID_DESC_AMY + NAME_DESC_AMY + DATEOFBIRTH_DESC_AMY + EMAIL_DESC_AMY
                + DEPARTMENT_DESC_AMY + POSITION_DESC_AMY + ADDRESS_DESC_AMY + SALARY_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing email -> rejected */
        command = AddCommand.COMMAND_WORD + EMPLOYEEID_DESC_AMY + NAME_DESC_AMY + DATEOFBIRTH_DESC_AMY + PHONE_DESC_AMY
                + DEPARTMENT_DESC_AMY + POSITION_DESC_AMY + ADDRESS_DESC_AMY + SALARY_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing department -> rejectec */
        command = AddCommand.COMMAND_WORD + EMPLOYEEID_DESC_AMY + NAME_DESC_AMY + DATEOFBIRTH_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + POSITION_DESC_AMY + ADDRESS_DESC_AMY + SALARY_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing position -> rejectec */
        command = AddCommand.COMMAND_WORD + EMPLOYEEID_DESC_AMY + NAME_DESC_AMY + DATEOFBIRTH_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + DEPARTMENT_DESC_AMY + ADDRESS_DESC_AMY + SALARY_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing address -> rejected */
        command = AddCommand.COMMAND_WORD + EMPLOYEEID_DESC_AMY + NAME_DESC_AMY + DATEOFBIRTH_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + DEPARTMENT_DESC_AMY + POSITION_DESC_AMY + SALARY_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: missing salary -> rejectec */
        command = AddCommand.COMMAND_WORD + EMPLOYEEID_DESC_AMY + NAME_DESC_AMY + DATEOFBIRTH_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + DEPARTMENT_DESC_AMY + POSITION_DESC_AMY + ADDRESS_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + PersonUtil.getPersonDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid employeeId -> rejected */
        command = AddCommand.COMMAND_WORD + INVALID_EMPLOYEEID_DESC + NAME_DESC_AMY + DATEOFBIRTH_DESC_AMY
                + PHONE_DESC_AMY + EMAIL_DESC_AMY + DEPARTMENT_DESC_AMY + POSITION_DESC_AMY
                + ADDRESS_DESC_AMY + SALARY_DESC_AMY;
        assertCommandFailure(command, EmployeeId.MESSAGE_EMPLOYEEID_CONSTRAINTS);

        /* Case: invalid name -> rejected */
        command = AddCommand.COMMAND_WORD + EMPLOYEEID_DESC_AMY + INVALID_NAME_DESC + DATEOFBIRTH_DESC_AMY
                + PHONE_DESC_AMY + EMAIL_DESC_AMY + DEPARTMENT_DESC_AMY + POSITION_DESC_AMY
                + ADDRESS_DESC_AMY + SALARY_DESC_AMY;
        assertCommandFailure(command, Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid date of birth -> rejected */
        command = AddCommand.COMMAND_WORD + EMPLOYEEID_DESC_AMY + NAME_DESC_AMY + INVALID_DATEOFBIRTH_DESC
                + PHONE_DESC_AMY + EMAIL_DESC_AMY + DEPARTMENT_DESC_AMY + POSITION_DESC_AMY
                + ADDRESS_DESC_AMY + SALARY_DESC_AMY;
        assertCommandFailure(command, DateOfBirth.MESSAGE_DATEOFBIRTH_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        command = AddCommand.COMMAND_WORD + EMPLOYEEID_DESC_AMY + NAME_DESC_AMY + DATEOFBIRTH_DESC_AMY
                + INVALID_PHONE_DESC + EMAIL_DESC_AMY + DEPARTMENT_DESC_AMY + POSITION_DESC_AMY
                + ADDRESS_DESC_AMY + SALARY_DESC_AMY;
        assertCommandFailure(command, Phone.MESSAGE_PHONE_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        command = AddCommand.COMMAND_WORD + EMPLOYEEID_DESC_AMY + NAME_DESC_AMY + DATEOFBIRTH_DESC_AMY
                + PHONE_DESC_AMY + INVALID_EMAIL_DESC + DEPARTMENT_DESC_AMY + POSITION_DESC_AMY
                + ADDRESS_DESC_AMY + SALARY_DESC_AMY;
        assertCommandFailure(command, Email.MESSAGE_EMAIL_CONSTRAINTS);

        /* Case: invalid department -> rejected */
        command = AddCommand.COMMAND_WORD + EMPLOYEEID_DESC_AMY + NAME_DESC_AMY + DATEOFBIRTH_DESC_AMY
                + PHONE_DESC_AMY + EMAIL_DESC_AMY + INVALID_DEPARTMENT_DESC + POSITION_DESC_AMY
                + ADDRESS_DESC_AMY + SALARY_DESC_AMY;
        assertCommandFailure(command, Department.MESSAGE_DEPARTMENT_CONSTRAINTS);

        /* Case: invalid position-> rejected */
        command = AddCommand.COMMAND_WORD + EMPLOYEEID_DESC_AMY + NAME_DESC_AMY + DATEOFBIRTH_DESC_AMY
                + PHONE_DESC_AMY + EMAIL_DESC_AMY + DEPARTMENT_DESC_AMY + INVALID_POSITION_DESC
                + ADDRESS_DESC_AMY + SALARY_DESC_AMY;
        assertCommandFailure(command, Position.MESSAGE_POSITION_CONSTRAINTS);

        /* Case: invalid address -> rejected */
        command = AddCommand.COMMAND_WORD + EMPLOYEEID_DESC_AMY + NAME_DESC_AMY + DATEOFBIRTH_DESC_AMY
                + PHONE_DESC_AMY + EMAIL_DESC_AMY + DEPARTMENT_DESC_AMY + POSITION_DESC_AMY
                + INVALID_ADDRESS_DESC + SALARY_DESC_AMY;
        assertCommandFailure(command, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        /* Case: invalid salary -> rejected */
        command = AddCommand.COMMAND_WORD + EMPLOYEEID_DESC_AMY + NAME_DESC_AMY + DATEOFBIRTH_DESC_AMY
                + PHONE_DESC_AMY + EMAIL_DESC_AMY + DEPARTMENT_DESC_AMY + POSITION_DESC_AMY
                + ADDRESS_DESC_AMY + INVALID_SALARY_DESC;
        assertCommandFailure(command, Salary.MESSAGE_SALARY_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        command = AddCommand.COMMAND_WORD + EMPLOYEEID_DESC_AMY + NAME_DESC_AMY + DATEOFBIRTH_DESC_AMY
                + PHONE_DESC_AMY + EMAIL_DESC_AMY + DEPARTMENT_DESC_AMY + POSITION_DESC_AMY
                + ADDRESS_DESC_AMY + SALARY_DESC_AMY + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Person toAdd) {
        assertCommandSuccess(PersonUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Person)}. Executes {@code command}
     * instead.
     * @see AddCommandSystemTest#assertCommandSuccess(Person)
     */
    private void assertCommandSuccess(String command, Person toAdd) {
        Model expectedModel = getModel();
        expectedModel.addPerson(toAdd);
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Person)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddCommandSystemTest#assertCommandSuccess(String, Person)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
