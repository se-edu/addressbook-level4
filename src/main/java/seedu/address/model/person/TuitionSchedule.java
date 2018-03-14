package seedu.address.model.person;

import java.util.ArrayList;

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
     * @param task to be added
     */
    public void addTask(TuitionTask newTask){
            tuitionTaskList.add(newTask);
            taskList.add(newTask); // potential error
    }



}
