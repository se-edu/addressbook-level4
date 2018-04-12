package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_TASK_TIMING_CLASHES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_TIME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_TIME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DURATION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DURATION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TaskUtil.FORMATTER;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.typicaladdressbook.TypicalAddressBookCompiler.getTypicalAddressBook2;
import static seedu.address.testutil.typicaladdressbook.TypicalTasks.TASK_AMY;

import java.time.LocalDateTime;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TaskBuilder;

//@@author yungyung04
public class AddTuitionTaskCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook2(), new UserPrefs());
    private LocalDateTime taskDateTimeAmy = LocalDateTime.parse(VALID_DATE_TIME_AMY, FORMATTER);

    @Test
    public void constructor_nullTaskDetail_throwsNullPointerException() {
        //one of the other 3 task details is null.
        thrown.expect(NullPointerException.class);
        new AddTuitionTaskCommand(INDEX_FIRST_PERSON, taskDateTimeAmy, VALID_DURATION_AMY, null);
    }

    @Test
    public void execute_taskAcceptedByModel_addSuccessful() throws Exception {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        AddTuitionTaskCommand addTuitionAmy = getAddTuitionTaskCommandForTask(
                INDEX_THIRD_PERSON, taskDateTimeAmy, VALID_DURATION_AMY, VALID_TASK_DESC_AMY);

        String expectedMessage = String.format(AddTuitionTaskCommand.MESSAGE_SUCCESS, TASK_AMY);
        expectedModel.addTask(TASK_AMY);

        assertCommandSuccess(addTuitionAmy, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        AddTuitionTaskCommand command = getAddTuitionTaskCommandForTask(outOfBoundIndex, taskDateTimeAmy,
                VALID_DURATION_AMY, VALID_TASK_DESC_AMY);
        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_clashingTask_throwsCommandException() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_TASK_TIMING_CLASHES);

        getAddTuitionTaskCommandForTask(INDEX_THIRD_PERSON, taskDateTimeAmy, VALID_DURATION_AMY,
                VALID_TASK_DESC_AMY).execute();

        getAddTuitionTaskCommandForTask(INDEX_FIRST_PERSON, taskDateTimeAmy, VALID_DURATION_AMY,
                VALID_TASK_DESC_AMY).execute();
    }


    @Test
    public void equals() {
        LocalDateTime taskDateTimeBob = LocalDateTime.parse(VALID_DATE_TIME_BOB, FORMATTER);

        AddTuitionTaskCommand addTuitionAmy = getAddTuitionTaskCommandForTask(
                INDEX_THIRD_PERSON, taskDateTimeAmy, VALID_DURATION_AMY, VALID_TASK_DESC_AMY);
        AddTuitionTaskCommand addTuitionAmyCopy = getAddTuitionTaskCommandForTask(
                INDEX_THIRD_PERSON, taskDateTimeAmy, VALID_DURATION_AMY, VALID_TASK_DESC_AMY);
        AddTuitionTaskCommand addTuitionBob = new AddTuitionTaskCommand(
                INDEX_SECOND_PERSON, taskDateTimeBob, VALID_DURATION_BOB, VALID_TASK_DESC_BOB);

        // an AddPersonalTaskCommand object with same task details as addTuitionAmy
        AddPersonalTaskCommand addPersonalTask =
                new AddPersonalTaskCommand(new TaskBuilder(TASK_AMY).buildPersonalTask());

        // same value -> returns true
        assertTrue(addTuitionAmy.equals(addTuitionAmyCopy));

        // same object -> returns true
        assertTrue(addTuitionAmy.equals(addTuitionAmy));

        // different types -> returns false
        assertFalse(addTuitionAmy.equals(1));

        // null -> returns false
        assertFalse(addTuitionAmy.equals(null));

        // different task type -> returns false
        assertFalse(addTuitionAmy.equals(addPersonalTask));

        // different detail -> returns false
        assertFalse(addTuitionAmy.equals(addTuitionBob));
    }

    /**
     * Generates a new AddTuitionTaskCommand with the details of the given tuition task.
     */
    private AddTuitionTaskCommand getAddTuitionTaskCommandForTask(Index tuteeIndex, LocalDateTime taskDateTime,
                                                                  String duration, String description) {
        AddTuitionTaskCommand command = new AddTuitionTaskCommand(tuteeIndex, taskDateTime, duration, description);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
