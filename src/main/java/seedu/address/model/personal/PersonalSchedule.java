package seedu.address.model.personal;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;

import seedu.address.model.Schedule;
import seedu.address.model.person.exceptions.DurationParseException;
import seedu.address.model.person.exceptions.TimingClashException;

/**
 * Represents the personal schedule of the user
 */
public class PersonalSchedule implements Schedule {

    private static final String MESSAGE_INVALID_DURATION = "The duration format is invalid";
    private static final String MESSAGE_INVALID_DATE_TIME = "The input date and time is invalid";
    private static final String MESSAGE_TASK_TIMING_CLASHES = "This task clashes with another task";
    private static final int LENGTH_OF_DATE_TIME = 16;
    private static final int INDEX_OF_START_OF_DURATION = LENGTH_OF_DATE_TIME + 1;
    private static final int INVALID_INDEX = -1;
    private static final String NO_DESCRIPTION = "";
    private static final String DURATION_VALIDATION_REGEX = "([0-9]|1[0-9]|2[0-3])\\b(h)\\b([0-5][0-9])\\b(h)\\b";

    private String duration;
    private String description;
    private DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("yyyy/mm/dd HH:mm")
            .withResolverStyle(ResolverStyle.STRICT);
    private String stringDateTime;
    private LocalDateTime taskDateTime;
    private ArrayList<PersonalTask> personalTaskList = new ArrayList<>();

    /**
     * Creates a schedule for the person*
     */
    public PersonalSchedule() {
    }

    /**
     * Adds a new private task to the schedule
     *
     * @param task to be added
     */
    public void addTask(String task) {
        try {
            parseTask(task.trim());
            PersonalTask newTask = new PersonalTask(taskDateTime, duration, description);
            personalTaskList.add(newTask);
        } catch (DateTimeParseException dtpe) {
            System.out.println(MESSAGE_INVALID_DATE_TIME);
        } catch (DurationParseException dpe) {
            System.out.println(dpe.getMessage());
        } catch (TimingClashException tce) {
            System.out.println(MESSAGE_TASK_TIMING_CLASHES);
        }
    }

    /**
     * Parses the task into date, time, duration and description
     *
     * @param task to be parsed
     * @throws DateTimeParseException if date and time given is not valid
     * @throws DurationParseException if duration format is invalid
     * @throws TimingClashException if there is a timing clash
     */
    private void parseTask(String task) throws DateTimeParseException, DurationParseException, TimingClashException {
        description = parseDescription(task);
        try {
            stringDateTime = parseDateTime(task);
            taskDateTime = LocalDateTime.parse(stringDateTime, formatter);
            duration = parseDuration(task);
            checkClashes();
        } catch (DateTimeParseException dtpe) {
            throw new DateTimeParseException(MESSAGE_INVALID_DATE_TIME, dtpe.getParsedString(), dtpe.getErrorIndex());
        } catch (DurationParseException dpe) {
            throw new DurationParseException (dpe.getMessage());
        } catch (TimingClashException tce) {
            throw new TimingClashException(tce.getMessage());
        }
    }

    /**
     * Parses task into its date and time.
     *
     * @param task to be parsed
     * @return date and time of the task
     */
    private String parseDateTime(String task) {
        return task.substring(0, LENGTH_OF_DATE_TIME);
    }

    /**
     * Parses task into its duration
     *
     * @param task to be parsed
     * @return duration of the task
     * @throws DurationParseException if duration format is invalid
     */
    private String parseDuration(String task) throws DurationParseException {
        int indexOfEndOfDuration = task.indexOf(" ", INDEX_OF_START_OF_DURATION);
        String parsedDuration;
        if (indexOfEndOfDuration == INVALID_INDEX) {
            parsedDuration = task.substring(INDEX_OF_START_OF_DURATION);
        } else {
            parsedDuration = task.substring(INDEX_OF_START_OF_DURATION,
                    indexOfEndOfDuration - INDEX_OF_START_OF_DURATION);
        }
        if (!parsedDuration.matches(DURATION_VALIDATION_REGEX)) {
            throw new DurationParseException(MESSAGE_INVALID_DURATION);
        }
        return parsedDuration;
    }

    /**
     * Parses task into description
     *
     * @param task to be parsed
     * @return description of the task
     */
    private String parseDescription(String task) {
        int indexOfEndOfDuration = task.indexOf(" ", INDEX_OF_START_OF_DURATION);
        if (indexOfEndOfDuration == INVALID_INDEX) {
            return NO_DESCRIPTION;
        } else {
            return task.substring(indexOfEndOfDuration).trim();
        }
    }

    /**
     * Checks for any clashes in the task timing in schedule
     *
     * @throws TimingClashException if there is a timing clash
     */
    private void checkClashes() throws TimingClashException {
        LocalDateTime taskEndTime = getTaskEndTime(duration, taskDateTime);

        for (PersonalTask recordedTask : personalTaskList) {
            if (isTimeClash(taskEndTime, recordedTask)) {
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
     * @param taskEndTime End time of the new task
     * @param recordedTask Task that is already in the schedule
     * @return true if no clash
     *         false if clashes
     */
    private boolean isTimeClash(LocalDateTime taskEndTime, PersonalTask recordedTask) {
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
