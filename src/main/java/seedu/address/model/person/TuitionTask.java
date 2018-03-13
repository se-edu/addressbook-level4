package seedu.address.model.person;

import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;

import seedu.address.model.Task;

/**
 * Represents a tuition task that the person has
 */
public class TuitionTask implements Task {


    public static final String MESSAGE_TASK_CONSTRAINT =
                    "Task can only be tuition\n"
                    + ", the person involved must already be inside the contact list\n"
                    + ", Date can only contain numbers in the format of dd/mm/yyyy\n"
                    + ", Time must in the format of HH:mm\n"
                    + " and Duration must be in hours.";

    private String person;
    private String description;
    private String duration;
    private LocalDateTime taskDateTime;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/mm/dd HH:mm");


    public TuitionTask(String person, LocalDateTime taskDateTime, String duration, String description) {
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
