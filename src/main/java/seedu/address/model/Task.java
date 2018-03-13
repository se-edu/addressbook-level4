package seedu.address.model;

import java.time.LocalDateTime;

/**
 * Represents a task that person has
 */
public interface Task {

    LocalDateTime getTaskDateTime();

    String getDescription();

    String getDuration();

}
