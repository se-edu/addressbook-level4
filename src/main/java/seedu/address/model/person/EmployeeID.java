package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's employeeID in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmployeeID(String)}
 */
public class EmployeeID {

    public static final String MESSAGE_EMPLOYEEID_CONSTRAINTS =
            "Employee IDs should only contain numbers, and it must be 6 digits long";
    public static final String EMPLOYEEID_VALIDATION_REGEX = "\\d{6}";
    public final String value;

    /**
     * Constructs a {@code EmployeeID}.
     *
     * @param employeeid A valid employee ID.
     */

    public EmployeeID(String employeeID) {
        requireNonNull(employeeID);
        checkArgument(isValidEmployeeID(employeeID), MESSAGE_EMPLOYEEID_CONSTRAINTS);
        value = employeeID;
    }

    /**
     * Returns true if a given string is a valid employee ID.
     */
    public static boolean isValidEmployeeID(String test) {
        return test.matches(EMPLOYEEID_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmployeeID // instanceof handles nulls
                && value.equals(((EmployeeID) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
