package seedu.address.storage.expenses;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.expenses.Expenses;
import seedu.address.model.expenses.ExpensesList;
import seedu.address.model.expenses.ReadOnlyExpensesList;

/**
 * An Immutable ExpensesList that is serializable to XML format
 */
@XmlRootElement(name = "expenseslist")
public class XmlSerializableExpensesList {

    public static final String MESSAGE_DUPLICATE_EXPENSES = "Expenses list contains duplicate expenses).";

    @XmlElement
    private List<XmlAdaptedExpenses> multiExpenses;

    /**
     * Creates an empty XmlSerializableExpensesList.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableExpensesList() {
        multiExpenses = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableExpensesList(ReadOnlyExpensesList src) {
        this();
        multiExpenses.addAll(src.getExpensesRequestList().stream().map(XmlAdaptedExpenses::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this expenseslist into the model's {@code ExpensesList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedExpenses}.
     */
    public ExpensesList toModelType() throws IllegalValueException {
        ExpensesList expensesList = new ExpensesList();
        for (XmlAdaptedExpenses e : multiExpenses) {
            Expenses expenses = e.toModelType();
            if (expensesList.hasExpenses(expenses)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_EXPENSES);
            }
            expensesList.addExpenses(expenses);
        }
        return expensesList;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableExpensesList)) {
            return false;
        }
        return multiExpenses.equals(((XmlSerializableExpensesList) other).multiExpenses);
    }
}
