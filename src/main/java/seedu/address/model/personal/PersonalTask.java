package seedu.address.model.personal;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import seedu.address.model.Task;

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

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/mm/dd HH:mm");

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
}
