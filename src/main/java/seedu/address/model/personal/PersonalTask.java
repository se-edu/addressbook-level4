package seedu.address.model.personal;

import java.time.LocalDateTime;

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
        return description != "";
    }

    /**
     * this fixes the valid args test, but has conflict with Task card
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonalTask // instanceof handles nulls
                && taskDateTime.getDayOfMonth() == ((PersonalTask) other).taskDateTime.getDayOfMonth()
                && taskDateTime.getHour() == ((PersonalTask) other).taskDateTime.getHour()
                && taskDateTime.getMinute() == ((PersonalTask) other).taskDateTime.getMinute()
                && duration.equals(((PersonalTask) other).duration)
                && description.equals(((PersonalTask) other).description));
    }
    */
}
