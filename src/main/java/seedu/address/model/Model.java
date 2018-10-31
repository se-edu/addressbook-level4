package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import seedu.address.model.item.Item;
import seedu.address.model.ledger.Account;
import seedu.address.model.ledger.Ledger;
import seedu.address.model.member.Person;
import seedu.address.model.tag.Tag;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    Predicate<Ledger> PREDICATE_SHOW_ALL_LEDGERS = unused -> true;

    Predicate<Item> PREDICATE_SHOW_ALL_ITEMS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a member with the same identity as {@code member} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Returns true if a ledger with the same date as (@code ledger) exists in the club book
     */
    boolean hasLedger(Ledger ledger);

    /**
     * Returns true if an item with the same name as (@code item) exists in the club book
     */
    boolean hasItem(Item item);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     * Deletes the given member.
     * The member must exist in the address book.
     */
    void deletePerson(Person target);

    void deleteTag(Tag tag);

    /**
     * Adds the given member.
     * {@code member} must not already exist in the address book.
     */
    void addPerson(Person person);


    /**
     * Adds the given ledger
     * @param ledger
     */
    void addLedger(Ledger ledger);

    /**
     * Deletes the given ledger
     * @param ledger
     */
    void deleteLedger(Ledger ledger);

    /**
     * Increases the amount in balance by a given amount
     * @param account
     */
    void increaseAccount (Account account);

    /**
     * Decreases the amount in balance by a given amount
     * @param account
     */
    void decreaseAccount (Account account);
    /**
     * Replaces the given member {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The member identity of {@code editedPerson} must not be the same as another existing member in the address book.
     */
    void updatePerson(Person target, Person editedPerson);

    /**
     * Replaces the given ledger (@code target) with (@code editedLedger).
     * target must exist in the club book.
     */
    void updateLedger(Ledger target, Ledger editedLedger);

    /**
     * Replaces the given item (@code target) with (@code editedItem).
     * target must exist in the club book.
     */
    void updateItem(Item target, Item editedItem);

    /** Returns an unmodifiable view of the filtered member list */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered ledger list */
    ObservableList<Ledger> getFilteredLedgerList();

    /** Returns an unmodifiable view of the filtered ledger set */
    ObservableSet<Ledger> getFilteredLedgerSet();

    /** Returns an unmodifiable view of the filtered item list */
    ObservableList<Item> getFilteredItemList();

    /**
     * Updates the filter of the filtered member list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Updates the filter of the filtered ledger list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredLedgerList(Predicate<Ledger> predicate);

    /**
     * Updates the filter of the filtered item list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredItemList(Predicate<Item> predicate);


    /**
     * Returns true if the model has previous address book states to restore.
     */
    boolean canUndoAddressBook();

    /**
     * Returns true if the model has undone address book states to restore.
     */
    boolean canRedoAddressBook();

    /**
     * Restores the model's address book to its previous state.
     */
    void undoAddressBook();

    /**
     * Restores the model's address book to its previously undone state.
     */
    void redoAddressBook();

    /**
     * Saves the current address book state for undo/redo.
     */
    void commitAddressBook();

    /**
     * Adds the given item.
     * @param item
     */
    void addItem(Item item);

    /**
     * Deletes the given item.
     * @param item
     */
    void deleteItem(Item item);

    /**
     * Restores the model's address book to its original state.
     */
    void undoAllAddressBook();

    /**
     * Restores the model's address book to its furthest undone state.
     */
    void redoAllAddressBook();

}
