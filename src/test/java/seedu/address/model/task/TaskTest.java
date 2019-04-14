package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINEDATE_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINETIME_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_IMPORTANT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PROJECT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASKNAME_TWO;
import static seedu.address.testutil.TypicalTasks.TASKONE;
import static seedu.address.testutil.TypicalTasks.TASKTWO;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.TaskBuilder;

public class TaskTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Task task = new TaskBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        task.getTags().remove(0);
    }

    @Test
    public void isSameTask() {
        // same object -> returns true
        assertTrue(TASKONE.isSameTask(TASKONE));

        // null -> returns false
        assertFalse(TASKONE.isSameTask(null));

        // different deadline time and deadline date but same task name -> returns true
        Task editedTaskOne = new TaskBuilder(TASKONE).withDeadlineTime(VALID_DEADLINETIME_TWO)
                .withDeadlineDate(VALID_DEADLINEDATE_TWO).build();
        assertTrue(TASKONE.isSameTask(editedTaskOne));

        // different task name -> returns false
        editedTaskOne = new TaskBuilder(TASKONE).withTaskName(VALID_TASKNAME_TWO).build();
        assertFalse(TASKONE.isSameTask(editedTaskOne));

        // same task name, same deadline time, different attributes -> returns true
        editedTaskOne = new TaskBuilder(TASKONE).withDeadlineDate(VALID_DEADLINEDATE_TWO)
                .withTags(VALID_TAG_IMPORTANT).build();
        assertTrue(TASKONE.isSameTask(editedTaskOne));

        // same task name, same deadline date, different attributes -> returns true
        editedTaskOne = new TaskBuilder(TASKONE).withDeadlineTime(VALID_DEADLINETIME_TWO)
                .withTags(VALID_TAG_IMPORTANT).build();
        assertTrue(TASKONE.isSameTask(editedTaskOne));

        // same task name, same deadline time, same deadline date, different attributes -> returns true
        editedTaskOne = new TaskBuilder(TASKONE).withTags(VALID_TAG_IMPORTANT).build();
        assertTrue(TASKONE.isSameTask(editedTaskOne));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Task taskOneCopy = new TaskBuilder(TASKONE).build();
        assertTrue(TASKONE.equals(taskOneCopy));

        // same object -> returns true
        assertTrue(TASKONE.equals(TASKONE));

        // null -> returns false
        assertFalse(TASKONE.equals(null));

        // different type -> returns false
        assertFalse(TASKONE.equals(5));

        // different task -> returns false
        assertFalse(TASKONE.equals(TASKTWO));

        // different task name -> returns false
        Task editedTaskOne = new TaskBuilder(TASKONE).withTaskName(VALID_TASKNAME_TWO).build();
        assertFalse(TASKONE.equals(editedTaskOne));

        // different deadline time -> returns false
        editedTaskOne = new TaskBuilder(TASKONE).withDeadlineTime(VALID_DEADLINETIME_TWO).build();
        assertFalse(TASKONE.equals(editedTaskOne));

        // different deadline date -> returns false
        editedTaskOne = new TaskBuilder(TASKONE).withDeadlineDate(VALID_DEADLINEDATE_TWO).build();
        assertFalse(TASKONE.equals(editedTaskOne));

        // different tags -> returns false
        editedTaskOne = new TaskBuilder(TASKONE).withTags(VALID_TAG_PROJECT).build();
        assertFalse(TASKONE.equals(editedTaskOne));
    }
}
