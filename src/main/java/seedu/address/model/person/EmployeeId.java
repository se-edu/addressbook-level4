package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's employeeId in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmployeeId(String)}
 */
public class EmployeeId {

    public static final String MESSAGE_EMPLOYEEID_CONSTRAINTS =
            "Employee Ids should only contain a 6 digit number, and it should not be blank";
    public static final String EMPLOYEEID_VALIDATION_REGEX = "[0-9]{6}";
    public final String value;

    /**
     * Constructs a {@code EmployeeId}.
     *
     * @param employeeId A valid employee Id.
     */

    public EmployeeId(String employeeId) {
        requireNonNull(employeeId);
        checkArgument(isValidEmployeeId(employeeId), MESSAGE_EMPLOYEEID_CONSTRAINTS);
        value = employeeId;
    }

    /**
     * Returns true if a given string is a valid employee Id.
     */
    public static boolean isValidEmployeeId(String test) {
        return test.matches(EMPLOYEEID_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmployeeId // instanceof handles nulls
                && value.equals(((EmployeeId) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
