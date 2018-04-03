package seedu.address.model.tutee;

import java.time.LocalDateTime;

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
    private String tutee;
    private String description;
    private String duration;
    private LocalDateTime taskDateTime;

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
}
