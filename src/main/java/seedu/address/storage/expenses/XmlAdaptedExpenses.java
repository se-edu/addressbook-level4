package seedu.address.storage.expenses;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.expenses.EmployeeExpensesId;
import seedu.address.model.expenses.Expenses;
import seedu.address.model.expenses.ExpensesAmount;

/**
 * JAXB-friendly version of the Expenses.
 */
public class XmlAdaptedExpenses {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Expenses's %s field is missing!";

    @XmlElement(required = true)
    private String employeeExpensesId;
    @XmlElement(required = true)
    private String expensesAmount;

    /**
     * Constructs an XmlAdaptedExpenses.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedExpenses() {}

    /**
     * Constructs an {@code XmlAdaptedExpenses} with the given expenses details.
     */
    public XmlAdaptedExpenses(String employeeExpensesId, String expensesAmount) {
        this.employeeExpensesId = employeeExpensesId;
        this.expensesAmount = expensesAmount;
    }

    /**
     * Converts a given expenses into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedExpenses
     */
    public XmlAdaptedExpenses(Expenses source) {
        employeeExpensesId = source.getEmployeeExpensesId().employeeExpensesId;
        expensesAmount = source.getExpensesAmount().expensesAmount;
    }

    /**
     * Converts this jaxb-friendly adapted expenses object into the model's Expenses object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted expenses
     */
    public Expenses toModelType() throws IllegalValueException {
        if (employeeExpensesId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EmployeeExpensesId.class.getSimpleName()));
        }
        if (!EmployeeExpensesId.isValidEmployeeExpensesId(employeeExpensesId)) {
            throw new IllegalValueException(EmployeeExpensesId.MESSAGE_EMPLOYE_EXPENSES_ID_CONSTRAINTS);
        }
        final EmployeeExpensesId modelEmployeeExpensesId = new EmployeeExpensesId(employeeExpensesId);

        if (expensesAmount == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ExpensesAmount.class.getSimpleName()));
        }
        if (!ExpensesAmount.isValidExpensesAmount(expensesAmount)) {
            throw new IllegalValueException(ExpensesAmount.MESSAGE_EXPENSES_AMOUNT_CONSTRAINTS);
        }
        final ExpensesAmount modelExpensesAmount = new ExpensesAmount(expensesAmount);
        return new Expenses(modelEmployeeExpensesId, modelExpensesAmount);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedExpenses)) {
            return false;
        }

        XmlAdaptedExpenses otherExpenses = (XmlAdaptedExpenses) other;
        return Objects.equals(employeeExpensesId, otherExpenses.employeeExpensesId)
                && Objects.equals(expensesAmount, otherExpenses.expensesAmount);
    }
}
