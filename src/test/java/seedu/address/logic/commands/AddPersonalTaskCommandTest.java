package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_TIME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DURATION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_DESC_AMY;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.personal.PersonalTask;


public class AddPersonalTaskCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm")
            .withResolverStyle(ResolverStyle.STRICT);
    private LocalDateTime taskDateTime = LocalDateTime.parse(VALID_DATE_TIME_AMY, formatter);
    private PersonalTask task = new PersonalTask(taskDateTime, VALID_DURATION_AMY, VALID_TASK_DESC_AMY);

    @Test
    public void constructor_nullTask_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddPersonalTaskCommand(null);
    }

    @Test
    public void equals() {
        LocalDateTime taskDateTime2 = LocalDateTime.parse("08/08/1988 18:00", formatter);
        PersonalTask task2 = new PersonalTask(taskDateTime2, VALID_DURATION_AMY, VALID_TASK_DESC_AMY);

        AddPersonalTaskCommand addFirstTask = new AddPersonalTaskCommand(task);
        AddPersonalTaskCommand addFirstTaskCopy = new AddPersonalTaskCommand(task);
        AddPersonalTaskCommand addSecondTask = new AddPersonalTaskCommand(task2);

        // same object -> returns true
        assertTrue(addFirstTask.equals(addFirstTask));

        // same values -> returns true
        assertTrue(addFirstTask.equals(addFirstTaskCopy));

        // different types -> returns false
        assertFalse(addFirstTask.equals(1));

        // null -> returns false
        assertFalse(addFirstTask.equals(null));

        // different person -> returns false
        assertFalse(addFirstTask.equals(addSecondTask));
    }
}
