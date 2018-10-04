package seedu.address.model.expenses;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Expenses Amount in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidExpensesAmount(String)}
 */
public class ExpensesAmount {

    public static final String MESSAGE_EXPENSES_AMOUNT_CONSTRAINTS =
            "Expenses Amount should only contain numbers, and it should be at least 1 digits long";
    public static final String EMPLOYE_EXPENSES_AMOUNT_VALIDATION_REGEX = "\\d{1,}";

    public final String expensesAmount;

    /**
     * Constructs a {@code EmployeeExpensesId}.
     *
     * @param expensesAmount A valid Employee Expenses Id.
     */
    public ExpensesAmount(String expensesAmount) {
        requireNonNull(expensesAmount);
        checkArgument(isValidExpensesAmount(expensesAmount), MESSAGE_EXPENSES_AMOUNT_CONSTRAINTS);
        this.expensesAmount = expensesAmount;
    }

    /**
     * Returns true if a given string is a valid Expenses Amount.
     */
    public static boolean isValidExpensesAmount(String test) {
        return test.matches(EMPLOYE_EXPENSES_AMOUNT_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return expensesAmount;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExpensesAmount // instanceof handles nulls
                && expensesAmount.equals(((ExpensesAmount) other).expensesAmount)); // state check
    }

    @Override
    public int hashCode() {
        return expensesAmount.hashCode();
    }

}
