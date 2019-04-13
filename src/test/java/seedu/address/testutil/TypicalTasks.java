package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINEDATE_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINEDATE_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINETIME_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINETIME_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_IMPORTANT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PROJECT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASKNAME_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASKNAME_TWO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.TaskList;
import seedu.address.model.task.Task;



/**
 * A utility class containing a list of {@code task} objects to be used in tests.
 */
public class TypicalTasks {

    public static final Task TASKEIGHT = new TaskBuilder().withTaskName("CS2113 Code")
            .withDeadlineTime("2359").withDeadlineDate("201219").withTags("Important")
            .build();
    public static final Task TASKNINE = new TaskBuilder().withTaskName("CS2101 Guide")
            .withDeadlineTime("2359").withDeadlineDate("131219").withTags("TeamProject")
            .build();
    public static final Task TASKTHREE = new TaskBuilder().withTaskName("CS3235 Assignment")
            .withDeadlineTime("2359").withDeadlineDate("200220").withTags("HARD")
            .build();
    public static final Task TASKFOUR = new TaskBuilder().withTaskName("Book tickets")
            .withDeadlineTime("2359").withDeadlineDate("271219").withTags("ASAP")
            .build();
    public static final Task TASKFIVE = new TaskBuilder().withTaskName("Website UI")
            .withDeadlineTime("2359").withDeadlineDate("281219").withTags("HARD")
            .build();

    // Manually added
    public static final Task TASKSIX = new TaskBuilder().withTaskName("Buy Medicine")
            .withDeadlineTime("2359").withDeadlineDate("180419").build();
    public static final Task TASKSEVEN = new TaskBuilder().withTaskName("Mark homework")
            .withDeadlineTime("2359").withDeadlineDate("200719").build();

    // Manually added - task's details found in {@code CommandTestUtil)
    public static final Task TASKONE = new TaskBuilder().withTaskName(VALID_TASKNAME_ONE)
            .withDeadlineTime(VALID_DEADLINETIME_ONE).withDeadlineDate(VALID_DEADLINEDATE_ONE)
            .withTags(VALID_TAG_IMPORTANT).build();

    public static final Task TASKTWO = new TaskBuilder().withTaskName(VALID_TASKNAME_TWO)
            .withDeadlineTime(VALID_DEADLINETIME_TWO).withDeadlineDate(VALID_DEADLINEDATE_TWO)
            .withTags(VALID_TAG_PROJECT).build();

    private TypicalTasks() {} //prevents instantiation

    /**
     *
     * Returns an {@code TaskList} with all the typical tasks
     */
    public static TaskList getTypicalTaskList() {
        TaskList tl = new TaskList();
        for (Task task : getTypicalTask()) {
            tl.addTask(task);
        }
        return tl;
    }

    public static List<Task> getTypicalTask() {
        return new ArrayList<>(Arrays.asList(TASKTHREE, TASKFOUR, TASKFIVE, TASKSIX, TASKSEVEN));
    }
}

