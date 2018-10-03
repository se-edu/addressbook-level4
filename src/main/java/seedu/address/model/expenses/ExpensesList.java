package seedu.address.model.expenses;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameEmployeeExpensesId comparison)
 */
public class ExpensesList implements ReadOnlyExpensesList {

    private final UniqueExpensesList multiExpenses;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        multiExpenses = new UniqueExpensesList();
    }

    public ExpensesList() {}

    /**
     * Creates an AddressBook using the multiExpenses in the {@code toBeCopied}
     */
    public ExpensesList(ReadOnlyExpensesList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the expenses list with {@code multiExpenses}.
     * {@code multiExpenses} must not contain duplicate expenses.
     */
    public void setMultiExpenses(List<Expenses> multiExpenses) {
        this.multiExpenses.setMultiExpenses(multiExpenses);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyExpensesList newData) {
        requireNonNull(newData);

        setMultiExpenses(newData.getExpensesRequestList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code expenses} exists in the address book.
     */
    public boolean hasExpenses(Expenses expenses) {
        requireNonNull(expenses);
        return multiExpenses.contains(expenses);
    }

    /**
     * Adds an expenses to the address book.
     * The employee expenses id must not already exist in the address book.
     */
    public void addExpenses(Expenses e) {
        multiExpenses.add(e);
    }

    /**
     * Replaces the given expenses {@code target} in the list with {@code editedExpenses}.
     * {@code target} must exist in the expenses list.
     * The person identity of {@code editedExpenses} must not be the same as another existing expenses in the expenses
     * list.
     */
    public void updateExpenses(Expenses target, Expenses editedExpenses) {
        requireNonNull(editedExpenses);

        multiExpenses.setExpenses(target, editedExpenses);
    }

    /**
     * Removes {@code key} from this {@code ExpensesList}.
     * {@code key} must exist in the expenses list.
     */
    public void removeExpenses(Expenses key) {
        multiExpenses.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return multiExpenses.asUnmodifiableObservableList().size() + " multiExpenses";
        // TODO: refine later
    }

    @Override
    public ObservableList<Expenses> getExpensesRequestList() {
        return multiExpenses.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExpensesList // instanceof handles nulls
                && multiExpenses.equals(((ExpensesList) other).multiExpenses));
    }

    @Override
    public int hashCode() {
        return multiExpenses.hashCode();
    }
}
