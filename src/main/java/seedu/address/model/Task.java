package seedu.address.model;

import java.time.LocalDateTime;

/**
 * Represents a task that person has
 */
public interface Task {
    // Consider changing the location of the three string values below

    public static final String MESSAGE_DESCRIPTION_CONSTRAINTS = "Tasks Should have a non-empty description";

    public static final String MESSAGE_DURATION_CONSTRAINTS = "Duration must be a non-null value";

    public static final String MESSAGE_TIMING_CONSTRAINTS = "Tasks should not have a timing clash";

    LocalDateTime getTaskDateTime();

    String getDescription();

    String getDuration();

}
