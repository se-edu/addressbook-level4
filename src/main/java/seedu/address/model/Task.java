package seedu.address.model;

import java.time.LocalDateTime;

import com.calendarfx.model.Entry;

/**
 * Represents a task that person has
 */
public interface Task {
    // Consider changing the location of the three string values below

    String MESSAGE_DESCRIPTION_CONSTRAINTS = "Tasks Should have a non-empty description";

    String MESSAGE_DURATION_CONSTRAINTS = "Duration must be a non-null value";

    String MESSAGE_DATETIME_CONSTRAINTS = "Date and time must be a non-null value";

    LocalDateTime getTaskDateTime();

    String getStringTaskDateTime();

    String getDescription();

    String getDuration();

    Entry getEntry();
}
