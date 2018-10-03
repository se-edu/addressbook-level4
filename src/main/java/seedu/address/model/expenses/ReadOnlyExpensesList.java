package seedu.address.model.expenses;
import javafx.collections.ObservableList;
/**
 * Unmodifiable view of an Expenses list
 */
public interface ReadOnlyExpensesList {
    /**
     * Returns an unmodifiable view of the Expenses list.
     * This list will not contain any duplicate Expenses.
     */
    ObservableList<Expenses> getExpensesRequestList();
}
