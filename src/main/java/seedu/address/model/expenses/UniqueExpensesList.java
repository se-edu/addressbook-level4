package seedu.address.model.expenses;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.expenses.exceptions.DuplicateExpensesException;
import seedu.address.model.expenses.exceptions.ExpensesNotFoundException;

/**
 * A list of expenses that enforces uniqueness between its elements and does not allow nulls.
 * An expenses is considered unique by comparing using {@code Expenses#isSameExpensesRequest(Expenses)}. As such,
 * adding and updating of expenses uses Expenses#isSameExpensesRequest(Expenses) for equality so as to ensure that the
 * expenses being added or updated is unique in terms of identity in the UniqueExpensesList. However, the removal of a
 * expenses usesExpenses#equals(Object) so as to ensure that the expenses with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Expenses#isSameExpensesRequest(Expenses)
 */
public class UniqueExpensesList implements Iterable<Expenses> {

    private final ObservableList<Expenses> internalExpensesList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent expenses as the given argument.
     */
    public boolean contains(Expenses toCheck) {
        requireNonNull(toCheck);
        return internalExpensesList.stream().anyMatch(toCheck::isSameExpensesRequest);
    }

    /**
     * Adds a expenses to the list.
     * The expenses must not already exist in the list.
     */
    public void add(Expenses toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateExpensesException();
        }
        internalExpensesList.add(toAdd);
    }

    /**
     * Replaces the expenses {@code target} in the list with {@code editedExpenses}.
     * {@code target} must exist in the list.
     * The expenses identity of {@code editedExpenses} must not be the same as another existing expenses in the list.
     */
    public void setExpenses(Expenses target, Expenses editedExpenses) {
        requireAllNonNull(target, editedExpenses);

        int index = internalExpensesList.indexOf(target);
        if (index == -1) {
            throw new ExpensesNotFoundException();
        }

        if (!target.isSameExpensesRequest(editedExpenses) && contains(editedExpenses)) {
            throw new DuplicateExpensesException();
        }

        internalExpensesList.set(index, editedExpenses);
    }

    /**
     * Removes the equivalent expenses from the list.
     * The expenses must exist in the list.
     */
    public void remove(Expenses toRemove) {
        requireNonNull(toRemove);
        if (!internalExpensesList.remove(toRemove)) {
            throw new ExpensesNotFoundException();
        }
    }

    public void setMultiExpenses(UniqueExpensesList replacement) {
        requireNonNull(replacement);
        internalExpensesList.setAll(replacement.internalExpensesList);
    }

    /**
     * Replaces the contents of this list with {@code multiExpenses}.
     * {@code multiExpenses} must not contain duplicate expenses.
     */
    public void setMultiExpenses(List<Expenses> multiExpenses) {
        requireAllNonNull(multiExpenses);
        if (!multiExpensesAreUnique(multiExpenses)) {
            throw new DuplicateExpensesException();
        }

        internalExpensesList.setAll(multiExpenses);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Expenses> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalExpensesList);
    }

    @Override
    public Iterator<Expenses> iterator() {
        return internalExpensesList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueExpensesList // instanceof handles nulls
                && internalExpensesList.equals(((UniqueExpensesList) other).internalExpensesList));
    }

    @Override
    public int hashCode() {
        return internalExpensesList.hashCode();
    }

    /**
     * Returns true if {@code multiExpenses} contains only unique expenses.
     */
    private boolean multiExpensesAreUnique(List<Expenses> multiExpenses) {
        for (int i = 0; i < multiExpenses.size() - 1; i++) {
            for (int j = i + 1; j < multiExpenses.size(); j++) {
                if (multiExpenses.get(i).isSameExpensesRequest(multiExpenses.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}

