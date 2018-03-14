package seedu.address.model.personal;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;

import seedu.address.model.person.exceptions.DurationParseException;
import seedu.address.model.person.exceptions.TimingClashException;

import seedu.address.model.Schedule;

/**
 * Represents the personal schedule of the user
 */
public class PersonalSchedule extends Schedule {

    private static ArrayList<PersonalTask> personalTaskList = new ArrayList<>();

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
