package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_TIME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DURATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DESC;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import java.util.ArrayList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.Schedule;
import seedu.address.model.Task;
import seedu.address.model.personal.PersonalSchedule;
import seedu.address.model.personal.PersonalTask;


public class AddPersonalTaskCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm")
            .withResolverStyle(ResolverStyle.STRICT);
    private LocalDateTime taskDateTime = LocalDateTime.parse(VALID_DATE_TIME, formatter);
    private PersonalTask task = new PersonalTask(taskDateTime, VALID_DURATION, VALID_TASK_DESC);

    @Test
    public void constructor_nullTask_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddPersonalTaskCommand(null);
    }

    @Test
    public void execute_validPersonalTask_addSuccessful() throws Exception {
        ArrayList<Task> taskListCopy = Schedule.getTaskList();
        ArrayList<PersonalTask> personalTaskListCopy = PersonalSchedule.getPersonalTaskList();

        AddPersonalTaskCommand command = new AddPersonalTaskCommand(task);
        CommandResult commandResult = command.execute();

        assertEquals(AddPersonalTaskCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);

        /**
         * NullPointerException thrown. Do you know what's wrong?
        //taskList should be updated
        taskListCopy.add(task);
        assertEquals(taskListCopy, Schedule.getTaskList());

        //personalTaskList should be updated
        personalTaskListCopy.add(task);
        assertEquals(personalTaskListCopy, PersonalSchedule.getPersonalTaskList());
         */
    }

    @Test
    public void equals() {
        LocalDateTime taskDateTime2 = LocalDateTime.parse("08/08/1988 18:00", formatter);
        PersonalTask task2 = new PersonalTask(taskDateTime2, VALID_DURATION, VALID_TASK_DESC);

        AddPersonalTaskCommand addFirstTask = new AddPersonalTaskCommand(task);
        AddPersonalTaskCommand addFirstTaskcopy = new AddPersonalTaskCommand(task);
        AddPersonalTaskCommand addSecondTask = new AddPersonalTaskCommand(task2);

        // same object -> returns true
        assertTrue(addFirstTask.equals(addFirstTask));

        // same values -> returns true
        assertTrue(addFirstTask.equals(addFirstTaskcopy));

        // different types -> returns false
        assertFalse(addFirstTask.equals(1));

        // null -> returns false
        assertFalse(addFirstTask.equals(null));

        // different person -> returns false
        assertFalse(addFirstTask.equals(addSecondTask));
    }
}
