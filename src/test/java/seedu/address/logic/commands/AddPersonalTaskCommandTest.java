package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.commons.core.Messages.MESSAGE_TASK_TIMING_CLASHES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_TIME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DURATION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_TIME_BOB;
import static seedu.address.testutil.TaskBuilder.DEFAULT_DESCRIPTION;
import static seedu.address.testutil.TaskBuilder.DEFAULT_DURATION;
import static seedu.address.testutil.TaskUtil.FORMATTER;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.Task;
import seedu.address.model.person.exceptions.TimingClashException;
import seedu.address.model.personal.PersonalTask;
import seedu.address.testutil.ModelStub;
import seedu.address.testutil.TaskBuilder;

//@@author yungyung04
public class AddPersonalTaskCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullTask_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddPersonalTaskCommand(null);
    }

    @Test
    public void execute_taskAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonalTaskAdded modelStub = new ModelStubAcceptingPersonalTaskAdded();
        PersonalTask validTask = new TaskBuilder().buildPersonalTask();

        CommandResult commandResult = getAddPersonalTaskCommandForTask(validTask, modelStub).execute();

        assertEquals(String.format(AddPersonalTaskCommand.MESSAGE_SUCCESS, validTask), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validTask), modelStub.tasksAdded);
    }

    @Test
    public void execute_clashingTask_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingTimingClashException();
        PersonalTask validTask = new TaskBuilder().buildPersonalTask();

        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_TASK_TIMING_CLASHES);

        getAddPersonalTaskCommandForTask(validTask, modelStub).execute();
    }

    @Test
    public void equals() {
        PersonalTask firstPersonalTask = new TaskBuilder().withDateTime(VALID_DATE_TIME_AMY).buildPersonalTask();
        PersonalTask secondPersonalTask = new TaskBuilder().withDateTime(VALID_DATE_TIME_BOB).buildPersonalTask();

        AddPersonalTaskCommand addFirstTask = new AddPersonalTaskCommand(firstPersonalTask);
        AddPersonalTaskCommand addFirstTaskCopy = new AddPersonalTaskCommand(firstPersonalTask);
        AddPersonalTaskCommand addSecondTask = new AddPersonalTaskCommand(secondPersonalTask);

        LocalDateTime tuitionDateTime = LocalDateTime.parse(VALID_DATE_TIME_AMY, FORMATTER);
        AddTuitionTaskCommand addTuitionTask = new AddTuitionTaskCommand(
                INDEX_FIRST_PERSON, tuitionDateTime, DEFAULT_DURATION, DEFAULT_DESCRIPTION);

        // same object -> returns true
        assertTrue(addFirstTask.equals(addFirstTask));

        // same values -> returns true
        assertTrue(addFirstTask.equals(addFirstTaskCopy));

        // different types -> returns false
        assertFalse(addFirstTask.equals(1));

        // null -> returns false
        assertFalse(addFirstTask.equals(null));

        // different task type -> returns false
        assertFalse(addFirstTask.equals(addTuitionTask));

        // different detail -> returns false
        assertFalse(addFirstTask.equals(addSecondTask));
    }

    /**
     * Generates a new AddPersonalTaskCommand with the details of the given personal task.
     */
    private AddPersonalTaskCommand getAddPersonalTaskCommandForTask(PersonalTask task, Model model) {
        AddPersonalTaskCommand command = new AddPersonalTaskCommand(task);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A Model stub that always throw a TimingClashException when trying to add a task.
     */
    private class ModelStubThrowingTimingClashException extends ModelStub {
        @Override
        public void addTask(Task task) throws TimingClashException {
            throw new TimingClashException(MESSAGE_TASK_TIMING_CLASHES);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the task being added.
     */
    private class ModelStubAcceptingPersonalTaskAdded extends ModelStub {
        final ArrayList<Task> tasksAdded = new ArrayList<>();

        @Override
        public void addTask(Task task) throws TimingClashException {
            requireNonNull(task);
            tasksAdded.add(task);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
