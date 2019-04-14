package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.DeadlineDate;
import seedu.address.model.task.DeadlineTime;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskName;



/**
 * Storage class of {@link Task}.
 */
public class JsonAdaptedTask {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "task's %s field is missing!";

    private final String taskName;
    private final String deadlineDate;
    private final String deadlineTime;

    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedTask} with the given task details.
     */
    @JsonCreator
    public JsonAdaptedTask(@JsonProperty("taskName") String taskName, @JsonProperty("deadlineDate") String deadlineDate,
                             @JsonProperty("deadlineTime") String deadlineTime,
                             @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.taskName = taskName;
        this.deadlineDate = deadlineDate;
        this.deadlineTime = deadlineTime;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Task} into this class
     */
    public JsonAdaptedTask(Task source) {
        taskName = source.getTaskName().fullName;
        deadlineTime = source.getDeadlineTime().value;
        deadlineDate = source.getDeadlineDate().value;

        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Convert adapted task object into the model's {@code Task} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Task.
     */
    public Task toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }

        if (taskName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, TaskName
                    .class.getSimpleName()));
        }
        if (!TaskName.isValidName(taskName)) {
            throw new IllegalValueException(TaskName.MESSAGE_CONSTRAINTS);
        }

        final TaskName modelTaskName = new TaskName(taskName);

        if (deadlineDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, DeadlineDate
                    .class.getSimpleName()));
        }
        if (!DeadlineDate.isValidDeadlineDateInput(deadlineDate)) {
            throw new IllegalValueException(DeadlineDate.MESSAGE_CONSTRAINTS);
        }
        if (!DeadlineDate.isValidDeadlineDate(deadlineDate)) {
            throw new IllegalValueException(DeadlineDate.MESSAGE_CONSTRAINTS_INVALID_DATE);
        }

        final DeadlineDate modelDeadlineDate = new DeadlineDate(deadlineDate);

        if (deadlineTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, DeadlineTime
                    .class.getSimpleName()));
        }
        if (!DeadlineTime.isValidDeadlineTimeInput(deadlineTime)) {
            throw new IllegalValueException(DeadlineTime.MESSAGE_CONSTRAINTS);
        }
        if (!DeadlineTime.isValidDeadlineTime(deadlineTime)) {
            throw new IllegalValueException(DeadlineTime.MESSAGE_CONSTRAINTS_INVALID_TIME);
        }

        final DeadlineTime modelDeadlineTime = new DeadlineTime(deadlineTime);
        final Set<Tag> modelTags = new HashSet<>(taskTags);
        return new Task(modelTaskName, modelDeadlineTime, modelDeadlineDate, modelTags);
    }

}


