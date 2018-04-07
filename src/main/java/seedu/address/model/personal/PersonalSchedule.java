package seedu.address.model.personal;

import java.util.ArrayList;

import com.calendarfx.model.Entry;

import seedu.address.model.Schedule;
import seedu.address.ui.CalendarPanel;

/**
 * Represents the personal schedule of the user
 */
public class PersonalSchedule extends Schedule {

    private static ArrayList<PersonalTask> personalTaskList = new ArrayList<>();

    public static ArrayList<PersonalTask> getPersonalTaskList() {
        return personalTaskList;
    }

    /**
     * Adds a new personal task.
     *
     * @param newTask to be added
     */
    public void addTask(PersonalTask newTask) {
        personalTaskList.add(newTask);
        taskList.add(newTask);
        Entry entry = newTask.getEntry();
        CalendarPanel.addEntry(entry);
    }
}
