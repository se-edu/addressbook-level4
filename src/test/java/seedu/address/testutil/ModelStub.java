package seedu.address.testutil;

import static org.junit.Assert.fail;

import java.util.Comparator;
import java.util.function.Predicate;

import javafx.collections.ObservableList;

import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.Task;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.exceptions.TimingClashException;
import seedu.address.model.tag.Tag;

/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {
    @Override
    public void addPerson(Person person) throws DuplicatePersonException {
        fail("This method should not be called.");
    }

    @Override
    public void addTask(Task task) throws TimingClashException {
        fail("This method should not be called");
    }

    @Override
    public void deleteTask(Task task) {
        fail("This method should not be called");
    }

    @Override
    public ObservableList<Task> getFilteredTaskList() {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public void updateFilteredTaskList(Predicate<Task> predicate) {
        fail("This method should not be called.");
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        fail("This method should not be called.");
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public void deletePerson(Person target) throws PersonNotFoundException {
        fail("This method should not be called.");
    }



    @Override
    public void updatePerson(Person target, Person editedPerson)
            throws DuplicatePersonException {
        fail("This method should not be called.");
    }



    @Override
    public ObservableList<Person> getFilteredPersonList() {
        fail("This method should not be called.");
        return null;
    }


    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        fail("This method should not be called.");
    }

    @Override
    public void deleteTag(Tag tag, Person person) {
        fail("deleteTag should not be called when adding Person.");
    }

    @Override
    public void sortFilteredPersonList(Comparator<Person> comparator) {
        fail("This method should not be called.");
    }

    @Override
    public void sortFilteredTaskList(Comparator<Task> comparator) {
        fail("This method should not be called.");
    }
}
