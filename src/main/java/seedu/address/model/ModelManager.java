package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.logging.Logger;

import com.sun.xml.bind.annotation.OverrideAnnotationOf;

import javafx.beans.property.ReadOnlySetWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.model.item.Item;
import seedu.address.model.ledger.Account;
import seedu.address.model.ledger.Ledger;
import seedu.address.model.member.Person;
import seedu.address.model.tag.Tag;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook versionedAddressBook;
    private final FilteredList<Person> filteredPersons;

    private final FilteredList<Ledger> filteredLedgers;
    private final FilteredList<Item> filteredItems;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedAddressBook(addressBook);
        filteredPersons = new FilteredList<>(versionedAddressBook.getPersonList());

        filteredLedgers = new FilteredList<>(versionedAddressBook.getLedgerList());
        filteredItems = new FilteredList<>(versionedAddressBook.getItemList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        versionedAddressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public void deleteTag (Tag tag) {
        versionedAddressBook.removeTag(tag);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return versionedAddressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(versionedAddressBook));
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return versionedAddressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        versionedAddressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public void addPerson(Person person) {
        versionedAddressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public boolean hasLedger (Ledger ledger) {
        requireAllNonNull(ledger);
        return versionedAddressBook.hasLedger(ledger);
    }

    @Override
    public void addLedger(Ledger ledger) {
        requireNonNull(ledger);
        versionedAddressBook.addLedger(ledger);
        updateFilteredLedgerList(PREDICATE_SHOW_ALL_LEDGERS);
        indicateAddressBookChanged();
    }

    @Override
    public void deleteLedger(Ledger ledger) {
        versionedAddressBook.removeLedger(ledger);
        updateFilteredLedgerList(PREDICATE_SHOW_ALL_LEDGERS);
        indicateAddressBookChanged();
    }

    @Override
    public void increaseAccount(Account account) {
        requireNonNull(account);
    }

    @Override
    public void decreaseAccount(Account account) {
        requireNonNull(account);
    }

    @Override
    public boolean hasItem(Item item) {
        requireAllNonNull(item);
        return versionedAddressBook.hasItem(item);
    }

    @Override
    public void addItem(Item item) {
        versionedAddressBook.addItem(item);
        updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        indicateAddressBookChanged();
    }

    @Override
    public void deleteItem(Item item) {
        versionedAddressBook.removeItem(item);
        indicateAddressBookChanged();
    }

    @Override
    public void updatePerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        versionedAddressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
    }

    @Override
    public void updateLedger(Ledger target, Ledger editedLedger) {
        requireAllNonNull(target, editedLedger);
        versionedAddressBook.updateLedger(target, editedLedger);
        indicateAddressBookChanged();
    }

    @Override
    public void updateItem(Item target, Item editedItem) {
        requireAllNonNull(target, editedItem);
        versionedAddressBook.updateItem(target, editedItem);
        indicateAddressBookChanged();
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    @Override
    public ObservableList<Ledger> getFilteredLedgerList() {
        return FXCollections.unmodifiableObservableList(filteredLedgers);
    }

    @Override
    public ObservableSet<Ledger> getFilteredLedgerSet() {
        Set<Ledger> filteredLedgers2 = new HashSet<>(filteredLedgers);
        return FXCollections.unmodifiableObservableSet((ObservableSet<Ledger>) filteredLedgers2);
    }

    @Override
    public ObservableList<Item> getFilteredItemList() {
        logger.info("Filtered list observed");
        logger.info("Size : " + Integer.toString(filteredItems.size()));
        for (Item i : filteredItems) {
            logger.info(i.getItemName().toString());
        }
        return FXCollections.unmodifiableObservableList(filteredItems);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public void updateFilteredLedgerList(Predicate<Ledger> predicate) {
        requireNonNull(predicate);
        filteredLedgers.setPredicate(predicate);
    }

    @Override
    public void updateFilteredItemList(Predicate<Item> predicate) {
        requireNonNull(predicate);
        filteredItems.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoAddressBook() {
        return versionedAddressBook.canUndo();
    }

    @Override
    public boolean canRedoAddressBook() {
        return versionedAddressBook.canRedo();
    }

    @Override
    public void undoAddressBook() {
        versionedAddressBook.undo();
        indicateAddressBookChanged();
    }

    @Override
    public void redoAddressBook() {
        versionedAddressBook.redo();
        indicateAddressBookChanged();
    }

    @Override
    public void commitAddressBook() {
        versionedAddressBook.commit();
    }

    @Override
    public void undoAllAddressBook() {
        versionedAddressBook.undoAll();
        indicateAddressBookChanged();
    }

    @Override
    public void redoAllAddressBook() {
        versionedAddressBook.redoAll();
        indicateAddressBookChanged();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedAddressBook.equals(other.versionedAddressBook)
                && filteredPersons.equals(other.filteredPersons);
    }

}
