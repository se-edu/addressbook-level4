package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EDUCATION_LEVEL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHOOL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_BOB;

import static seedu.address.model.person.PersonSortUtil.CATEGORY_EDUCATION_LEVEL;
import static seedu.address.model.person.PersonSortUtil.CATEGORY_GRADE;
import static seedu.address.model.person.PersonSortUtil.CATEGORY_NAME;
import static seedu.address.model.person.PersonSortUtil.CATEGORY_SCHOOL;
import static seedu.address.model.person.PersonSortUtil.CATEGORY_SUBJECT;

import static seedu.address.testutil.typicaladdressbook.TypicalAddressBookCompiler.getTypicalAddressBook2;
import static seedu.address.testutil.typicaladdressbook.TypicalTutees.ALICETUTEE;
import static seedu.address.testutil.typicaladdressbook.TypicalTutees.AMYTUTEE;
import static seedu.address.testutil.typicaladdressbook.TypicalTutees.BOBTUTEE;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

//@@author yungyung04
/**
 * Contains integration tests (interaction with the Model) for {@code FindPersonCommand}.
 */
public class FindPersonCommandTest {
    private static final int INDEX_FIRST_ELEMENT = 0;
    private static final int INDEX_SECOND_ELEMENT = 1;

    private Model model = new ModelManager(getTypicalAddressBook2(), new UserPrefs());

    private final String[] firstNameKeywords = {VALID_NAME_BOB.split("\\s+")[INDEX_FIRST_ELEMENT],
            VALID_NAME_AMY.split("\\s+")[INDEX_SECOND_ELEMENT]};
    private final String[] secondNameKeywords = {VALID_NAME_BOB.split("\\s+")[INDEX_FIRST_ELEMENT]};

    private final FindPersonCommand findFirstName = new FindPersonCommand(CATEGORY_NAME, firstNameKeywords);
    private final FindPersonCommand findSecondName = new FindPersonCommand(CATEGORY_NAME, secondNameKeywords);

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(findFirstName.equals(findFirstName));

        // same values -> returns true
        FindPersonCommand findFirstCommandCopy = new FindPersonCommand(CATEGORY_NAME, firstNameKeywords);
        assertTrue(findFirstName.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstName.equals(1));

        // null -> returns false
        assertFalse(findFirstName.equals(null));

        // different person -> returns false
        assertFalse(findFirstName.equals(findSecondName));
    }

    @Test
    public void execute_findName_foundSuccessfully() {
        //multiple keywords
        findFirstName.setData(model, new CommandHistory(), new UndoRedoStack());
        String expectedMessage = String.format(FindPersonCommand.MESSAGE_SUCCESS + "\n"
                + MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        assertCommandSuccess(findFirstName, expectedMessage, Arrays.asList(AMYTUTEE, BOBTUTEE));

        //single keyword
        findSecondName.setData(model, new CommandHistory(), new UndoRedoStack());
        expectedMessage = String.format(FindPersonCommand.MESSAGE_SUCCESS + "\n" + MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        assertCommandSuccess(findSecondName, expectedMessage, Arrays.asList(BOBTUTEE));
    }

    @Test
    public void execute_findEducatonLevel_foundSuccessfully() {
        String[] educationLevelKeywords = {VALID_EDUCATION_LEVEL_AMY};
        FindPersonCommand findEducationLevel = new FindPersonCommand(CATEGORY_EDUCATION_LEVEL, educationLevelKeywords);
        findEducationLevel.setData(model, new CommandHistory(), new UndoRedoStack());
        String expectedMessage = String.format(FindPersonCommand.MESSAGE_SUCCESS + "\n"
                + MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        //Alice and Amy have the same education level
        assertCommandSuccess(findEducationLevel, expectedMessage, Arrays.asList(ALICETUTEE, AMYTUTEE));
    }

    @Test
    public void execute_findGrade_foundSuccessfully() {
        String[] gradeKeywords = {VALID_GRADE_AMY, VALID_GRADE_BOB};
        FindPersonCommand findGrade = new FindPersonCommand(CATEGORY_GRADE, gradeKeywords);
        findGrade.setData(model, new CommandHistory(), new UndoRedoStack());
        String expectedMessage = String.format(FindPersonCommand.MESSAGE_SUCCESS + "\n"
                + MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        assertCommandSuccess(findGrade, expectedMessage, Arrays.asList(AMYTUTEE, BOBTUTEE));
    }

    @Test
    public void execute_findSchool_foundSuccessfully() {
        String[] schoolKeywords = {VALID_SCHOOL_AMY.split("\\s+")[INDEX_FIRST_ELEMENT]};
        FindPersonCommand findSchool = new FindPersonCommand(CATEGORY_SCHOOL, schoolKeywords);
        findSchool.setData(model, new CommandHistory(), new UndoRedoStack());
        String expectedMessage = String.format(FindPersonCommand.MESSAGE_SUCCESS + "\n"
                + MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        assertCommandSuccess(findSchool, expectedMessage, Arrays.asList(AMYTUTEE));
    }

    @Test
    public void execute_findSubject_foundSuccessfully() {
        String[] subjectKeywords = {VALID_SUBJECT_BOB};
        FindPersonCommand findSubject = new FindPersonCommand(CATEGORY_SUBJECT, subjectKeywords);
        findSubject.setData(model, new CommandHistory(), new UndoRedoStack());
        String expectedMessage = String.format(FindPersonCommand.MESSAGE_SUCCESS + "\n"
                + MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        //Alice and Bob learn the same subject.
        assertCommandSuccess(findSubject, expectedMessage, Arrays.asList(ALICETUTEE, BOBTUTEE));
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Person>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindPersonCommand command, String expectedMessage, List<Person> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
