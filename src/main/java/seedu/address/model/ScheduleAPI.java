package seedu.address.model;

import java.util.ArrayList;

/**
 * Represents all the tasks that the person has
 */
public interface ScheduleAPI {

    /**
     * Returns a list of all tasks inside the schedule.
     */
    ArrayList<Task>  getTaskList();
}
