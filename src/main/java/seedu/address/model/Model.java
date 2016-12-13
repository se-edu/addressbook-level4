package seedu.address.model;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;

import java.util.Set;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Deletes the given person. */
    void deletePerson(ReadOnlyPerson target) throws UniquePersonList.PersonNotFoundException;

    /** Adds the given person */
    void addPerson(Person person) throws UniquePersonList.DuplicatePersonException;

    /**
     * Updates the person {@code dst} with {@code src}.
     *
     * @param dst person to edit
     * @param src person with updated details
     * @throws UniquePersonList.DuplicatePersonException if editing the person's details causes the person to
     *      be equivalent to another existing person in the list.
     * @throws UniquePersonList.PersonNotFoundException if no such person could be found in the list.
     */
    void updatePerson(ReadOnlyPerson dst, ReadOnlyPerson src)
            throws UniquePersonList.DuplicatePersonException, UniquePersonList.PersonNotFoundException;

    /** Returns the filtered person list as an {@code UnmodifiableObservableList<ReadOnlyPerson>} */
    UnmodifiableObservableList<ReadOnlyPerson> getFilteredPersonList();

    /** Updates the filter of the filtered person list to show all persons */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered person list to filter by the given keywords*/
    void updateFilteredPersonList(Set<String> keywords);

}
