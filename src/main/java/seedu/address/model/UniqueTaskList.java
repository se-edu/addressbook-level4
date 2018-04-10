package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_TASK_TIMING_CLASHES;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.TimingClashException;
import seedu.address.model.task.exceptions.TaskNotFoundException;

/**
 * A list of tasks that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 */
public class UniqueTaskList implements Iterable<Task> {
    private static final String HOUR_DELIMITER = "h";
    private static final String MINUTE_DELIMITER = "m";

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();


    /**
     * Constructs empty TaskList.
     */
    public UniqueTaskList() {}

    //@@author ChoChihTun
    /**
     * Adds a task to the list.
     *
     * @throws TimingClashException if there is a clash in timing with an existing task
     */
    public void add(Task toAdd) throws TimingClashException {
        requireNonNull(toAdd);
        if (isTimeClash(toAdd.getTaskDateTime(), toAdd.getDuration())) {
            throw new TimingClashException(MESSAGE_TASK_TIMING_CLASHES);
        }
        internalList.add(toAdd);
    }
    //@@author

    /**
     * Removes the equivalent task from the list.
     *
     */
    public boolean remove(Task toRemove) throws TaskNotFoundException {
        requireNonNull(toRemove);
        final boolean taskFoundAndDeleted = internalList.remove(toRemove);
        if (!taskFoundAndDeleted) {
            throw new TaskNotFoundException();
        }
        return taskFoundAndDeleted;
    }

    public void setTasks(UniqueTaskList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setTasks(List<Task> tasks) throws TimingClashException {
        requireAllNonNull(tasks);
        final UniqueTaskList replacement = new UniqueTaskList();
        for (final Task task : tasks) {
            replacement.add(task);
        }
        setTasks(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Task> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    //@@author ChoChihTun
    /**
     * Checks for any clashes in the task timing in schedule
     *
     * @param startDateTime start date and time of new task
     * @param duration duration of new task
     */
    private boolean isTimeClash(LocalDateTime startDateTime, String duration) {
        LocalDateTime taskEndTime = getTaskEndTime(duration, startDateTime);

        for (Task recordedTask : internalList) {
            LocalDateTime startTimeOfRecordedTask = recordedTask.getTaskDateTime();
            String durationOfRecordedTask = recordedTask.getDuration();
            LocalDateTime endTimeOfRecordedTask = getTaskEndTime(durationOfRecordedTask, startTimeOfRecordedTask);
            boolean isClash = !(taskEndTime.isBefore(startTimeOfRecordedTask)
                    || startDateTime.isAfter(endTimeOfRecordedTask))
                    && !(taskEndTime.equals(startTimeOfRecordedTask)
                    || startDateTime.equals(endTimeOfRecordedTask));
            if (isClash) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns date and time when the task ends
     */
    private static LocalDateTime getTaskEndTime(String duration, LocalDateTime startDateTime) {
        int indexOfHourDelimiter = duration.indexOf(HOUR_DELIMITER);
        int indexOfMinuteDelimiter = duration.indexOf(MINUTE_DELIMITER);
        int indexOfFirstDigitInMinute = indexOfHourDelimiter + 1;
        int hoursInDuration = Integer.parseInt(duration.substring(0, indexOfHourDelimiter));
        int minutesInDuration = Integer.parseInt(duration.substring(indexOfFirstDigitInMinute, indexOfMinuteDelimiter));

        LocalDateTime taskEndTime;
        taskEndTime = startDateTime.plusHours(hoursInDuration).plusMinutes(minutesInDuration);
        return taskEndTime;
    }
    //@@author

    @Override
    public Iterator<Task> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTaskList // instanceof handles nulls
                && this.internalList.equals(((UniqueTaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
