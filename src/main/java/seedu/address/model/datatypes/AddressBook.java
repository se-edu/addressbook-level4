package seedu.address.model.datatypes;

import seedu.address.model.Tag;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;
import seedu.address.util.collections.UnmodifiableObservableList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final ObservableList<Person> persons;
    private final ObservableList<Tag> tags;

    {
        persons = FXCollections.observableArrayList();
        tags = FXCollections.observableArrayList();
    }

    public AddressBook() {}

    /**
     * Persons and Tags are copied into this addressbook
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this(toBeCopied.getPersonList(), toBeCopied.getTagList());
    }

    /**
     * Persons and Tags are copied into this addressbook
     */
    public AddressBook(List<? extends ReadOnlyPerson> persons, List<Tag> tags) {
        resetData(persons, tags);
    }

//// list overwrite operations

    public ObservableList<Person> getPersons() {
        return persons;
    }

    public ObservableList<Tag> getTags() {
        return tags;
    }

    public void setPersons(List<Person> persons) {
        this.persons.setAll(persons);
    }

    public void setTags(Collection<Tag> tags) {
        this.tags.setAll(tags);
    }

    public void clear() {
        persons.clear();
        tags.clear();
    }

    public void resetData(Collection<? extends ReadOnlyPerson> newPersons, Collection<Tag> newTags) {
        setPersons(newPersons.stream().map(Person::new).collect(Collectors.toList()));
        setTags(newTags);
    }

    public void resetData(ReadOnlyAddressBook newData) {
        resetData(newData.getPersonList(), newData.getTagList());
    }

//// person-level operations

    public boolean containsPerson(ReadOnlyPerson key) {
        return persons.contains(key);
    }

    public void addPerson(Person p){
        persons.add(p);
    }

    public boolean removePerson(ReadOnlyPerson key) throws UniquePersonList.PersonNotFoundException {
        if (persons.remove(key)) {
            return true;
        } else {
            throw new UniquePersonList.PersonNotFoundException();
        }
    }

//// tag-level operations

    public void addTag(Tag t){
        tags.add(t);
    }

    public boolean removeTag(Tag t) {
        return tags.remove(t);
    }

//// util methods

    @Override
    public String toString() {
        return persons.size() + " persons, " + tags.size() +  " tags";
        // TODO: refine later
    }

    @Override
    public List<ReadOnlyPerson> getPersonList() {
        return Collections.unmodifiableList(persons);
    }

    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags);
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyPerson> getPersonsAsReadOnlyObservableList() {
        return new UnmodifiableObservableList<>(persons);
    }

    @Override
    public UnmodifiableObservableList<Tag> getTagsAsReadOnlyObservableList() {
        return new UnmodifiableObservableList<>(tags);
    }

}
