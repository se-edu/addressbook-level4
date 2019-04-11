package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditTaskCommand.EditTaskDescriptor;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.DeadlineDate;
import seedu.address.model.task.DeadlineTime;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskName;

/**
 * A utility class to help with building EditTaskDescriptor objects.
 */

public class EditTaskDescriptorBuilder {

    private EditTaskDescriptor descriptor;

    public EditTaskDescriptorBuilder() {
        descriptor = new EditTaskDescriptor();
    }

    public EditTaskDescriptorBuilder(EditTaskDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    /**
     * Returns an {@code EditTaskDescriptor} with fields containing {@code task}'s details
     */
    public EditTaskDescriptorBuilder(Task task) {
        descriptor = new EditTaskDescriptor();
        descriptor.setTaskName(task.getTaskName());
        descriptor.setDeadlineTime(task.getDeadlineTime());
        descriptor.setDeadlineDate(task.getDeadlineDate());
        descriptor.setTags(task.getTags());
    }

    /**
     * Sets the {@code TaskName} of the {@code EditTaskDescriptor} that we are building
     */
    public EditTaskDescriptorBuilder withTaskName(String taskName) {
        descriptor.setTaskName(new TaskName(taskName));
        return this;
    }

    /**
     * Sets the {@code DeadlineTime} of the {@code EditTaskDescriptor} that we are building
     */
    public EditTaskDescriptorBuilder withDeadlineTime(String deadlineTime) {
        descriptor.setDeadlineTime(new DeadlineTime(deadlineTime));
        return this;
    }

    /**
     * Sets the {@code DeadlineDate} of the {@code EditTaskDescriptor} that we are building
     */
    public EditTaskDescriptorBuilder withDeadlineDate(String deadlineDate) {
        descriptor.setDeadlineDate(new DeadlineDate(deadlineDate));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditTaskDescriptor}
     * that we are building.
     */
    public EditTaskDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditTaskDescriptor build() {
        return descriptor;
    }



}
