package seedu.address.model.tutee;

import java.util.ArrayList;

import com.calendarfx.model.Entry;

import seedu.address.model.Schedule;
import seedu.address.model.Task;
import seedu.address.ui.CalendarPanel;

//@@author ChoChihTun
/**
 * Represents the tuition schedule of the tutor
 */
public class TuitionSchedule extends Schedule {

    private static ArrayList<TuitionTask> tuitionTaskList = new ArrayList<>();

    /**
     * Adds a new tuition task to the schedule
     *
     * @param newTask to be added
     */
    public static void addTask(TuitionTask newTask) {
        tuitionTaskList.add(newTask);
        taskList.add(newTask);
        Entry entry = newTask.getEntry();
        CalendarPanel.addEntry(entry);

    }

    /**
     * Deletes a tuition task from schedule
     *
     * @param task to remove task from TuitionTaskList and taskList
     */
    public static void deleteTask(Task task) {
        tuitionTaskList.remove(task);
        taskList.remove(task);
        CalendarPanel.deleteTask(task.getEntry());
    }

    /**
     * Returns an arraylist of TuitionTask
     *
     */
    public ArrayList<TuitionTask> getTuitionSchedule() {
        return tuitionTaskList;
    }



}
