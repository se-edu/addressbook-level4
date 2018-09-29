package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Department in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDepartment(String)}
 */
public class Department {
    public static final String MESSAGE_DEPARTMENT_CONSTRAINTS =
            "Department should only contain alphabets and spaces, and it should not be blank";
    public static final String DEPARTMENT_VALIDATION_REGEX = "[A-Za-z -]{2,}";
    public final String value;

    /**
     * Constructs a {@code department}.
     *
     * @param department A valid department.
     */

    public Department (String department) {
        requireNonNull(department);
        checkArgument(isValidDepartment(department), MESSAGE_DEPARTMENT_CONSTRAINTS);
        value = department;
    }

    /**
     * Returns true if a given string is a valid department.
     */
    public static boolean isValidDepartment(String test) {
        return test.matches(DEPARTMENT_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Department // instanceof handles nulls
                && value.equals(((Department) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
