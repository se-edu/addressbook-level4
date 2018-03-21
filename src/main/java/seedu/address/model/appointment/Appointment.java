package seedu.address.model.appointment;

import java.util.Date;
import java.util.Objects;

import seedu.address.model.person.Person;

/**
 * Represents a Appointment in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Appointment {

    private final Person person;
    private final Date date;
    private final String location;
    private final String remarks;

    /**
     * Every field must be present and not null.
     */
    public Appointment(Person person, Date date, String location, String remarks) {
        this.person = person;
        this.date = date;
        this.location = location;
        this.remarks = remarks;
    }

    public Person getPerson() {
        return person;
    }

    public Date getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getRemarks() {
        return remarks;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Appointment otherAppointment = (Appointment) other;
        return otherAppointment.getPerson().equals(this.getPerson())
                && otherAppointment.getDate().equals(this.getDate())
                && otherAppointment.getLocation().equals(this.getLocation())
                && otherAppointment.getRemarks().equals(this.getRemarks());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(person, date, location, remarks);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getPerson().getName())
                .append(" Date: ")
                .append(getDate().toString())
                .append(" Location: ")
                .append(getLocation())
                .append(" Remarks: ")
                .append(getRemarks());
        return builder.toString();
    }
}
