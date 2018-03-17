package seedu.address.model;

import java.time.LocalDateTime;

import java.util.ArrayList;

import seedu.address.model.person.exceptions.TimingClashException;

/**
 * Wraps the data of all existing tasks.
 */
public class Schedule {

    protected static ArrayList<Task> taskList = new ArrayList<>();

    private static final String MESSAGE_TASK_TIMING_CLASHES = "This task clashes with another task";

    /**
     * Returns a list of all existing tasks.
     */
    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    /**
     * Checks for any clashes in the task timing in schedule
     *
     * @throws TimingClashException if there is a timing clash
     */
    public static void checkClashes(LocalDateTime taskDateTime, String duration) throws TimingClashException {
        LocalDateTime taskEndTime = getTaskEndTime(duration, taskDateTime);

        for (Task recordedTask : taskList) {
            if (isTimeClash(taskDateTime, taskEndTime, recordedTask)) {
                throw new TimingClashException(MESSAGE_TASK_TIMING_CLASHES);
            }
        }
    }

    /**
     * Returns date and time when the task ends
     */
    private static LocalDateTime getTaskEndTime(String duration, LocalDateTime startDateTime) {
        int indexOfHourDelimiter = duration.indexOf("h");
        int indexOfMinuteDelimeter = duration.indexOf("m");
        int indexOfFirstMinuteDigit = indexOfHourDelimiter + 1;
        int hoursInDuration = Integer.parseInt(duration.substring(0, indexOfHourDelimiter));
        int minutesInDuration = Integer.parseInt(duration.substring(indexOfFirstMinuteDigit, indexOfMinuteDelimeter));

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
    private static boolean isTimeClash(LocalDateTime taskDateTime, LocalDateTime taskEndTime, Task recordedTask) {
        LocalDateTime startTimeOfTaskInSchedule = recordedTask.getTaskDateTime();
        String duration = recordedTask.getDuration();
        LocalDateTime endTimeOfTaskInSchedule = getTaskEndTime(duration, startTimeOfTaskInSchedule);

        return !(taskEndTime.isBefore(startTimeOfTaskInSchedule) || taskDateTime.isAfter(endTimeOfTaskInSchedule));
    }
}
