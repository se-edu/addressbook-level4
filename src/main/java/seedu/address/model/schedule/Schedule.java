package seedu.address.model.schedule;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.person.EmployeeId;

/**
 * Represents a Person's remark in the address book.
 * Guarantees: immutable; is always valid
 */
public class Schedule {

    // Data fields
    private final EmployeeId id;
    private final Type type;
    private final Date date;

    public Schedule(EmployeeId id, Type type, Date date) {
        requireAllNonNull(type, date);
        this.type = type;
        this.date = date;
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public Date getDate() {
        return date;
    }

    public EmployeeId getId() {
        return id;
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Schedule)) {
            return false;
        }

        Schedule otherPerson = (Schedule) other;
        return otherPerson.getDate().equals(getDate())
                && otherPerson.getType().equals(getType());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(type, date);
    }

    /**
     * Returns true if both schedules of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameSchedule(Schedule otherSchedule) {
        if (otherSchedule == this) {
            return true;
        }

        return otherSchedule != null
                && otherSchedule.getId().equals(getId())
                && (otherSchedule.getType().equals(getType())
                && otherSchedule.getDate().equals(getDate()));
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Date: ")
                .append(getDate())
                .append(" Type: ")
                .append(getType());
        return builder.toString();
    }

}
