package seedu.address.model.expenses;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents an Expenses of an employee in the expensesList.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Expenses {

    // Identity fields
    private final EmployeeExpensesId employeeExpensesId;

    // Data fields
    private final ExpensesAmount expensesAmount;

    /**
     * Every field must be present and not null.
     */
    public Expenses(EmployeeExpensesId employeeExpensesId, ExpensesAmount expensesAmount) {
        requireAllNonNull(employeeExpensesId, expensesAmount);
        this.employeeExpensesId = employeeExpensesId;
        this.expensesAmount = expensesAmount;
    }

    public EmployeeExpensesId getEmployeeExpensesId() {
        return employeeExpensesId;
    }

    public ExpensesAmount getExpensesAmount() {
        return expensesAmount;
    }

    /**
     * Returns true if both persons of the same id have expenses that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameExpensesRequest(Expenses otherExpenses) {
        if (otherExpenses == this) {
            return true;
        }

        return otherExpenses != null
                && otherExpenses.getEmployeeExpensesId().equals(getEmployeeExpensesId())
                && (otherExpenses.getExpensesAmount().equals(getExpensesAmount()));
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

        if (!(other instanceof Expenses)) {
            return false;
        }

        Expenses otherExpenses = (Expenses) other;
        return otherExpenses.getEmployeeExpensesId().equals(getEmployeeExpensesId())
                && otherExpenses.getExpensesAmount().equals(getExpensesAmount());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(employeeExpensesId, expensesAmount);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getEmployeeExpensesId())
                .append(" Expenses Amount: ")
                .append(getExpensesAmount());
        return builder.toString();
    }

}
