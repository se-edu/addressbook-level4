package seedu.address.model.personal;

import java.util.ArrayList;

import seedu.address.model.Schedule;

/**
 * Represents the personal schedule of the user
 */
public class PersonalSchedule extends Schedule {

    private static ArrayList<PersonalTask> personalTaskList = new ArrayList<>();

    public static ArrayList<PersonalTask> getPersonalTaskList() {
        return personalTaskList;
    }

    /**
     * Adds a new private task.
     *
     * @param newTask to be added
     */
    public void addTask(PersonalTask newTask) {
        personalTaskList.add(newTask);
        taskList.add(newTask);
    }
}
