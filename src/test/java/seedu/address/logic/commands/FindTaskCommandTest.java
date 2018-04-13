package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.commons.core.Messages.MESSAGE_TASKS_LISTED_OVERVIEW;
import static seedu.address.model.task.TaskSortUtil.CATEGORY_MONTH;
import static seedu.address.testutil.typicaladdressbook.TypicalAddressBookCompiler.getTypicalAddressBook2;
import static seedu.address.testutil.typicaladdressbook.TypicalTasks.TASK_ALICE;
import static seedu.address.testutil.typicaladdressbook.TypicalTasks.TASK_BENSON;
import static seedu.address.testutil.typicaladdressbook.TypicalTasks.TASK_CARL;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.Task;
import seedu.address.model.UserPrefs;

//@@author ChoChihTun
/**
 * Contains integration tests (interaction with the Model) for {@code FindTaskCommand}.
 */
public class FindTaskCommandTest {
    private static final String OCTOBER = "10";
    private static final String NOVEMBER = "11";
    private static final String DECEMBER = "12";

    private final String[] monthBetweenKeywords = {OCTOBER, NOVEMBER, DECEMBER};
    private final String[] monthNamelyKeyword = {OCTOBER};

    private final FindTaskCommand findMonthBetweenKeywordsCommand =
            new FindTaskCommand(CATEGORY_MONTH, monthBetweenKeywords);
    private final FindTaskCommand findMonthNamelyKeywordCommand =
            new FindTaskCommand(CATEGORY_MONTH, monthNamelyKeyword);

    private Model model = new ModelManager(getTypicalAddressBook2(), new UserPrefs());

    @Test
    public void equals_validArgs_returnsTrue() {
        // same object
        assertTrue(findMonthBetweenKeywordsCommand.equals(findMonthBetweenKeywordsCommand));

        // objects with same value
        FindTaskCommand findMonthBetweenKeywordsCommandCopy = new FindTaskCommand(CATEGORY_MONTH, monthBetweenKeywords);
        assertTrue(findMonthBetweenKeywordsCommand.equals(findMonthBetweenKeywordsCommandCopy));
    }

    @Test
    public void equals_invalidArgs_returnsFalse() {
        // null
        assertFalse(findMonthBetweenKeywordsCommand.equals(null));

        // wrong parameter data type
        assertFalse(findMonthBetweenKeywordsCommand.equals(1));

        // different commands
        assertFalse(findMonthBetweenKeywordsCommand.equals(findMonthNamelyKeywordCommand));
    }

    @Test
    public void execute_findMonth_success() {
        // between months
        findMonthBetweenKeywordsCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        String expectedMessage = String.format(FindTaskCommand.MESSAGE_SUCCESS + "\n"
                + MESSAGE_TASKS_LISTED_OVERVIEW, 3);
        assertCommandSuccess(findMonthBetweenKeywordsCommand, expectedMessage,
                Arrays.asList(TASK_ALICE, TASK_BENSON, TASK_CARL));

        // namely month
        findMonthNamelyKeywordCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        expectedMessage = String.format(FindTaskCommand.MESSAGE_SUCCESS + "\n" + MESSAGE_TASKS_LISTED_OVERVIEW, 2);
        assertCommandSuccess(findMonthNamelyKeywordCommand, expectedMessage, Arrays.asList(TASK_ALICE, TASK_BENSON));
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Task>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindTaskCommand command, String expectedMessage, List<Task> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredTaskList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
