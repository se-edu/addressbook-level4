package seedu.address.model.person;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import seedu.address.model.Schedule;
import seedu.address.model.person.exceptions.DurationParseException;
import seedu.address.model.person.exceptions.TimingClashException;


/**
 * Represents the tuition schedule of the person
 */
public class TuitionSchedule implements Schedule {

    private static final String MESSAGE_TASK_TIMING_CLASHES = "This task clashes with another task";

    private String person;
    private ArrayList<TuitionTask> tuitionTaskList = new ArrayList<>();

    /**
     * Creates a schedule for the person
     *
     * @param person with the schedule to be created
     */
    public TuitionSchedule(String person) {
        this.person = person;
    }

    /**
     * Adds a new tuition task to the schedule
     *
     * @param task to be added
     */
    public void addTask(TuitionTask newTask){
            tuitionTaskList.add(newTask);
    }

    /**
     * Checks for any clashes in the task timing in schedule
     *
     * @throws TimingClashException if there is a timing clash
     */
    public static void checkClashes(LocalDateTime taskDateTime, String duration) throws TimingClashException {
        LocalDateTime taskEndTime = getTaskEndTime(duration, taskDateTime);

        for (TuitionTask recordedTask : tuitionTaskList) {
            if (isTimeClash(taskDateTime, taskEndTime, recordedTask)) {
                throw new TimingClashException(MESSAGE_TASK_TIMING_CLASHES);
            }
        }
    }

    /**
     * Returns date and time when the task ends
     */
    private LocalDateTime getTaskEndTime(String duration, LocalDateTime startDateTime) {
        int indexOfHourDelimiter = duration.indexOf("h");
        int indexOfFirstMinuteDigit = indexOfHourDelimiter + 1;
        int hoursInDuration = Integer.parseInt(duration.substring(0, indexOfHourDelimiter));
        int minutesInDuration = Integer.parseInt(duration.substring(indexOfFirstMinuteDigit));

        LocalDateTime taskEndTime;
        taskEndTime = startDateTime.plusHours(hoursInDuration).plusMinutes(minutesInDuration);
        return taskEndTime;
    }

    /**
     * Checks if the new task date and time clashes with a task in the schedule
     *
     * @param taskEndTime  End time of the new task
     * @param recordedTask Task that is already in the schedule
     * @return true if no clash
     * false if clashes
     */
    private boolean isTimeClash(LocalDateTime taskDateTime, LocalDateTime taskEndTime, TuitionTask recordedTask) {
        LocalDateTime startTimeOfTaskInSchedule = recordedTask.getTaskDateTime();
        String duration = recordedTask.getDuration();
        LocalDateTime endTimeOfTaskInSchedule = getTaskEndTime(duration, startTimeOfTaskInSchedule);

        if (taskEndTime.isBefore(startTimeOfTaskInSchedule) || taskDateTime.isAfter(endTimeOfTaskInSchedule)) {
            return false;
        } else {
            return true;
        }
    }

}
