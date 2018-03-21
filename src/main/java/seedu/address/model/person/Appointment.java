package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's appointment in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAppointment(String)}
 */
public class Appointment {


    public static final String MESSAGE_APPOINTMENT_CONSTRAINTS =
            "Appointment should be in the format DD-MM-YYYY";
    public static final String APPOINTMENT_VALIDATION_REGEX = "\\d{2}-\\d{2}-\\d{4}";
    public final String value;

    /**
     * Constructs a {@code Appointment}.
     *
     * @param appointment A valid Appointment date.
     */
    public Appointment(String appointment) {
        requireNonNull(appointment);
        checkArgument(isValidAppointment(appointment), MESSAGE_APPOINTMENT_CONSTRAINTS);
        this.value = appointment;
    }

    /**
     * Returns true if a given string is a valid person appointment.
     */
    public static boolean isValidAppointment(String test) {
        return test.matches(APPOINTMENT_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Appointment // instanceof handles nulls
                && this.value.equals(((Appointment) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
