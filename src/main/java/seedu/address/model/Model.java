package seedu.address.model;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;

import java.util.Collection;
import java.util.Set;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Deletes the given person.
     * @see #deletePersons(Collection)
     */
    void deletePersons(ReadOnlyPerson... persons) throws UniquePersonList.PersonNotFoundException;

    /**
     * Deletes the given {@code persons}.
     * @throws PersonNotFoundException without deleting any persons if any of the {@code persons}
     * can't be found in this list.
     */
    void deletePersons(Collection<ReadOnlyPerson> persons) throws UniquePersonList.PersonNotFoundException;

    /** Adds the given person */
    void addPerson(Person person) throws UniquePersonList.DuplicatePersonException;

    /** Returns the filtered person list as an {@code UnmodifiableObservableList<ReadOnlyPerson>} */
    UnmodifiableObservableList<ReadOnlyPerson> getFilteredPersonList();

    /** Updates the filter of the filtered person list to show all persons */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered person list to filter by the given keywords*/
    void updateFilteredPersonList(Set<String> keywords);

}
