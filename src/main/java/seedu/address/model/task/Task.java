package seedu.address.model.task;

import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class Task {
    private final DeadlineDate deadlineDate;
    private final DeadlineTime deadlineTime;
    private final TaskName taskName;
    private final Set<Tag> tags = new HashSet<>();

    public Task (TaskName taskName, DeadlineTime deadlineTime, DeadlineDate deadlineDate
    , Set<Tag> tags){
        requireAllNonNull(taskName, deadlineTime, deadlineDate);
        this.deadlineDate = deadlineDate;
        this.deadlineTime = deadlineTime;
        this.taskName  = taskName;
        this.tags.addAll(tags);
    }

    public TaskName getTaskName() {
        return taskName;
    }

    public DeadlineDate getDeadlineDate() { return deadlineDate;}

    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public DeadlineTime getDeadlineTime() { return deadlineTime;}

    public boolean isSameTask(Task otherTask) {
        if (otherTask == this) {
            return true;
        }

        return otherTask!= null
                && otherTask.getTaskName().equals(getTaskName())
                || (otherTask.getDeadlineDate().equals(getDeadlineDate()) || otherTask.getDeadlineTime().equals(getDeadlineTime()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Task)) {
            return false;
        }

        Task otherTask = (Task) other;
        return otherTask.getTaskName().equals(getTaskName())
                && otherTask.getDeadlineTime().equals(getDeadlineTime())
                && otherTask.getDeadlineDate().equals(getDeadlineDate())
                && otherTask.getTags().equals(getTags());
    }
    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(taskName, deadlineDate, deadlineTime, tags);
    }
    @Override
    public String toString(){
        final StringBuilder builder = new StringBuilder();
        builder.append(getTaskName())
                .append(" DeadlineDate: ")
                .append(getDeadlineDate())
                .append(" DeadlineTime: ")
                .append(getDeadlineTime());
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
