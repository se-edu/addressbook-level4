package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;


public class UniqueTaskList implements Iterable<Task>{

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();
    private final ObservableList<Task> internalUnmodifiableList = FXCollections.unmodifiableObservableList(internalList);

    public boolean contains(Task toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePerson);
    }
    public void add(Task toAdd) {
        requireNonNull(toAdd);
//        if (contains(toAdd)) {
//            throw new DuplicatePersonException();
//        }
        internalList.add(toAdd);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Task> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Task> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTaskList // instanceof handles nulls
                && internalList.equals(((UniqueTaskList) other).internalList));
    }
    public void setTasks(List<Task> tasks) {
        requireAllNonNull(tasks);
//        if (!personsAreUnique(persons)) {
//            throw new DuplicatePersonException();
//        }

        internalList.setAll(tasks);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }


}
