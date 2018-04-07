package seedu.address.testutil;

import static seedu.address.testutil.TaskUtil.FORMATTER;

import java.time.LocalDateTime;

import seedu.address.model.personal.PersonalTask;
import seedu.address.model.tutee.TuitionTask;

/**
 * A utility class to help with building Tuition Task objects.
 */
public class TaskBuilder {
    public static final String DEFAULT_TUTEE_NAME = "Alice Pauline";
    public static final String DEFAULT_DATE = "85355255";
    public static final String DEFAULT_TIME = "alice@gmail.com";
    public static final String DEFAULT_DATE_TIME = DEFAULT_DATE + " " + DEFAULT_TIME;
    public static final String DEFAULT_DURATION = "1h30m";
    public static final String DEFAULT_DESCRIPTION = "Alice's homework";

    private static final String EMPTY_STRING = "";

    protected String name;
    protected LocalDateTime taskDateTime;
    protected String duration;
    protected String description;

    public TaskBuilder() {
        name = DEFAULT_TUTEE_NAME;
        taskDateTime = LocalDateTime.parse(DEFAULT_DATE_TIME, FORMATTER);
        duration = DEFAULT_DURATION;
        description = DEFAULT_DESCRIPTION;
    }

    /**
     * Initializes the TaskBuilder with the data of a given {@code Tuition Task}.
     */
    public TaskBuilder(TuitionTask taskToCopy) {
        name = taskToCopy.getPerson();
        taskDateTime = taskToCopy.getTaskDateTime();
        duration = taskToCopy.getDuration();
        description = taskToCopy.getDescription();
    }

    /**
     * Initializes the TaskBuilder with the data of a given {@code Personal Task}.
     */
    public TaskBuilder(PersonalTask taskToCopy) {
        name = null;
        taskDateTime = taskToCopy.getTaskDateTime();
        duration = taskToCopy.getDuration();
        description = taskToCopy.getDescription();
    }

    /**
     * Sets the {@code name} of the {@code Task} that we are building.
     */
    public TaskBuilder withTuteeName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the {@code taskDateTime} of the {@code Task} that we are building.
     */
    public TaskBuilder withDateTime(String taskDateTime) {
        this.taskDateTime = LocalDateTime.parse(taskDateTime, FORMATTER);
        return this;
    }

    /**
     * Sets the {@code duration} of the {@code Task} that we are building.
     */
    public TaskBuilder withDuration(String duration) {
        this.duration = duration;
        return this;
    }

    /**
     * Sets the {@code description} of the {@code Task} that we are building.
     */
    public TaskBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Sets the {@code description} of the {@code Task} that we are building to be empty.
     */
    public TaskBuilder withoutDescription() {
        this.description = EMPTY_STRING;
        return this;
    }

    public PersonalTask buildPersonalTask() {
        return new PersonalTask(taskDateTime, duration, description);
    }
    public TuitionTask buildTuitionTask() {
        return new TuitionTask(name, taskDateTime, duration, description);
    }
}
