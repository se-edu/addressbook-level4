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

    private String person;
    private String description;
    private String duration;
    private LocalDateTime taskDateTime;

    /**
     * Creates a tuition task
     *
     * @param person person involves in the task
     * @param taskDateTime date and time of the task
     * @param duration duration of the task
     * @param description description of the task
     */
    public TuitionTask(String person, LocalDateTime taskDateTime, String duration, String description) {
        this.person = person;
        this.taskDateTime = taskDateTime;
        this.duration = duration;
        this.description = description;
    }

    public LocalDateTime getTaskDateTime() {
        return taskDateTime;
    }

    public String getPerson() {
        return person;
    }

    public String getDescription() {
        return description;
    }

    public String getDuration() {
        return duration;
    }
}
