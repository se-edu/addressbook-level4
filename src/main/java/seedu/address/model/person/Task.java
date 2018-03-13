package seedu.address.model.person;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that person has
 */
public class Task {

    public static final String MESSAGE_TASK_CONSTRAINT =
            "Task can only be either personal or tuition\n"
            + ", the person involved must already be inside the contact list\n"
            + ", Date can only contain numbers in the format of dd/mm/yyyy\n"
            + ", Time must in the format of HH:mm\n"
            + " and Duration must be in hours.";

    private String person;
    private String description;
    private String duration;
    private LocalDateTime taskDateTime;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/mm/dd HH:mm");


    public Task(String person, LocalDateTime taskDateTime, String duration, String description) {
        this.person = person;
        this.taskDateTime = taskDateTime;
        this.duration = duration;
        this.description = description;
    }

    public String getTaskDateTime() {
        return taskDateTime.format(formatter);
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
