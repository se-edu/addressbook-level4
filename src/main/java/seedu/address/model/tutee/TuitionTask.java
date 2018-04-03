package seedu.address.model.tutee;

import java.time.LocalDateTime;

import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;

import seedu.address.model.Task;

//@@author ChoChihTun
/**
 * Represents a tuition task that the person has
 */
public class TuitionTask implements Task {

    public static final String MESSAGE_TASK_CONSTRAINT =
            "Task can only be tuition\n"
                    + ", the person involved must already be inside the contact list\n"
                    + ", Date can only contain numbers in the format of dd/mm/yyyy\n"
                    + ", Time must in the format of HH:mm\n"
                    + " and Duration must be the format of 01h30m";

    //private Tutee tutee;
    private static final String TUITION_TITLE = "Tuition with %1$s";  
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
        return description != "";
    }

    /**
     * fixes the test but has conflict with Task card
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TuitionTask // instanceof handles nulls
                && tutee.equals(((TuitionTask) other).tutee)
                && taskDateTime.equals(((TuitionTask) other).taskDateTime)
                && duration.equals(((TuitionTask) other).duration)
                && description.equals(((TuitionTask) other).description));
    }
    */
  
    public String getTuitionTitle() {
        return String.format(TUITION_TITLE, person);
    }
}
