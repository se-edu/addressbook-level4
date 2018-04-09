package seedu.address.model.tutee;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;

import seedu.address.model.Task;

//@@author ChoChihTun
/**
 * Represents a tuition task that the tutee has
 */
public class TuitionTask implements Task {

    private static final String TUITION_TITLE = "Tuition with %1$s";
    private static final String HOUR_DELIMITER = "h";
    private static final String MINUTE_DELIMITER = "m";
    private static final String NULL_STRING = "";
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm")
            .withResolverStyle(ResolverStyle.STRICT);

    private String tutee;
    private String description;
    private String duration;
    private LocalDateTime taskDateTime;
    private Entry entry;

    /**
     * Creates a tuition task
     *
     * @param tutee tutee involved in the task
     * @param taskDateTime date and time of the task
     * @param duration duration of the task
     * @param description description of the task
     */
    public TuitionTask(String tutee, LocalDateTime taskDateTime, String duration, String description) {
        this.tutee = tutee;
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
        Entry entry = new Entry(getTuitionTitle());
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
        int indexOfFirstMinuteDigit = duration.indexOf(HOUR_DELIMITER) + 1;
        int indexOfMinuteDelimiter = duration.indexOf(MINUTE_DELIMITER);
        return Integer.parseInt(duration.substring(indexOfFirstMinuteDigit, indexOfMinuteDelimiter));
    }

    public Entry getEntry() {
        return entry;
    }

    public LocalDateTime getTaskDateTime() {
        return taskDateTime;
    }

    public String getPerson() {
        return tutee;
    }

    public String getDescription() {
        return description;
    }

    public String getDuration() {
        return duration;
    }

    @Override
    public String getStringTaskDateTime() {
        return taskDateTime.format(formatter);
    }

    //@@author yungyung04
    @Override
    public String toString() {
        if (hasDescription()) {
            return "Tuition task with description " + description + " on "
                    + Integer.toString(taskDateTime.getDayOfMonth()) + " " + taskDateTime.getMonth().name()
                    + " " + Integer.toString(taskDateTime.getYear());
        } else {
            return "Tuition task without description on " + Integer.toString(taskDateTime.getDayOfMonth())
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
                || (other instanceof TuitionTask // instanceof handles nulls
                && tutee.equals(((TuitionTask) other).tutee)
                && taskDateTime.equals(((TuitionTask) other).taskDateTime)
                && duration.equals(((TuitionTask) other).duration)
                && description.equals(((TuitionTask) other).description));
    }

    public String getTuitionTitle() {
        return String.format(TUITION_TITLE, tutee);
    }
}
