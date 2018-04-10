package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EDUCATION_LEVEL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EDUCATION_LEVEL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.GRADE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.GRADE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EDUCATION_LEVEL;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GRADE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SUBJECT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.SCHOOL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SCHOOL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EDUCATION_LEVEL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHOOL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.typicaladdressbook.TypicalPersons.KEYWORD_MATCHING_MEIER;
import static seedu.address.testutil.typicaladdressbook.TypicalTutees.ALICETUTEE;
import static seedu.address.testutil.typicaladdressbook.TypicalTutees.AMYTUTEE;
import static seedu.address.testutil.typicaladdressbook.TypicalTutees.BOBTUTEE;
import static seedu.address.testutil.typicaladdressbook.TypicalTutees.CARLTUTEE;
import static seedu.address.testutil.typicaladdressbook.TypicalTutees.HOONTUTEE;
import static seedu.address.testutil.typicaladdressbook.TypicalTutees.IDATUTEE;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddTuteeCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tutee.EducationLevel;
import seedu.address.model.tutee.Grade;
import seedu.address.model.tutee.School;
import seedu.address.model.tutee.Subject;
import seedu.address.model.tutee.Tutee;
import seedu.address.testutil.TuteeBuilder;
import seedu.address.testutil.TuteeUtil;

//@@author ChoChihTun
public class AddTuteeCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void addtutee() throws Exception {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a person without tags to a non-empty address book, command with leading spaces and trailing spaces
         * -> added
         */
        Tutee toAdd = AMYTUTEE;
        String command = "   " + AddTuteeCommand.COMMAND_WORD + "  " + NAME_DESC_AMY + "  " + PHONE_DESC_AMY + " "
                + EMAIL_DESC_AMY + "   " + ADDRESS_DESC_AMY + "   " + SUBJECT_DESC_AMY + GRADE_DESC_AMY
                + EDUCATION_LEVEL_DESC_AMY + SCHOOL_DESC_AMY + TAG_DESC_FRIEND + " ";
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

