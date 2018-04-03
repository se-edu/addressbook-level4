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

    public static final String MESSAGE_TASK_CONSTRAINT =
                    "Date can only contain numbers in the format of dd/mm/yyyy\n"
                    + ", Time must in the format of HH:mm\n"
                    + " and Duration must be in hours.";

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
        int indexOfHourDelimiter = duration.indexOf("h");
        return Integer.parseInt(duration.substring(0, indexOfHourDelimiter));
    }

    /**
     * Parses minute component out of duration
     *
     * @return number of minutes in the duration
     */
    private int parseMinutes() {
        int startOfMinutesIndex = duration.indexOf("h") + 1;
        int indexOfMinuteDelimiter = duration.indexOf("m");
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
}
