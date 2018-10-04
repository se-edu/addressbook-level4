package seedu.address.commons.events.model;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.expenses.ReadOnlyExpensesList;

/** Indicates the ExpensesList in the model has changed*/
public class ExpensesListChangedEvent extends BaseEvent {

    public final ReadOnlyExpensesList data;

    public ExpensesListChangedEvent(ReadOnlyExpensesList data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getExpensesRequestList().size();
    }
}