        /* Case: add a tutee with all fields same as another tutee in the address book except name -> added */
        toAdd = new TuteeBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withSubject(VALID_SUBJECT_AMY).withGrade(VALID_GRADE_AMY)
                .withEducationLevel(VALID_EDUCATION_LEVEL_AMY).withSchool(VALID_SCHOOL_AMY).withTags(VALID_TAG_FRIEND)
                .build();
        command = AddTuteeCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + SUBJECT_DESC_AMY + GRADE_DESC_AMY + EDUCATION_LEVEL_DESC_AMY + SCHOOL_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a tutee with all fields same as another tutee in the address book except phone -> added */
        toAdd = new TuteeBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).withSubject(VALID_SUBJECT_AMY)
                .withGrade(VALID_GRADE_AMY).withEducationLevel(VALID_EDUCATION_LEVEL_AMY).withSchool(VALID_SCHOOL_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        command = AddTuteeCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + SUBJECT_DESC_AMY + GRADE_DESC_AMY + EDUCATION_LEVEL_DESC_AMY + SCHOOL_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a tutee with all fields same as another tutee in the address book except email -> added */
        toAdd = new TuteeBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_AMY).withSubject(VALID_SUBJECT_AMY).withGrade(VALID_GRADE_AMY)
                .withEducationLevel(VALID_EDUCATION_LEVEL_AMY).withSchool(VALID_SCHOOL_AMY).withTags(VALID_TAG_FRIEND)
                .withTags(VALID_TAG_FRIEND).build();
        command = AddTuteeCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
                + SUBJECT_DESC_AMY + GRADE_DESC_AMY + EDUCATION_LEVEL_DESC_AMY + SCHOOL_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the address book except address -> added */
        toAdd = new TuteeBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_BOB).withSubject(VALID_SUBJECT_AMY).withGrade(VALID_GRADE_AMY)
                .withEducationLevel(VALID_EDUCATION_LEVEL_AMY).withSchool(VALID_SCHOOL_AMY).withTags(VALID_TAG_FRIEND)
                .withTags(VALID_TAG_FRIEND).build();
        command = AddTuteeCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_BOB
                + SUBJECT_DESC_AMY + GRADE_DESC_AMY + EDUCATION_LEVEL_DESC_AMY + SCHOOL_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty address book -> added */
        deleteAllPersons();
        assertCommandSuccess(ALICETUTEE);

        /* Case: add a person with tags, command with parameters in random order -> added */
        toAdd = BOBTUTEE;
        command = AddTuteeCommand.COMMAND_WORD + TAG_DESC_FRIEND + PHONE_DESC_BOB + ADDRESS_DESC_BOB + NAME_DESC_BOB
                + TAG_DESC_HUSBAND + EMAIL_DESC_BOB + GRADE_DESC_BOB + SUBJECT_DESC_BOB + EDUCATION_LEVEL_DESC_BOB
                + SCHOOL_DESC_BOB;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person, missing tags -> added */
        assertCommandSuccess(HOONTUTEE);

        /* -------------------------- Perform add operation on the shown filtered list ------------------------------ */

        /* Case: filters the person list before adding -> added */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        assertCommandSuccess(IDATUTEE);

        /* ------------------------ Perform add operation while a person card is selected --------------------------- */

        /* Case: selects first card in the person list, add a person -> added, card selection remains unchanged */
        selectPerson(Index.fromOneBased(1));
        assertCommandSuccess(CARLTUTEE);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate tutee -> rejected */
        command = TuteeUtil.getAddTuteeCommand(HOONTUTEE);
        assertCommandFailure(command, AddTuteeCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add a duplicate tutee except with different tags -> rejected */
        // "friends" is an existing tag used in the default model, see TypicalPersons#ALICE
        // This test will fail if a new tag that is not in the model is used, see the bug documented in
        // AddressBook#addPerson(Person)
        command = TuteeUtil.getAddTuteeCommand(HOONTUTEE) + " " + PREFIX_TAG.getPrefix() + "friends";
        assertCommandFailure(command, AddTuteeCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: missing name -> rejected */
        command = AddTuteeCommand.COMMAND_WORD + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + SUBJECT_DESC_AMY + GRADE_DESC_AMY + EDUCATION_LEVEL_DESC_AMY + SCHOOL_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuteeCommand.MESSAGE_USAGE));

        /* Case: missing phone -> rejected */
        command = AddTuteeCommand.COMMAND_WORD + NAME_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + SUBJECT_DESC_AMY + GRADE_DESC_AMY + EDUCATION_LEVEL_DESC_AMY + SCHOOL_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuteeCommand.MESSAGE_USAGE));

        /* Case: missing email -> rejected */
        command = AddTuteeCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + ADDRESS_DESC_AMY
                + SUBJECT_DESC_AMY + GRADE_DESC_AMY + EDUCATION_LEVEL_DESC_AMY + SCHOOL_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuteeCommand.MESSAGE_USAGE));

        /* Case: missing address -> rejected */
        command = AddTuteeCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + SUBJECT_DESC_AMY + GRADE_DESC_AMY + EDUCATION_LEVEL_DESC_AMY + SCHOOL_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuteeCommand.MESSAGE_USAGE));

        /* Case: missing subject -> rejected */
        command = AddTuteeCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + GRADE_DESC_AMY + EDUCATION_LEVEL_DESC_AMY + SCHOOL_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuteeCommand.MESSAGE_USAGE));

        /* Case: missing grade -> rejected */
        command = AddTuteeCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + GRADE_DESC_AMY + EDUCATION_LEVEL_DESC_AMY + SCHOOL_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuteeCommand.MESSAGE_USAGE));

        /* Case: missing education level -> rejected */
        command = AddTuteeCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + SUBJECT_DESC_AMY + GRADE_DESC_AMY + SCHOOL_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuteeCommand.MESSAGE_USAGE));

        /* Case: missing school -> rejected */
        command = AddTuteeCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + SUBJECT_DESC_AMY + GRADE_DESC_AMY + EDUCATION_LEVEL_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuteeCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "addtutees " + TuteeUtil.getTuteeDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddTuteeCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + SUBJECT_DESC_AMY + GRADE_DESC_AMY + EDUCATION_LEVEL_DESC_AMY + SCHOOL_DESC_AMY;
        assertCommandFailure(command, Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        command = AddTuteeCommand.COMMAND_WORD + NAME_DESC_AMY + INVALID_PHONE_DESC + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + SUBJECT_DESC_AMY + GRADE_DESC_AMY + EDUCATION_LEVEL_DESC_AMY + SCHOOL_DESC_AMY;
        assertCommandFailure(command, Phone.MESSAGE_PHONE_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        command = AddTuteeCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + INVALID_EMAIL_DESC + ADDRESS_DESC_AMY
                + SUBJECT_DESC_AMY + GRADE_DESC_AMY + EDUCATION_LEVEL_DESC_AMY + SCHOOL_DESC_AMY;
        assertCommandFailure(command, Email.MESSAGE_EMAIL_CONSTRAINTS);

        /* Case: invalid address -> rejected */
        command = AddTuteeCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + INVALID_ADDRESS_DESC
                + SUBJECT_DESC_AMY + GRADE_DESC_AMY + EDUCATION_LEVEL_DESC_AMY + SCHOOL_DESC_AMY;
        assertCommandFailure(command, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        command = AddTuteeCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + SUBJECT_DESC_AMY + GRADE_DESC_AMY + EDUCATION_LEVEL_DESC_AMY + SCHOOL_DESC_AMY + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);

        /* Case: invalid subject -> rejected */
        command = AddTuteeCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + INVALID_SUBJECT_DESC + GRADE_DESC_AMY + EDUCATION_LEVEL_DESC_AMY + SCHOOL_DESC_AMY + INVALID_TAG_DESC;
        assertCommandFailure(command, Subject.MESSAGE_SUBJECT_CONSTRAINTS);

        /* Case: invalid grade -> rejected */
        command = AddTuteeCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + SUBJECT_DESC_AMY + INVALID_GRADE_DESC + EDUCATION_LEVEL_DESC_AMY + SCHOOL_DESC_AMY + INVALID_TAG_DESC;
        assertCommandFailure(command, Grade.MESSAGE_GRADE_CONSTRAINTS);

        /* Case: invalid education level -> rejected */
        command = AddTuteeCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + SUBJECT_DESC_AMY + GRADE_DESC_AMY + INVALID_EDUCATION_LEVEL + SCHOOL_DESC_AMY + INVALID_TAG_DESC;
        assertCommandFailure(command, EducationLevel.MESSAGE_EDUCATION_LEVEL_CONSTRAINTS);

        /* Case: invalid school -> rejected */
        command = AddTuteeCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + SUBJECT_DESC_AMY + GRADE_DESC_AMY + EDUCATION_LEVEL_DESC_AMY + INVALID_SCHOOL + INVALID_TAG_DESC;
        assertCommandFailure(command, School.MESSAGE_SCHOOL_CONSTRAINTS);
    }
    /**
     * Executes the {@code AddTuteeCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddTuteeCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Tutee toAdd) {
        assertCommandSuccess(TuteeUtil.getAddTuteeCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Tutee)}. Executes {@code command}
     * instead.
     * @see AddTuteeCommandSystemTest#assertCommandSuccess(Tutee)
     */
    private void assertCommandSuccess(String command, Tutee toAdd) {
        Model expectedModel = getModel();
        try {
            expectedModel.addPerson(toAdd);
        } catch (DuplicatePersonException dpe) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }
        String expectedResultMessage = String.format(AddTuteeCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Person)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddTuteeCommandSystemTest#assertCommandSuccess(String, Tutee)
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
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
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
