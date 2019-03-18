package seedu.address.model.task;

import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class Task {
    private final DeadlineDate deadlineDate;
    private final DeadlineTime deadlineTime;
    private final TaskName taskName;
    private final Set<Tag> tags = new HashSet<>();

    public Task (TaskName taskName, DeadlineTime deadlineTime, DeadlineDate deadlineDate){
        requireAllNonNull(taskName, deadlineTime, deadlineDate);
        this.deadlineDate = deadlineDate;
        this.deadlineTime = deadlineTime;
        this.taskName  = taskName;
    }

    public TaskName getTaskName() {
        return taskName;
    }

    public DeadlineDate getDeadlineDate() { return this.deadlineDate;}

    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public DeadlineTime getDeadlineTime() { return this.deadlineTime;}

    public boolean isSamePerson(Task otherTask) {
        if (otherTask == this) {
            return true;
        }

        return otherTask!= null
                && otherTask.getTaskName().equals(getTaskName())
                && (otherTask.getDeadlineDate().equals(getDeadlineDate()) || otherTask.getDeadlineTime().equals(getDeadlineTime()));
    }
}
