package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.tag.Tag;
import seedu.address.model.task.DeadlineDate;
import seedu.address.model.task.DeadlineTime;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskName;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building task objects.
 */

public class TaskBuilder {

    public static final String DEFAULT_TASKNAME = "CS2113T Code";
    public static final String DEFAULT_DEADLINETIME = "2359";
    public static final String DEFAULT_DEADLINEDATE = "251219";

    private TaskName taskName;
    private DeadlineTime deadlineTime;
    private DeadlineDate deadlineDate;
    private Set<Tag> tags;

    public TaskBuilder() {
        taskName = new TaskName(DEFAULT_TASKNAME);
        deadlineDate = new DeadlineDate(DEFAULT_DEADLINEDATE);
        deadlineTime = new DeadlineTime(DEFAULT_DEADLINETIME);
        tags = new HashSet<>();
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(Task taskToCopy) {
        taskName = taskToCopy.getTaskName();
        deadlineTime = taskToCopy.getDeadlineTime();
        deadlineDate = taskToCopy.getDeadlineDate();
        tags = new HashSet<>(taskToCopy.getTags());
    }

    /**
     * Sets the {@code TaskName} of the {@code task} that we are building
     */
    public TaskBuilder withTaskName(String taskName) {
        this.taskName = new TaskName(taskName);
        return this;
    }

    /**
     * Sets the {@code DeadlineTime} of the {@code task} that we are building
     */
    public TaskBuilder withDeadlineTime(String deadlineTime) {
        this.deadlineTime = new DeadlineTime(deadlineTime);
        return this;
    }

    /**
     * Sets the {@code DeadlineDate} of the {@code task} that we are building
     */
    public TaskBuilder withDeadlineDate(String deadlineDate) {
        this.deadlineDate = new DeadlineDate(deadlineDate);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code task}
     * that we are building.
     */
    public TaskBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public Task build() {
        return new Task(taskName, deadlineTime, deadlineDate, tags);
    }
}
