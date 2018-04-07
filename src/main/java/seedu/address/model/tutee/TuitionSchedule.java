package seedu.address.model.tutee;

import java.util.ArrayList;

import com.calendarfx.model.Entry;

import seedu.address.model.Schedule;
import seedu.address.ui.CalendarPanel;

/**
 * Represents the tuition schedule of the person
 */
public class TuitionSchedule extends Schedule {

    private String personName;
    private ArrayList<TuitionTask> tuitionTaskList = new ArrayList<>();

    /**
     * Creates a schedule for the person
     *
     * @param personName with the schedule to be created
     */
    public TuitionSchedule(String personName) {
        this.personName = personName;
    }

    /**
     * Adds a new tuition task to the schedule
     *
     * @param newTask to be added
     */
    public void addTask(TuitionTask newTask) {
        tuitionTaskList.add(newTask);
        taskList.add(newTask);
        Entry entry = newTask.getEntry();
        CalendarPanel.addEntry(entry);

    }

    /**
     * Deletes a tuition task from schedule
     * Returns the tuition task to be deleted
     *
     * @param deleteTask to remove task from TuitionTaskList and taskList
     */
    public TuitionTask deleteTask(int deleteTask) {
        TuitionTask task = tuitionTaskList.get(deleteTask);
        tuitionTaskList.remove(deleteTask);
        taskList.remove(deleteTask);
        return task;
    }

    /**
     * Returns an arraylist of TuitionTask
     *
     */
    public ArrayList<TuitionTask> getTuitionSchedule() {
        return tuitionTaskList;
    }



}
