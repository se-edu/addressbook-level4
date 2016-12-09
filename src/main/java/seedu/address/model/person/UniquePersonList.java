package seedu.address.model.person;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.commons.exceptions.DuplicateDataException;

import java.util.*;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Person#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniquePersonList implements Iterable<Person> {

    private static final int PERSON_NOT_FOUND = -1;
    private final ObservableList<Person> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(ReadOnlyPerson toCheck) {
        assert toCheck != null;
        return internalList.contains(toCheck);
    }

    /**
     * Adds a person to the list.
     *
     * @throws DuplicatePersonException if the person to add is a duplicate of an existing person in the list.
     */
    public void add(Person toAdd) throws DuplicatePersonException {
        assert toAdd != null;
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }
    
    /**
     * Edits the details of an existing person in the list.
     * 
     * @param toEdit        The person whose details are to be edited
     * @param detailsToEdit The map containing values of details to be edited.
     * @throws DuplicatePersonException If editing the person's details causes the person to 
     *                                  be equivalent to another existing person in the list.
     */
    public void edit(Person toEdit, HashMap<String, Object> detailsToEdit) throws DuplicatePersonException {
        assert toEdit != null && detailsToEdit != null;
        
        if (isDuplicatePerson(toEdit, detailsToEdit)) {
            throw new DuplicatePersonException();
        } 
        
        int index = internalList.indexOf(toEdit);
        editDetails(toEdit, detailsToEdit);
        internalList.set(index, toEdit);
    }

    /**
     * Verifies if editing the person's details causes the person to 
     * be equivalent to another existing person in the list.
     * 
     * @param toEdit        The person whose details are to be edited
     * @param detailsToEdit The map containing values of details to be edited.
     * @return              True if editing the person's details causes the person to 
     *                      be equivalent to another existing person in the list.
     */
    private boolean isDuplicatePerson(Person toEdit, HashMap<String, Object> detailsToEdit) {
        Person copy = new Person(toEdit);
        editDetails(copy, detailsToEdit);
        
        if (!editingOnlyTags(detailsToEdit) && contains(copy)) {
            return true;
        }
        return false;
    }

    /**
     * Verifies if only the person's tags are being edited.
     * 
     * @param detailsToEdit The map containing values of details to be edited.
     * @return              True if editing only tags.
     */
    private boolean editingOnlyTags(HashMap<String, Object> detailsToEdit) {
        return detailsToEdit.size() == 1 
                && (detailsToEdit.containsKey(Tag.KEY) || detailsToEdit.containsKey(Tag.RESET_KEY));
    }

    /**
     * Updates the values of the person {@code toEdit}.
     * 
     * @param toEdit        The person whose details are to be edited
     * @param detailsToEdit The map containing values of details to be edited.
     */
    @SuppressWarnings("unchecked")
    private void editDetails(Person toEdit, HashMap<String, Object> detailsToEdit) {
        Set<String> keySet = detailsToEdit.keySet();
        if (keySet.contains(Name.KEY)) {
            toEdit.setName((Name) detailsToEdit.get(Name.KEY)); 
        }
        
        if (keySet.contains(Phone.KEY)) {
            toEdit.setPhone((Phone) detailsToEdit.get(Phone.KEY)); 
        }
        
        if (keySet.contains(Email.KEY)) {
            toEdit.setEmail((Email) detailsToEdit.get(Email.KEY)); 
        }
        
        if (keySet.contains(Address.KEY)) {
            toEdit.setAddress((Address) detailsToEdit.get(Address.KEY)); 
        }
        
        if (keySet.contains(Tag.KEY)) {
            toEdit.setTags(new UniqueTagList((Set<Tag>) detailsToEdit.get(Tag.KEY))); 
        } else if (keySet.contains(Tag.RESET_KEY)) {
            toEdit.setTags(new UniqueTagList());
        }
    }
    
    /**
     * Returns the Person object equivalent to {@code readOnlyPersonToEdit}.
     * 
     * @param readOnlyPersonToEdit  The person whose details are to be edited, as a {@code ReadOnlyPerson} object.
     * @return                      The person whose details are to be edited, as a {@code Person} object.
     * @throws PersonNotFoundException if no such person could be found in the list.
     */
    public Person findPersonToEdit(ReadOnlyPerson readOnlyPersonToEdit) throws PersonNotFoundException {
        int indexOfPerson = internalList.indexOf(readOnlyPersonToEdit);
        if (indexOfPerson != PERSON_NOT_FOUND) {
            return internalList.get(indexOfPerson);
        } else {
            throw new UniquePersonList.PersonNotFoundException();
        }
    }

    /**
     * Removes the equivalent person from the list.
     *
     * @throws PersonNotFoundException if no such person could be found in the list.
     */
    public boolean remove(ReadOnlyPerson toRemove) throws PersonNotFoundException {
        assert toRemove != null;
        final boolean personFoundAndDeleted = internalList.remove(toRemove);
        if (!personFoundAndDeleted) {
            throw new PersonNotFoundException();
        }
        return personFoundAndDeleted;
    }

    public void setPersons(UniquePersonList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public UnmodifiableObservableList<Person> asObservableList() {
        return new UnmodifiableObservableList<>(internalList);
    }

    @Override
    public Iterator<Person> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePersonList // instanceof handles nulls
                && this.internalList.equals(
                ((UniquePersonList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicatePersonException extends DuplicateDataException {
        protected DuplicatePersonException() {
            super("Operation would result in duplicate persons");
        }
    }

    /**
     * Signals that an operation targeting a specified person in the list would fail because
     * there is no such matching person in the list.
     */
    public static class PersonNotFoundException extends Exception {}

}
