package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.commands.EditTaskCommand.EditTaskDescriptor;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;

/**
 * Utility class for Task
 */
public class TaskUtil {

    /**
     * Returns an add task command for adding the {@code task}
     */
    public static String getAddTaskCommand(Task task) {
        return AddTaskCommand.COMMAND_WORD + " " + getTaskDetails(task);
    }

    /**
     * Returns the part of command string for the given {@code task}'s details.
     */
    public static String getTaskDetails(Task task) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + task.getTaskName().fullName + " ");
        sb.append(PREFIX_DEADLINE_TIME + task.getDeadlineTime().value + " ");
        sb.append(PREFIX_DEADLINE_DATE + task.getDeadlineDate().value + " ");
        task.getTags().stream().forEach(s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditTaskDescriptor}'s details.
     */
    public static String getEditTaskDescriptorDetails(EditTaskDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getTaskName().ifPresent(taskName -> sb.append(PREFIX_NAME)
                .append(taskName.fullName).append(" "));
        descriptor.getDeadlineTime().ifPresent(deadlineTime -> sb.append(PREFIX_DEADLINE_TIME)
                .append(deadlineTime.value).append(" "));
        descriptor.getDeadlineDate().ifPresent(deadlineDate -> sb.append(PREFIX_DEADLINE_DATE)
                .append(deadlineDate.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
