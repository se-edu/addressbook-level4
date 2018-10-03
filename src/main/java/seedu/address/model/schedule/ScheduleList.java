package seedu.address.model.schedule;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class ScheduleList implements ReadOnlyScheduleList {

    private final UniqueScheduleList schedules;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        schedules = new UniqueScheduleList();
    }

    public ScheduleList() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public ScheduleList(ReadOnlyScheduleList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code schedules}.
     * {@code schedules} must not contain duplicate schedules.
     */
    public void setSchedules(List<Schedule> schedules) {
        this.schedules.setSchedules(schedules);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyScheduleList newData) {
        requireNonNull(newData);

        setSchedules(newData.getScheduleList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasSchedule(Schedule schedule) {
        requireNonNull(schedule);
        return schedules.contains(schedule);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addSchedule(Schedule schedule) {
        schedules.add(schedule);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void updateSchedule(Schedule schedule, Schedule editedSchedule) {
        requireNonNull(editedSchedule);

        schedules.setPerson(schedule, editedSchedule);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Schedule key) {
        schedules.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return schedules.asUnmodifiableObservableList().size() + " schedules";
        // TODO: refine later
    }

    @Override
    public ObservableList<Schedule> getScheduleList() {
        return schedules.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ScheduleList // instanceof handles nulls
                && schedules.equals(((ScheduleList) other).schedules));
    }

    @Override
    public int hashCode() {
        return schedules.hashCode();
    }
}
