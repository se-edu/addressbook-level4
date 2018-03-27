package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.person.PersonSortUtil.CATEGORY_EDUCATION_LEVEL;
import static seedu.address.model.person.PersonSortUtil.CATEGORY_GRADE;
import static seedu.address.model.person.PersonSortUtil.CATEGORY_NAME;
import static seedu.address.model.person.PersonSortUtil.CATEGORY_SCHOOL;
import static seedu.address.model.person.PersonSortUtil.CATEGORY_SUBJECT;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalTutees.ALICETUTEE;
import static seedu.address.testutil.TypicalTutees.AMYTUTEE;
import static seedu.address.testutil.TypicalTutees.BOBTUTEE;
import static seedu.address.testutil.TypicalTutees.getTypicalAddressBook;

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


/**
 * Contains integration tests (interaction with the Model) for {@code SortCommand}.
 */
public class SortCommandTest {
    private static final int INDEX_FIRST_ELEMENT = 0;
    private static final int AMOUNT_OF_PERSON_INSIDE_TYPICAL_ADDRESS_BOOK = 4;

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private final SortCommand sortName = new SortCommand(CATEGORY_NAME);

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(sortName.equals(sortName));

        // same values -> returns true
        SortCommand sortNameCopy = new SortCommand(CATEGORY_NAME);
        assertTrue(sortName.equals(sortNameCopy));

        // different types -> returns false
        assertFalse(sortName.equals(1));

        // null -> returns false
        assertFalse(sortName.equals(null));

        // different category -> returns false
        SortCommand sortGrade = new SortCommand(CATEGORY_GRADE);
        assertFalse(sortName.equals(sortGrade));
    }

    @Test
    public void execute_sortName_sortedSuccessfully() {
        sortName.setData(model, new CommandHistory(), new UndoRedoStack());
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS);
        assertCommandSuccess(sortName, expectedMessage,
                Arrays.asList(ALICETUTEE, AMYTUTEE, BOBTUTEE, DANIEL));
    }

    @Test
    public void execute_sortEducatonLevel_sortedSuccessfully() {
        SortCommand sortEducationLevel = new SortCommand(CATEGORY_EDUCATION_LEVEL);
        sortEducationLevel.setData(model, new CommandHistory(), new UndoRedoStack());
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS);
        assertCommandSuccess(sortEducationLevel, expectedMessage,
                Arrays.asList(BOBTUTEE, ALICETUTEE, AMYTUTEE, DANIEL));
    }

    @Test
    public void execute_sortGrade_sortedSuccessfully() {
        SortCommand sortGrade = new SortCommand(CATEGORY_GRADE);
        sortGrade.setData(model, new CommandHistory(), new UndoRedoStack());
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS);
        assertCommandSuccess(sortGrade, expectedMessage,
                Arrays.asList(BOBTUTEE, AMYTUTEE, ALICETUTEE, DANIEL));
    }

    @Test
    public void execute_sortSchool_sortedSuccessfully() {
        SortCommand sortSchool = new SortCommand(CATEGORY_SCHOOL);
        sortSchool.setData(model, new CommandHistory(), new UndoRedoStack());
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS);
        assertCommandSuccess(sortSchool, expectedMessage,
                Arrays.asList(AMYTUTEE, ALICETUTEE, BOBTUTEE, DANIEL));
    }

    @Test
    public void execute_sortSubject_sortedSuccessfully() {
        SortCommand sortSubject = new SortCommand(CATEGORY_SUBJECT);
        sortSubject.setData(model, new CommandHistory(), new UndoRedoStack());
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS);
        assertCommandSuccess(sortSubject, expectedMessage,
                Arrays.asList(AMYTUTEE, ALICETUTEE, BOBTUTEE, DANIEL));
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Person>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(SortCommand command, String expectedMessage, List<Person> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
