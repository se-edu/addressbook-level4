package seedu.address.model.person.exceptions;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's department in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDepartment(String)}
 */
public class Department {

    public static final String MESSAGE_DEPARTMENT_CONSTRAINTS =
            "Department names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String DEPARTMENT_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullDepartment;

    /**
     * Constructs a {@code Name}.
     *
     * @param department A valid department name.
     */
    public Department(String department) {
        requireNonNull(department);
        checkArgument(isValidDepartment(department), MESSAGE_DEPARTMENT_CONSTRAINTS);
        fullDepartment = department;
    }

    /**
     * Returns true if a given string is a valid department name.
     */
    public static boolean isValidDepartment(String test) {
        return test.matches(DEPARTMENT_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullDepartment;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Department // instanceof handles nulls
                && fullDepartment.equals(((Department) other).fullDepartment)); // state check
    }

    @Override
    public int hashCode() {
        return fullDepartment.hashCode();
    }

}
