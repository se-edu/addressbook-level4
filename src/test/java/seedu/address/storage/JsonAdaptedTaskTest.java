package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.JsonAdaptedTask.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalTasks.TASKTWO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.DeadlineDate;
import seedu.address.model.task.DeadlineTime;
import seedu.address.model.task.TaskName;
import seedu.address.testutil.Assert;

public class JsonAdaptedTaskTest {
    private static final String INVALID_TASKNAME = "R@chel";
    private static final String INVALID_DEADLINETIME = "26899";
    private static final String INVALID_DEADLINETIME_NOEXIST = "2400";
    private static final String INVALID_DEADLINEDATE = "3434343";
    private static final String INVALID_DEADLINEDATE_NOEXIST = "310219";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_TASKNAME = TASKTWO.getTaskName().toString();
    private static final String VALID_DEADLINETIME = TASKTWO.getDeadlineTime().toString();
    private static final String VALID_DEADLINEDATE = TASKTWO.getDeadlineDate().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = TASKTWO.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validTaskDetails_returnsTask() throws Exception {
        JsonAdaptedTask task = new JsonAdaptedTask(TASKTWO);
        assertEquals(TASKTWO, task.toModelType());
    }

    @Test
    public void toModelType_invalidTaskName_throwsIllegalValueException() {
        JsonAdaptedTask task =
                new JsonAdaptedTask(INVALID_TASKNAME, VALID_DEADLINEDATE, VALID_DEADLINETIME, VALID_TAGS);
        String expectedMessage = TaskName.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_nullTaskName_throwsIllegalValueException() {
        JsonAdaptedTask task = new JsonAdaptedTask(null, VALID_DEADLINEDATE, VALID_DEADLINETIME, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, TaskName.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_invalidDeadlineTime_throwsIllegalValueException() {
        JsonAdaptedTask task =
                new JsonAdaptedTask(VALID_TASKNAME, VALID_DEADLINEDATE, INVALID_DEADLINETIME, VALID_TAGS);
        String expectedMessage = DeadlineTime.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_invalidDeadlineTimeTwo_throwsIllegalValueException() {
        JsonAdaptedTask task =
                new JsonAdaptedTask(VALID_TASKNAME, VALID_DEADLINEDATE, INVALID_DEADLINETIME_NOEXIST, VALID_TAGS);
        String expectedMessage = DeadlineTime.MESSAGE_CONSTRAINTS_INVALID_TIME;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_invalidDeadlineDate_throwsIllegalValueException() {
        JsonAdaptedTask task =
                new JsonAdaptedTask(VALID_TASKNAME, INVALID_DEADLINEDATE, VALID_DEADLINETIME, VALID_TAGS);
        String expectedMessage = DeadlineDate.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_invalidDeadlineDateTwothrowsIllegalValueException() {
        JsonAdaptedTask task =
                new JsonAdaptedTask(VALID_TASKNAME, INVALID_DEADLINEDATE_NOEXIST, VALID_DEADLINETIME, VALID_TAGS);
        String expectedMessage = DeadlineDate.MESSAGE_CONSTRAINTS_INVALID_DATE;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, task::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedTask task =
                new JsonAdaptedTask(VALID_TASKNAME, VALID_DEADLINEDATE, VALID_DEADLINETIME, invalidTags);
        Assert.assertThrows(IllegalValueException.class, task::toModelType);
    }

}
