package seedu.address.model.expenses;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Employee Expenses Id in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmployeeExpensesId(String)}
 */
public class EmployeeExpensesId {

    public static final String MESSAGE_EMPLOYE_EXPENSES_ID_CONSTRAINTS =
            "Employee Expenses Id should only contain numbers, and it should be at least 3 digits long";
    public static final String EMPLOYE_EXPENSES_ID_VALIDATION_REGEX = "\\d{3,}";

    public final String employeeExpensesId;

    /**
     * Constructs a {@code EmployeeExpensesId}.
     *
     * @param employeeExpensesId A valid Employee Expenses Id.
     */
    public EmployeeExpensesId(String employeeExpensesId) {
        requireNonNull(employeeExpensesId);
        checkArgument(isValidEmployeeExpensesId(employeeExpensesId), MESSAGE_EMPLOYE_EXPENSES_ID_CONSTRAINTS);
        this.employeeExpensesId = employeeExpensesId;
    }

    /**
     * Returns true if a given string is a valid Employee Expenses Id.
     */
    public static boolean isValidEmployeeExpensesId(String test) {
        return test.matches(EMPLOYE_EXPENSES_ID_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return employeeExpensesId;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmployeeExpensesId // instanceof handles nulls
                && employeeExpensesId.equals(((EmployeeExpensesId) other).employeeExpensesId)); // state check
    }

    @Override
    public int hashCode() {
        return employeeExpensesId.hashCode();
    }

}
