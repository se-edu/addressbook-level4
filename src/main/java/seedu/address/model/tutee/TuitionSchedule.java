package seedu.address.model.tutee;

import java.util.ArrayList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Schedule;


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
    }

    /**
     * Deletes a tuition task from schedule
     *
     * @param deleteTask to remove task from TuitionTaskList and taskList
     */
    public void deleteTask(Index deleteTask) {
        tuitionTaskList.remove(deleteTask);
        taskList.remove(deleteTask);
    }

    public ArrayList<TuitionTask> getTuitionSchedule() {
        return tuitionTaskList;
    }



}
