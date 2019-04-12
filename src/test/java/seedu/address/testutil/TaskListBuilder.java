package seedu.address.testutil;

import seedu.address.model.TaskList;
import seedu.address.model.task.Task;

/**
 * A utility class to help with building Task List objects.
 * Example usage: <br>
 *     {@code TaskList= new TaskListBuilder().withTaskName("CS2113T", "CODE").build();}
 */
public class TaskListBuilder {

    private TaskList taskList;

    public TaskListBuilder() {
        taskList = new TaskList();
    }

    public TaskListBuilder(TaskList taskList) {
        this.taskList = taskList;
    }

    /**
     * Adds a new {@code Task} to the {@code TaskList} that we are building
     */
    public TaskListBuilder withTask(Task task) {
        taskList.addTask(task);
        return this;
    }

    public TaskList build() {
        return taskList;
    }

}
