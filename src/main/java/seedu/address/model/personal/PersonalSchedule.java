package seedu.address.model.personal;

import java.util.ArrayList;

import seedu.address.model.Schedule;

/**
 * Represents the personal schedule of the user
 */
public class PersonalSchedule extends Schedule {

    public static ArrayList<PersonalTask> personalTaskList = new ArrayList<>();

    /**
     * Creates a schedule for the person*
     */
    public PersonalSchedule() {
    }

    /**
     * Adds a new private task.
     *
     * @param task to be added
     */
    public void addTask(PersonalTask newTask) {
            personalTaskList.add(newTask);
            taskList.add(newTask);
    }

}
