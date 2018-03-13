package seedu.address.model.person;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;

import seedu.address.model.Schedule;
import seedu.address.model.Task;
import seedu.address.model.person.exceptions.DurationParseException;



/**
 * Represents the tuition schedule of the person
 */
public class TuitionSchedule implements Schedule {

    private static final String MESSAGE_INVALID_DURATION = "The duration format is invalid";
    private static final String MESSAGE_INVALID_DATE_TIME = "The input date and time is invalid";
    private static final int LENGTH_OF_DATE_TIME = 16;
    private static final int INDEX_OF_START_OF_DURATION = LENGTH_OF_DATE_TIME + 1;
    private static final int INVALID_INDEX = -1;
    private static final String NO_DESCRIPTION = "";
    private static final String DURATION_VALIDATION_REGEX = "\\d*\\.??\\d*";

    private String person;
    private String duration;
    private String description;
    private DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("yyyy/mm/dd HH:mm")
            .withResolverStyle(ResolverStyle.STRICT);
    private String stringDateTime;
    private LocalDateTime taskDateTime;
    private ArrayList<Task> tuitionTaskList = new ArrayList<>();

    /**
     * Creates a schedule for the person
     *
     * @param person with the schedule to be created
     */
    public TuitionSchedule(String person) {
        this.person = person;
    }

    /**
     * Adds a new tuition task to the schedule
     *
     * @param task to be added
     */
    public void addTask(String task) {
        try {
            parseTask(task.trim());
            TuitionTask newTask = new TuitionTask(person, taskDateTime, duration, description);
            tuitionTaskList.add(newTask);
        } catch (DateTimeParseException dtpe) {
            System.out.println(MESSAGE_INVALID_DATE_TIME);
        } catch (DurationParseException dpe) {
            System.out.println(dpe.getMessage());
        }
    }

    /**
     * Parses the task into date, time, duration and description
     *
     * @param task to be parsed
     */
    private void parseTask(String task) throws DateTimeParseException, DurationParseException {
        description = parseDescription(task);
        try {
            stringDateTime = parseDateTime(task);
            taskDateTime = LocalDateTime.parse(stringDateTime, formatter);
            duration = parseDuration(task);
        } catch (DateTimeParseException dtpe) {
            throw new DateTimeParseException(MESSAGE_INVALID_DATE_TIME, dtpe.getParsedString(), dtpe.getErrorIndex());
        } catch (DurationParseException dpe) {
            throw new DurationParseException (dpe.getMessage());
        }
    }

    /**
     * Parses task into its date and time.
     *
     * @param task to be parsed
     * @return date and time of the task
     */
    private String parseDateTime(String task) {
        return task.substring(0, LENGTH_OF_DATE_TIME);
    }

    /**
     * Parses task into its duration
     *
     * @param task to be parsed
     * @return duration of the task
     */
    private String parseDuration(String task) throws DurationParseException {
        int indexOfEndOfDuration = task.indexOf(" ", INDEX_OF_START_OF_DURATION);
        String parsedDuration;
        if (indexOfEndOfDuration == INVALID_INDEX) {
            parsedDuration = task.substring(INDEX_OF_START_OF_DURATION);
        } else {
            parsedDuration = task.substring(INDEX_OF_START_OF_DURATION,
                    indexOfEndOfDuration - INDEX_OF_START_OF_DURATION);
        }
        if (!parsedDuration.trim().matches(DURATION_VALIDATION_REGEX)) {
            throw new DurationParseException(MESSAGE_INVALID_DURATION);
        }
        return parsedDuration;
    }

    /**
     * Parses task into description
     *
     * @param task to be parsed
     * @return description of the task
     */
    private String parseDescription(String task) {
        int indexOfEndOfDuration = task.indexOf(" ", INDEX_OF_START_OF_DURATION);
        if (indexOfEndOfDuration == INVALID_INDEX) {
            return NO_DESCRIPTION;
        } else {
            return task.substring(indexOfEndOfDuration).trim();
        }
    }

}
