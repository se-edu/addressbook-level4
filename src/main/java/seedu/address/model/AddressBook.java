package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.model.item.Item;
import seedu.address.model.item.UniqueItemList;
import seedu.address.model.ledger.Ledger;
import seedu.address.model.ledger.UniqueLedgerList;
import seedu.address.model.member.Person;
import seedu.address.model.member.UniquePersonList;
import seedu.address.model.tag.Tag;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
    }

    private final UniqueLedgerList ledgers;

    {
        ledgers = new UniqueLedgerList();
    }

    private final UniqueItemList items;

    {
        items = new UniqueItemList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the member list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Replaces the contents of the ledger list with {@code ledgers}.
     * {@code ledgers} must not contain duplicate ledgers.
     */
    public void setLedgers(List<Ledger> ledger) {
        this.ledgers.setLedgers(ledgers);
    }

    /**
     * Replaces the contents of the item list with {@code items}.
     * {@code items} must not contain duplicate items.
     */
    public void setItems(List<Item> item) {
        this.items.setItems(items);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
    }

    //// member-level operations

    /**
     * Returns true if a member with the same identity as {@code member} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Returns true if a ledger with the same date as {@code ledger} exists in the club book
     */
    public boolean hasLedger(Ledger ledger) {
        requireNonNull(ledger);
        return ledgers.contains(ledger);
    }

    /**
     * Returns true if an item with the same ItemName as {@code item} exists in the club book
     */
    public boolean hasItem(Item item) {
        requireNonNull(item);
        return items.contains(item);
    }

    /**
     * Adds a member to the address book.
     * The member must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Adds a ledger to the club book
     */
    public void addLedger(Ledger ledger) {
        ledgers.add(ledger);
    }

    public void removeLedger(Ledger ledger) {
        requireNonNull(ledger);
        ledgers.remove(ledger);
    }

    /**
     * Adds an item to the club book
     */
    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        requireNonNull(item);
        items.remove(item);
    }

    /**
     * Replaces the given member {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The member identity of {@code editedPerson} must not be the same as another existing member in the address book.
     */
    public void updatePerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    public void updateLedger(Ledger target, Ledger editedLedger) {
        requireNonNull(editedLedger);
        ledgers.setLedger(target, editedLedger);
    }

    /**
     * Replaces the given item {@code target} in the list with {@code editedItem}.
     * {@code target} must exist in the address book.
     * The ItemName of {@code editedItem} must not be the same as another existing item in the item list.
     */
    public void updateItem(Item target, Item editedItem) {
        requireNonNull(editedItem);
        items.setItem(target, editedItem);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }
    /**
     * Removes {@code tag} from {@code member} in this {@code AddressBook}.
     */
    private void removeTagFromPerson(Tag tag, Person person) {

        Set<Tag> newTags = new HashSet<>(person.getTags());

        if (!newTags.remove(tag)) {
            return;
        }

        Person newPerson = new Person(person.getName(),
                person.getPhone(), person.getEmail(), person.getAddress(), person.getPostalcode(), person.getMajor(), newTags);

        updatePerson(person, newPerson);
    }

    public void removeTag (Tag tag) {
        persons.forEach(person -> removeTagFromPerson(tag, person));
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Ledger> getLedgerList() {
        return ledgers.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Item> getItemList() {
        return items.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && persons.equals(((AddressBook) other).persons));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
