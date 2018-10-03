package seedu.address.model.expenses.exceptions;

/**
 * Signals that the operation will result in duplicate Expenses (Expenses are considered duplicates if they have the
 * same identity).
 */
public class DuplicateExpensesException extends RuntimeException {
    public DuplicateExpensesException() {
        super("Operation would result in duplicate Expenses");
    }
}
