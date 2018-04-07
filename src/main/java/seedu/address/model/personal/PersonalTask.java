package seedu.address.model.personal;

import java.time.LocalDateTime;

import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;

import seedu.address.model.Task;

//@@author ChoChihTun
/**
 * Represents the personal task that the user has
 */
public class PersonalTask implements Task {

    private static final String HOUR_DELIMITER = "h";
    private static final String MINUTE_DELIMITER = "m";
    private static final String NULL_STRING = "";

    private String description;
    private String duration;
    private LocalDateTime taskDateTime;
    private Entry entry;

    /**
     * Creates a personal task
     *
     * @param taskDateTime date and time of the task
     * @param duration duration of the task
     * @param description description of the task
     */
    public PersonalTask(LocalDateTime taskDateTime, String duration, String description) {
        this.taskDateTime = taskDateTime;
        this.duration = duration;
        this.description = description;
        this.entry = createCalendarEntry();
    }

    /**
     * Creates an entry to be entered into the calendar
     *
     * @return Calendar entry
     */
    private Entry createCalendarEntry() {
        LocalDateTime endDateTime = getTaskEndTime();
        Interval interval = new Interval(taskDateTime, endDateTime);
        Entry entry = new Entry(description);
        entry.setInterval(interval);
        return entry;
    }

    /**
     * Returns the end time of the task
     */
    private LocalDateTime getTaskEndTime() {
        int hoursInDuration = parseHours();
        int minutesInDuration = parseMinutes();
        LocalDateTime endDateTime = taskDateTime.plusHours(hoursInDuration).plusMinutes(minutesInDuration);
        return endDateTime;
    }

    /**
     * Parses hour component out of duration
     *
     * @return number of hours in the duration
     */
    private int parseHours() {
        int indexOfHourDelimiter = duration.indexOf(HOUR_DELIMITER);
        return Integer.parseInt(duration.substring(0, indexOfHourDelimiter));
    }

    /**
     * Parses minute component out of duration
     *
     * @return number of minutes in the duration
     */
    private int parseMinutes() {
        int startOfMinutesIndex = duration.indexOf(HOUR_DELIMITER) + 1;
        int indexOfMinuteDelimiter = duration.indexOf(MINUTE_DELIMITER);
        return Integer.parseInt(duration.substring(startOfMinutesIndex, indexOfMinuteDelimiter));
    }

    public Entry getEntry() {
        return entry;
    }

    public LocalDateTime getTaskDateTime() {
        return taskDateTime;
    }

    public String getDescription() {
        return description;
    }

    public String getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        if (hasDescription()) {
            return "Personal task with description " + description + " on "
                    + Integer.toString(taskDateTime.getDayOfMonth()) + " "
                    + taskDateTime.getMonth().name() + " " + Integer.toString(taskDateTime.getYear());
        } else {
            return "Personal task without description on " + Integer.toString(taskDateTime.getDayOfMonth())
                    + " " + taskDateTime.getMonth().name() + " " + Integer.toString(taskDateTime.getYear());
        }
    }

    /**
     * Returns true if the tuition task contains a non-empty description.
     */
    private boolean hasDescription() {
        return !description.equals(NULL_STRING);
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonalTask // instanceof handles nulls
                && taskDateTime.equals(((PersonalTask) other).taskDateTime)
                && duration.equals(((PersonalTask) other).duration)
                && description.equals(((PersonalTask) other).description));
    }

    /* in case needed fo equals
    && taskDateTime.getDayOfMonth() == ((PersonalTask) other).taskDateTime.getDayOfMonth()
                && taskDateTime.getHour() == ((PersonalTask) other).taskDateTime.getHour()
                && taskDateTime.getMinute() == ((PersonalTask) other).taskDateTime.getMinute() */
}
